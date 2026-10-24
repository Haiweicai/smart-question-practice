# 接口契约清单

所有接口位于 `app/src/main/java/com/example/smart_question_practice/core/contract/`。
默认实现是 `core/stub/NoOpServices.java`（列表返回空、其余返回「尚未实现」）。

注册你的实现：

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.installAuth(new MyAuthService());
        ServiceLocator.installBank(new MyQuestionBankService());
        // ...
    }
}
```

> 约定：所有回调都在主线程触发；耗时操作（IO、数据库、网络）必须在实现里自行切线程。

---

## 1. AuthService —— 账号与会话

```java
void login(String account, String password, String device, Callback<User> callback);
void register(String account, String password, String device, Callback<User> callback);
User currentUser();      // 未登录返回 null
void logout();
```

- 本地实现：`UserDao` 查 `username`，密码建议加盐哈希后存
- 联网实现：`POST api/cloud/login`、`api/cloud/register`
- 登录态建议存 `SharedPreferences`，并让 `currentUser()` 可同步读取

## 2. QuestionBankService —— 题库与题目

```java
void listBanks(Callback<List<QuestionBank>> callback);
void createBank(String name, Callback<QuestionBank> callback);
void deleteBank(long bankId, Callback<Void> callback);
void listQuestions(long bankId, Callback<List<Question>> callback);
void saveQuestion(Question question, Callback<Question> callback);
void deleteQuestion(long questionId, Callback<Void> callback);
```

- 直接用 `QuestionBankDao` / `QuestionDao`
- 删除建议软删除（`isDeleted`）+ `RecycleBinFragment` 恢复
- 保存题目后记得更新题库的 `questionCount` 与 `typeStats`

## 3. PracticeService —— 刷题

```java
void startPractice(PracticeRequest request, Callback<List<Question>> callback);
void submitAnswer(long questionId, String userAnswer, boolean correct, Callback<Void> callback);
void saveProgress(long bankId, int currentIndex, Callback<Void> callback);
void listWrongQuestions(Callback<List<Question>> callback);
void listFavoriteQuestions(Callback<List<Question>> callback);
void setFavorite(long questionId, boolean favorite, Callback<Void> callback);

class PracticeRequest {
    long[] bankIds;
    int questionCount;
    int mode;   // 0=顺序 1=随机 2=错题重练 3=收藏练习
}
```

- `startPractice` 负责抽题：顺序按 `order`，随机用 `ORDER BY RANDOM()`
- `submitAnswer` 写 `AnswerRecord`；答错同时写 `WrongSource`
- `listWrongQuestions` 需要排除 `WrongDeleteLog` 中被删除的记录

## 4. QuestionParser —— 题库文件解析

```java
List<String> supportedExtensions();
void parse(File file, ParseOptions options, Callback<ParseResult> callback);

class ParseOptions { boolean useAiFallback = true; int expectedCount = 0; }
class ParseResult {
    List<Question> questions;
    int rawBlockCount;
    List<String> warnings;
}
```

- 这是原项目的核心算法，**未开源**
- 建议顺序：文本抽取 → 按题号/题型切块 → 正则提取选项与答案 → 交叉校验
- 校验失败或切块数量异常时，`useAiFallback` 走 `AiQuestionExtractor`
- `warnings` 用于导入预览弹窗（`dialog_import_preview.xml`）

## 5. AnswerMatcher —— 客观题判分

```java
boolean isCorrect(Question question, String userAnswer);
double score(Question question, String userAnswer);
```

按题型分别处理：

| 题型 | 规则建议 |
| --- | --- |
| 单选 `single` | 忽略大小写与空白，直接比较 |
| 多选 `multi` | 集合相等才算对；或用部分给分（漏选给一半、错选 0） |
| 判断 `judge` | 归一化 T/F、对/错、√/× |
| 填空 `fill` | 按 `blanks` 拆分，逐空比较；支持同义词表、忽略标点与空格 |

## 6. AiGrader —— 主观题评分

```java
void grade(GradingRequest request, Callback<GradingResult> callback);
```

- 输入：题干、参考答案、学生答案、满分
- 输出：分数、评语、优点/不足/建议
- 实现：调用 `AiQuestionApi.extractQuestions`（OpenAI 兼容），Prompt 自行设计
- 必须做 JSON 解析兜底（模型可能返回多余文字）
- 对应服务端 `POST api/exam/grade-subjective`

## 7. AiQuestionExtractor —— AI 抽题兜底

```java
void extract(String rawText, ExtractOptions options, Callback<ExtractResult> callback);
```

- 输出 `questionsJson`，结构见 [API.md](API.md) 的 question 字段
- 建议分批（每批 ≤ 20 题）避免超长上下文
- 需要校验每道题字段完整（题干、答案、题型），不合格的丢弃并计入警告

## 8. ExamService —— 本地模拟考试

```java
void startExam(ExamConfig config, Callback<ExamRecord> callback);
void listExamQuestions(long examRecordId, Callback<List<Question>> callback);
void saveAnswer(long examRecordId, ExamAnswer answer, Callback<Void> callback);
void submitExam(long examRecordId, Callback<ExamRecord> callback);
void forceSubmitExam(long examRecordId, String reason, Callback<ExamRecord> callback);
void listExamHistory(boolean includeLanExams, Callback<List<ExamRecord>> callback);
void deleteExamRecord(long examRecordId, Callback<Void> callback);
```

- 组卷：按 `ExamConfig` 的各题型数量从题库抽题，写入 `ExamRecord.questionIds`
- 选项乱序时把映射存进 `optionOrders`，交卷后按原顺序还原判分
- `submitExam` 计算客观题得分、`needsReview`、用时；主观题等阅卷
- `forceSubmitExam` 用于切后台超限（`switchCount > maxSwitchCount`）
- `listExamHistory` 用 `includeLanExams` 过滤 `lanFlag`

## 9. ClassService —— 班级

```java
void createClass(ClassInfo info, Callback<Integer> callback);
void searchClass(String code, Callback<ClassInfo> callback);
void joinClass(String code, Callback<Integer> callback);
void listMyClasses(Callback<List<ClassInfo>> callback);
void listMembers(int classId, Callback<List<Member>> callback);
void removeMember(int classId, int userId, Callback<Void> callback);
void addMembers(int classId, List<Integer> userIds, Callback<Void> callback);
void dissolveClass(int classId, Callback<Void> callback);
void uploadSharedBank(int classId, String bankName, String questionsJson, Callback<Integer> callback);
void listSharedBanks(int classId, Callback<List<SharedBank>> callback);
void deleteSharedBank(int classId, int sharedBankId, Callback<Void> callback);
```

- HTTP 对应：`api/class/*`（`network/BizApi.java`）
- 服务端表：`classes` / `class_members` / `class_banks`
- 权限：只有 `role = teacher` 能改名、移除成员、解散、删共享题库
- 创建班级可能需要会员校验（`api/vip/status`），这部分业务规则自己定

## 10. FriendService —— 好友与分享

```java
void myCode(Callback<String> callback);
void applyFriend(String code, Callback<Void> callback);
void listRequests(Callback<List<FriendRequest>> callback);
void handleRequest(String requestId, boolean accept, Callback<Void> callback);
void listFriends(Callback<List<Friend>> callback);
void removeFriend(String friendPublicId, Callback<Void> callback);
void sendBank(String toPublicId, String bankName, String questionsJson, Callback<Void> callback);
void listInbox(Callback<List<InboxItem>> callback);
void ackInbox(int itemId, Callback<Void> callback);
```

- 好友码：服务端生成、定时轮换（原实现 30 分钟），客户端只负责展示与刷新
- 好友申请是审批制：`api/friend/apply` → `api/friend/request/handle`
- 题库直发进收件箱：`api/share/send` → `api/share/pending` → `api/share/respond`
- 拉取后要落库为本地题库，并去重（同名 + 同题量可提示覆盖）

## 11. LanExamService —— 局域网考试

```java
void hostSession(LanExamSession session, Callback<String> callback);
void discoverSessions(int timeoutMs, Callback<List<LanExamSession>> callback);
void joinSession(String sessionId, Callback<LanExamSession> callback);
void submitPaper(String sessionId, String answersJson, Callback<Void> callback);
void finishSession(String sessionId, Callback<Void> callback);
void cancelSession(String sessionId, Callback<Void> callback);
void listRank(String sessionId, Callback<List<RankItem>> callback);
```

- 通信协议由你设计；权限已在 Manifest 声明（WiFi 组播）
- 数据落 `LanExamSession` / `LanRankCache`，`ExamRecord.lanFlag` 标记试卷归属
- 两种批阅方式：考生自批（`lanGradingMode = 0`）、发卷人批阅（`= 1`）

## 12. StatsService —— 学习统计

```java
StatsSummary getSummary(long fromTime, long toTime);
List<TypeAccuracy> getTypeAccuracy(long fromTime, long toTime);
List<DailyCount> getDailyCounts(long fromTime, long toTime);
List<BankAccuracy> getBankAccuracy();
```

- 数据源：`AnswerRecordDao`，已有 `BankAccuracyStat` / `QuestionTypeStat` 投影
- `StatisticsFragment` 用 MPAndroidChart 展示，骨架未接

## 13. UpdateService —— 应用更新

```java
void checkUpdate(Callback<UpdateInfo> callback);
void downloadApk(UpdateInfo info, ProgressListener listener, Callback<String> callback);
```

- 服务端可自行暴露 `GET api/update/check`（骨架已留路由，返回 501）
- 下载完用 `FileProvider`（Manifest 已配置 `file_paths.xml`）拉起安装
- 注意 `REQUEST_INSTALL_PACKAGES` 权限与 Android 8+ 的「未知来源」授权

---

## 快速自查

实现完一个接口后，用下面方式验证没有遗漏：

1. 在 `App.onCreate()` 里 `install` 对应实现
2. 打开相关页面，确认不再提示「尚未实现」
3. 断网 / 空数据 / 超长字符串三种边界各试一次
4. 确认回调在主线程（直接 `Toast` 不崩溃）
