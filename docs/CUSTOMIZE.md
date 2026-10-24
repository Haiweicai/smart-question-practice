# 二次开发指南

## 一、改包名（做成你自己的 App）

骨架包名是 `com.example.smart_question_practice`，建议改成你自己的。

### 1. 修改构建配置

`app/build.gradle`：

```groovy
android {
    namespace 'com.yourcompany.quizapp'
    defaultConfig {
        applicationId "com.yourcompany.quizapp"
    }
}
```

### 2. 修改 Manifest

`app/src/main/AndroidManifest.xml` 里的相对类名（`.App`、`.SplashActivity` 等）不用改，
只要 `namespace` 改了，相对路径会自动指向新包。

> `FileProvider` 的 `authorities` 是 `${applicationId}.fileprovider`，会自动跟随。

### 3. 移动源码目录

```bash
# 以 Android Studio 为准：右键包名 → Refactor → Rename → 勾选 Rename package
# 或手动：
app/src/main/java/com/example/smart_question_practice  ->  com/yourcompany/quizapp
```

同时全局替换 Java 文件里的 `package com.example.smart_question_practice` 与
`import com.example.smart_question_practice.`。

### 4. 检查残留

```bash
grep -r "com.example.smart_question_practice" app/src
```

`retrofit2` 接口里的路径是相对路径，不受影响；`BuildConfig` 会随 namespace 自动重建。

## 二、改品牌

| 内容 | 位置 |
| --- | --- |
| 应用名 | `res/values/strings.xml` 的 `app_name` |
| 图标 | `res/mipmap-*/app_icon*`、`res/drawable/app_icon*.xml` |
| 启动页 | `res/layout/activity_splash.xml`、`res/drawable/splash_background.xml` |
| 主色 | `res/values/colors.xml` + `res/values/themes.xml` 的 `eco*` 属性 |
| 护眼主题 | `res/values/themes_eye_care.xml` |
| 法律文本 | `assets/legal/`（当前为占位联系方式，务必替换） |

主题系统说明：`Base.Theme.StudyTest` 把 Material3 的颜色映射到自定义 `eco*` 属性，
各配色子主题只需覆盖 `eco*` 即可整体换肤，不用逐个页面改颜色。

## 三、实现业务

推荐顺序与对应关系：

```
1. 本地题库      QuestionBankService  →  QuestionBankDao / QuestionDao
2. 刷题          PracticeService      →  AnswerRecordDao / WrongSourceDao / PracticeProgressDao
3. 判分          AnswerMatcher
4. 导入          QuestionParser (+ AiQuestionExtractor)
5. 考试          ExamService          →  ExamConfigDao / ExamRecordDao / ExamAnswerDao
6. 账号          AuthService          →  api/cloud/*
7. 班级          ClassService         →  api/class/*
8. 好友          FriendService        →  api/friend/*、api/share/*
9. 班级考试      （直接调 BizApi，或封装成新接口）
10. 局域网       LanExamService
```

每个实现写完后在 `App.onCreate()` 注册，示例：

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Context ctx = getApplicationContext();
        ServiceLocator.installBank(new LocalQuestionBankService(ctx));
        ServiceLocator.installPractice(new LocalPracticeService(ctx));
        ServiceLocator.installMatcher(new DefaultAnswerMatcher());
    }
}
```

### 建议的实现类组织

```
data/repo/
├── LocalQuestionBankService.java
├── LocalPracticeService.java
├── LocalExamService.java
├── LocalAuthService.java
├── RemoteClassService.java
├── RemoteFriendService.java
├── parser/
│   ├── PdfQuestionParser.java
│   ├── DocxQuestionParser.java
│   └── ExcelQuestionParser.java
└── grade/
    ├── DefaultAnswerMatcher.java
    └── RemoteAiGrader.java
```

`ServiceLocator` 是全局单例，实现类内部用 `AppDatabase.getInstance(ctx)` 取库即可。

## 四、页面骨架怎么接

每个 Fragment 目前只有：

```java
@Override
protected int getLayoutId() { return R.layout.fragment_xxx; }
```

接业务时：

1. 用 `viewBinding`（已开启）拿到控件
2. 建一个 ViewModel，只依赖 `ServiceLocator`
3. `Callback` 回调里更新 UI（实现方保证在主线程）
4. 列表页补 Adapter（`res/layout/item_*.xml` 已经准备好）

示例（题库列表）：

```java
public class HomeFragment extends BaseFragment {
    @Override protected int getLayoutId() { return R.layout.fragment_home; }

    @Override public void onViewCreated(@NonNull View v, Bundle s) {
        ServiceLocator.bank().listBanks(new Callback<List<QuestionBank>>() {
            @Override public void onSuccess(List<QuestionBank> data) { /* 绑定到 RecyclerView */ }
            @Override public void onError(String msg) { /* Toast */ }
        });
    }
}
```

## 五、加新模块

1. 在 `core/contract/` 新建接口（例如 `NoticeService`）
2. 在 `core/stub/NoOpServices.java` 加空实现
3. 在 `core/ServiceLocator.java` 加槽位 + install 方法
4. 在 `App.onCreate()` 注册真实实现
5. 新建 `ui/XxxFragment` + 布局 + `nav_graph.xml` 节点

## 六、删掉不需要的模块

骨架按模块切分，删起来很快：

| 删除目标 | 需要动的地方 |
| --- | --- |
| 局域网考试 | `ui/lan/`、`core/contract/LanExamService.java`、`LanExamService` 槽位、`nav_graph.xml` 的 `lanExamFragment` / `lanExamRankFragment` 及其 action、`data/room/Lan*` |
| 班级考试 | `ui/classexam/`、`nav_graph.xml` 中 `classExamsFragment` 起的整段、`BizApi` 中班级考试相关方法 |
| 会员 | `BizApi` 的 vip 方法、相关 DTO |
| AI 相关 | `network/AiQuestionApi.java`、`AiChat*`、`core/contract/AiGrader.java`、`AiQuestionExtractor.java` 及其槽位 |
| 统计上报 | `network/StatsApi.java`、服务端 `heartbeats` / `ai_usage` / `crash_reports` |

删完记得同步 `docs/` 里的说明，保持文档与代码一致。

## 七、代码风格建议

- 页面只做展示与事件转发，业务放 Service/Repository
- 数据库访问一律离开主线程
- 接口回调统一走 `Callback`，不要混用 `LiveData` 与裸 `Thread`
- 新增实体时同步更新 `AppDatabase.version` 与 `Migration`
- 提交前跑一次 `./gradlew assembleDebug`，避免把不可编译的状态推上去
