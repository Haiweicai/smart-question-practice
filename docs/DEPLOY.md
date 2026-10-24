# 部署指南

## 一、服务端

### 1. 环境要求

- Node.js 18+（推荐 20 LTS）
- 能读写 `server/data/` 的磁盘权限
- better-sqlite3 是原生模块，需要编译工具链：
  - Debian/Ubuntu：`sudo apt install -y build-essential python3`
  - CentOS：`sudo yum groupinstall -y "Development Tools"`
  - Windows：安装 Visual Studio Build Tools

### 2. 本地启动

```bash
cd server
npm install
cp .env.example .env
npm start
```

验证：

```bash
curl http://127.0.0.1:3000/health
curl -X POST http://127.0.0.1:3000/api/cloud/login -H "Content-Type: application/json" -d "{}"
```

第二条应返回 `501`，说明路由已挂载但还没实现——这是正常状态。

### 3. 生产部署（systemd + Nginx）

```ini
# /etc/systemd/system/smart-question.service
[Unit]
Description=smart-question-practice server
After=network.target

[Service]
Type=simple
WorkingDirectory=/opt/smart-question/server
EnvironmentFile=/opt/smart-question/server/.env
ExecStart=/usr/bin/node src/app.js
Restart=always
RestartSec=3
User=www-data

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl daemon-reload
sudo systemctl enable --now smart-question
sudo systemctl status smart-question
```

Nginx 反向代理（务必上 HTTPS）：

```nginx
server {
    listen 443 ssl;
    server_name api.example.com;

    ssl_certificate     /etc/letsencrypt/live/api.example.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/api.example.com/privkey.pem;

    client_max_body_size 16m;

    location / {
        proxy_pass http://127.0.0.1:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

> 注意：`app.js` 已经 `app.set('trust proxy', true)`，取真实 IP 用 `X-Forwarded-For`。

### 4. 数据备份

SQLite 是单文件，直接备份即可：

```bash
sqlite3 server/data/app.db ".backup '/backup/app-$(date +%F).db'"
```

或者用 cron 每日打包 `server/data/`。

## 二、Android

### 1. Debug 包

```bash
# Windows PowerShell
$env:SERVER_URL="https://api.example.com"
$env:PANEL_TOKEN="your-token"
.\gradlew.bat assembleDebug
```

```bash
# macOS / Linux
SERVER_URL=https://api.example.com PANEL_TOKEN=your-token ./gradlew assembleDebug
```

产物：`app/build/outputs/apk/debug/app-debug.apk`

### 2. Release 包

1. 生成签名文件（只需一次）：

```bash
keytool -genkeypair -v -keystore release.jks -alias app \
  -keyalg RSA -keysize 2048 -validity 10000
```

2. 在 `~/.gradle/gradle.properties`（**不要放进仓库**）配置：

```properties
RELEASE_STORE_FILE=/absolute/path/release.jks
RELEASE_STORE_PASSWORD=***
RELEASE_KEY_ALIAS=app
RELEASE_KEY_PASSWORD=***
```

3. 在 `app/build.gradle` 的 `android { signingConfigs { ... } }` 中引用上述变量，并让 `release` 使用它。

4. 构建：

```bash
SERVER_URL=https://api.example.com PANEL_TOKEN=your-token ./gradlew assembleRelease
```

`.gitignore` 已排除 `*.jks`、`*.keystore`、`*.apk`、`local.properties`。

### 3. 网络配置

- 默认 `SERVER_URL` 是 `http://127.0.0.1:3000`，只适合模拟器/本机联调
- `AndroidManifest.xml` 已开启 `usesCleartextTraffic="true"`；正式发布建议改成 HTTPS 并把它置为 `false`
- 若用自签证书，请配置 `res/xml/network_security_config.xml` 并引用

### 4. 密钥安全

| 做法 | 说明 |
| --- | --- |
| 构建参数注入 | 推荐：CI 里设置环境变量 |
| 本地 `gradle.properties` | 只放本机，仓库忽略 |
| 写死在代码/资源里 | **不要**，泄露后无法撤回 |

AI 密钥更稳妥的做法是放到服务端，App 只调用自己的接口，由服务端转发大模型请求。

## 三、发布检查清单

- [ ] `SERVER_URL` 指向正式域名且为 HTTPS
- [ ] `PANEL_TOKEN` 已更换且足够随机
- [ ] 服务端 `.env` 不在仓库里
- [ ] `curl /health` 正常
- [ ] App 能注册/登录（实现后）
- [ ] 弱网、断网、超时三种场景不崩溃
- [ ] 隐私政策与实际采集的数据一致
- [ ] 提审前删除测试账号与测试班级数据
