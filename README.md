# 智能刷题（Android）

一个智能刷题 Android 应用：题库自建、五种刷题模式、模拟考试、错题本、收藏、学习统计、局域网联机考试，可选 AI 判卷与 AI 解析。

本仓库有两种用法：

- **普通用户（不想写代码）**：直接下载安装包使用 → 见下方「下载安装」和 [docs/USER_GUIDE.md](docs/USER_GUIDE.md)
- **开发者**：基于骨架源码二次开发 → 见下方「开发者快速开始」和 [docs/INTERFACES.md](docs/INTERFACES.md)

> 仓库内的**源码**是只有外壳、接口和数据结构的骨架版本。
> 业务实现（题库解析算法、AI 判分、班级/好友/考试的完整实现、服务端监控面板等）属于私有业务资产，不在仓库中。
> 面向普通用户的完整安装包，以 Releases 页面发布的 APK 为准。

## 下载安装（普通用户）

1. 打开 [Releases](https://github.com/Haiweicai/smart-question-practice/releases/latest) 页面；
2. 下载最新版 [`smart_question_practice_v10.85.41.apk`](https://github.com/Haiweicai/smart-question-practice/releases/download/v10.85.41/smart_question_practice_v10.85.41.apk)；
3. 传到安卓手机/平板后安装（Android 8.0 及以上，iOS 不支持）。

| 版本 | 大小 | 系统要求 | SHA-256 |
| --- | --- | --- | --- |
| v10.85.41 | 22.57 MB | Android 8.0+ | `9D7606F0231F38DD12269AC6C07A708839D865B71AB7A31F176BDDD8530512A1` |

安装步骤、五种刷题模式、模拟考试、局域网联机、AI 密钥配置、常见问题，全部写在
**[docs/USER_GUIDE.md](docs/USER_GUIDE.md)**。

## 框架长什么样（开发者）

启动后可以正常进入首页、切换底部导航、打开每个页面；点击任何业务按钮会提示「功能尚未实现」，
不会崩溃。所有需要你补的地方都用 `TODO` 和 `docs/INTERFACES.md` 标出来了。

## 开发者快速开始

### 1. 编译 App

需要：JDK 17、Android SDK（compileSdk 34）。

```bash
# 1) 在项目根目录创建 local.properties，指向你的 SDK
#    Windows 示例：sdk.dir=D\:\\Android SDK
#    macOS/Linux 示例：sdk.dir=/Users/you/Library/Android/sdk

# 2) 构建
./gradlew assembleDebug          # Windows: .\gradlew.bat assembleDebug
```

产物：`app/build/outputs/apk/debug/app-debug.apk`

### 2. 启动服务端

```bash
cd server
npm install
cp .env.example .env             # Windows: copy .env.example .env
npm start
```

看到 `listening on http://0.0.0.0:3000` 即成功，`curl http://127.0.0.1:3000/health` 应返回 `{"ok":true,...}`。

### 3. 首次运行

安装 APK 后：启动页 → 登录页（1.2 秒后自动进首页）→ 底部五个 Tab 都可切换。

> 登录页自动跳转只是为了让外壳先跑通，实际开发时删掉即可，见 `ui/LoginFragment.java`。

## 配置文件与密钥

仓库内**不含任何真实地址、密钥或手机号**。构建参数全部通过环境变量注入：

| 环境变量 | 用途 | 默认值 |
| --- | --- | --- |
| `SERVER_URL` | App 访问的服务端地址 | `http://127.0.0.1:3000` |
| `PANEL_TOKEN` | 管理口令（请求体 token 字段） | 空 |
| `ARK_API_KEY` | 火山方舟（豆包）密钥 | 空 |
| `DEEPSEEK_API_KEY` | DeepSeek 密钥 | 空 |

示例：

```bash
SERVER_URL=https://api.example.com PANEL_TOKEN=demo ./gradlew assembleDebug
```

服务端同理，见 `server/.env.example`。

## 目录结构

```
app/src/main/java/com/example/smart_question_practice/
├── App.java                 # Application，注入实现的入口
├── SplashActivity.java      # 启动页
├── MainActivity.java        # 底部导航 + Navigation 容器
├── core/
│   ├── Callback.java        # 统一异步回调
│   ├── ServiceLocator.java  # 服务注册表（可替换实现）
│   ├── contract/            # 13 个业务接口（你要实现的全部契约）
│   └── stub/                # NoOpServices：默认空实现
├── data/room/               # Room 实体 + DAO + AppDatabase
├── network/                 # Retrofit 接口与客户端（BizApi / StatsApi / AiQuestionApi）
└── ui/                      # 41 个页面（布局 + 空壳 Fragment）
server/                      # Node.js 服务端骨架（路由 + 表结构）
docs/                        # 使用说明 / 架构 / 接口 / 部署 / 二次开发文档
```

## 我要改哪里？

| 想做的事 | 看这里 |
| --- | --- |
| 作为普通用户下载、安装、使用 | [docs/USER_GUIDE.md](docs/USER_GUIDE.md) |
| 知道每个接口要做什么 | [docs/INTERFACES.md](docs/INTERFACES.md) |
| 知道服务端每个路由的字段 | [docs/API.md](docs/API.md) |
| 理解整体分层与数据流 | [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) |
| 部署服务端 / 打正式包 | [docs/DEPLOY.md](docs/DEPLOY.md) |
| 改包名、改品牌、加新模块 | [docs/CUSTOMIZE.md](docs/CUSTOMIZE.md) |

## 实现路线建议

1. **本地数据层**：`QuestionBankService` + `PracticeService`（Room DAO 已经写好，照着接）
2. **题库导入**：`QuestionParser`（PDF/Word/Excel/TXT → Question）
3. **判分**：`AnswerMatcher`（客观题），再选做 `AiGrader`（主观题）
4. **考试**：`ExamService`（组卷、交卷、历史）
5. **联网**：服务端账号 → 班级 → 好友 → 班级考试
6. **局域网考试**：`LanExamService`（需要自己设计发现与传输协议）

每完成一个，在 `App.onCreate()` 里注册：

```java
ServiceLocator.installBank(new MyQuestionBankService());
ServiceLocator.installPractice(new MyPracticeService());
```

## 已知限制

- 所有页面只有布局，没有 ViewModel / Adapter / 交互逻辑
- `QuestionParser`、`AnswerMatcher`、AI 相关接口没有实现
- 服务端全部返回 501，数据库仅有表结构
- 会员、监控面板等运营向功能不在骨架范围

## 许可

MIT，见 [LICENSE](LICENSE)。

## 声明

本项目仅用于学习与技术交流。请勿用于任何违反所在地区法律法规或学校规定的用途。
导入的题库内容版权归原作者所有。
