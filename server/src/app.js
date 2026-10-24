/**
 * 智能刷题服务端骨架入口。
 *
 * 只做三件事：
 *   1. 加载环境变量
 *   2. 挂载路由表（全部返回 501，等待你实现）
 *   3. 启动 HTTP 服务
 *
 * 实现业务时建议按 routes/ 下的模块拆分，把数据库访问收敛到 db.js。
 */
require('dotenv').config();

const express = require('express');
const { getDb } = require('./db');
const routes = require('./routes');

const app = express();
const PORT = Number(process.env.PORT || 3000);

app.set('trust proxy', true);
app.use(express.json({ limit: '8mb' }));

// 允许 App / 浏览器跨域访问
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Methods', 'GET,POST,OPTIONS');
  res.header('Access-Control-Allow-Headers', 'Content-Type,Authorization');
  if (req.method === 'OPTIONS') return res.status(204).end();
  next();
});

// 健康检查（部署后先用它确认服务活着）
app.get('/health', (req, res) => {
  res.json({ ok: true, ts: Date.now(), version: require('../package.json').version });
});

app.use(routes);

// 统一 404
app.use((req, res) => {
  res.status(404).json({ ok: false, error: 'not found: ' + req.method + ' ' + req.path });
});

// 统一错误处理
app.use((err, req, res, next) => {
  console.error('[error]', err);
  res.status(500).json({ ok: false, error: 'internal error' });
});

// 提前初始化数据库，避免首次请求时才建表
getDb();

app.listen(PORT, () => {
  console.log(`[smart-question-practice] listening on http://0.0.0.0:${PORT}`);
});
