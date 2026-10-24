# 架构说明

## 分层

```
┌────────────────────────────────────────────────────┐
│  ui/            41 个 Fragment（只 inflate 布局）    │
│  页面之间由 res/navigation/nav_graph.xml 连接        │
└───────────────────────┬────────────────────────────┘
                        │ 调用
┌───────────────────────▼────────────────────────────┐
│  core/ServiceLocator   服务注册表（可替换）           │
│  core/contract/        13 个业务接口（你要实现的契约） │
│  core/stub/            默认空实现（NoOpServices）     │
└───────────────────────┬────────────────────────────┘
                        │ 由你实现
        ┌───────────────┴────────────────┐
        ▼                                ▼
┌───────────────────────┐      ┌──────────────────────┐
│ data/room/  本地持久化 │      │ network/  HTTP 访问   │
│ 13 实体 + 11 DAO       │      │ Retrofit 接口与客户端 │
└───────────────────────┘      └──────────────────────┘
```

设计目标：**UI 只依赖接口，不依赖实现**。这样你可以先写本地实现（离线可用），
再逐步替换为联网实现，页面代码不用改。

## 关键文件

| 文件 | 作用 |
| --- | --- |
| `App.java` | Application，在这里调用 `ServiceLocator.installXxx(...)` 注入实现 |
| `core/ServiceLocator.java` | 13 个静态槽位，默认指向 `NoOpServices`，线程安全的 install |
| `core/Callback.java` | `onSuccess(T)` / `onError(String)`，实现方负责切回主线程 |
| `core/stub/NoOpServices.java` | 全部接口的空实现：列表返回空、单对象返回错误「尚未实现」 |
| `data/room/AppDatabase.java` | Room 入口，version 1，13 张表 |
| `network/RetrofitClient.java` | 三套 BaseUrl：业务后端 / 统计后端 / AI 服务 |

## 数据模型（data/room）

13 个实体，按用途分组：

| 分组 | 实体 | 说明 |
| --- | --- | --- |
| 账号 | `User` | 本地账号（`username` 唯一） |
| 题库 | `QuestionBank`、`Question` | 题库与题目，题目含图片路径、选项、答案、解析 |
| 练习 | `AnswerRecord`、`WrongSource`、`WrongDeleteLog`、`PracticeProgressEntity` | 作答记录、错题来源、错题删除日志、练习进度 |
| 考试 | `ExamConfig`、`ExamRecord`、`ExamAnswer` | 组卷配置、考试记录、每题作答 |
| 局域网 | `LanUserEntity`、`LanExamSession`、`LanRankCache` | 局域网考生、会话、排行榜缓存 |

DAO 中已经包含统计用聚合查询的返回类型：`BankAccuracyStat`、`QuestionTypeStat`。

> 骨架是全新空库（version = 1），没有历史迁移链。新增字段请升级 version 并写 Migration；
> `fallbackToDestructiveMigration()` 只适合开发期。

## 三个闭环流程

### 1. 导入题库

```
UploadFragment
  → QuestionParser.parse(file, options, callback)
      ├─ PDF/Word/Excel/TXT 文本抽取（你实现）
      ├─ 规则切题（题干/选项/答案/解析）（你实现）
      └─ 失败时 AiQuestionExtractor.extract(...) 兜底
  → QuestionBankService.createBank / saveQuestion  →  Room
```

### 2. 刷题

```
PracticeFragment
  → PracticeService.startPractice(request)
      → QuestionDao 按 bankIds / 模式抽题
  → 每题：AnswerMatcher.isCorrect / score
  → PracticeService.submitAnswer  →  AnswerRecord + WrongSource
  → PracticeService.saveProgress
```

### 3. 考试

```
ExamConfigFragment → BankMultiSelectFragment
  → ExamService.startExam(config)       生成 ExamConfig + ExamRecord
  → ExamFragment                         逐题作答，ExamService.saveAnswer
  → 交卷 ExamService.submitExam
      → 客观题判分（AnswerMatcher）
      → 主观题标记 needsReview，等 AiGrader / 人工阅卷
  → ExamResultFragment / ExamReviewFragment / ExamHistoryFragment
```

### 4. 班级考试（联网）

```
教师：CreateClass → 上传班级题库 → 组卷 → class/share-exam → class/publish-exam
学生：JoinClass → class/exams → exam/detail → exam/submit → class/ranking
教师：exam/monitor、exam/grade-by-teacher、class/close-exam、class/publish-ranking
```

对应的客户端方法见 `network/BizApi.java`，服务端路由见 `server/src/routes/index.js`。

### 5. 局域网考试

`LanExamService` 只给契约，通信方式由你决定。常见方案：

- UDP 组播/广播做发现（`CHANGE_WIFI_MULTICAST_STATE` 权限已声明）
- HTTP 或 WebSocket 做答卷传输（发卷端起一个本地服务）
- `LanExamSession` 双端各存一条，用同一个 `sessionId` 关联

## 线程模型

- Room 查询、文件解析、网络请求都必须离开主线程
- `Callback` 约定：**实现方负责切回主线程**（`Handler(Looper.getMainLooper())` 或 `runOnUiThread`）
- NoOpServices 是同步返回的，所以骨架阶段不会出现线程问题

## 为什么没有 ViewModel

原项目的 ViewModel / Adapter / 交互逻辑属于业务代码，未包含。
推荐做法：每个 Fragment 配一个 ViewModel，ViewModel 只依赖 `ServiceLocator` 提供的接口，
这样页面逻辑可测试、可替换。
