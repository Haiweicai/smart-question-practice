# 服务端骨架（Node.js + Express + SQLite）

这是一个**只有路由表与表结构**的服务端骨架，所有接口默认返回 `501 not implemented`。
它的作用是把 App 需要的 HTTP 契约固定下来，你只需要按自己的产品逻辑填充实现。

## 目录

```
server/
├── src/
│   ├── app.js           # 入口：中间件、健康检查、路由挂载
│   ├── db.js            # SQLite 连接 + 自动建表
│   └── routes/
│       └── index.js     # 全部路由（501 占位）
├── db/
│   └── schema.sql       # 表结构
├── data/                # 运行时生成的 app.db（已 gitignore）
├── .env.example
└── package.json
```

## 启动

```bash
cd server
npm install
cp .env.example .env      # Windows: copy .env.example .env
npm start
```

然后访问：

```bash
curl http://127.0.0.1:3000/health
# {"ok":true,"ts":...,"version":"1.0.0-skeleton"}

curl -X POST http://127.0.0.1:3000/api/class/create
# 501 {"ok":false,"error":"not implemented","endpoint":"POST /api/class/create",...}
```

数据库会在首次启动时自动按 `db/schema.sql` 建表，文件位于 `server/data/app.db`。

## 实现一个接口

以「创建班级」为例，在 `src/routes/index.js` 里把占位注册替换掉：

```js
const { getDb } = require('../db');

router.post('/api/class/create', (req, res) => {
  const db = getDb();
  const { name, description, code, userId, device } = req.body;
  if (!name || !code) {
    return res.status(400).json({ ok: false, error: 'name/code required' });
  }
  const now = Date.now();
  const info = db.prepare(
    'INSERT INTO classes (name, description, code, owner_user_id, owner_device, created_at) VALUES (?,?,?,?,?,?)'
  ).run(name, description || '', code, userId || '', device || '', now);
  res.json({ ok: true, classId: info.lastInsertRowid });
});
```

删掉 `batch('POST', ['/api/class/create', ...])` 里对应的字符串即可，避免路由重复注册。

## 请求约定

- 请求/响应均为 JSON（`Content-Type: application/json`）
- 字段名与 App 端 `network/BizApi.java` 的 DTO 完全一致（camelCase）
- 统一响应：成功 `{ "ok": true, ... }`，失败 `{ "ok": false, "error": "原因" }`
- 客户端把 `PANEL_TOKEN` 放在参数或请求体的 `token` 字段里；应在服务端加中间件校验

## 二次开发建议

1. **先做账号**：`/api/cloud/register`、`/api/cloud/login`、`/api/cloud/status`
2. **再做班级**：创建 / 搜索 / 加入 / 成员列表
3. **考试模块最复杂**：组卷 → 发布 → 提交 → 阅卷 → 排行，建议单独拆 `exam.js`
4. **安全**：密码用 bcrypt，不要存明文或裸 MD5；token 校验必须服务端做；限制请求体大小与频率
5. **部署**：详见根目录 [docs/DEPLOY.md](../docs/DEPLOY.md)

## 与旧版服务端的区别

旧版实现（含监控面板、激活码体系）属于私有业务代码，未包含在本仓库中。本骨架只保留：

- 路由路径与请求/响应字段
- 数据库中需要的表与字段
- 启动与健康检查

这样别人能部署、能联调，但拿不到你的具体业务实现。
