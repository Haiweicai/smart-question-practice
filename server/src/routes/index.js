/**
 * 路由表。
 *
 * 所有端点当前统一返回 501，响应体固定为：
 *   { ok: false, error: "not implemented", endpoint: "POST /api/class/create" }
 *
 * 实现方式：把对应路由的 TODO handler 换成真实逻辑即可，路径与 App 端
 * network/BizApi.java、StatsApi.java 一一对应，不需要改客户端。
 */
const express = require('express');

const router = express.Router();

/** 统一占位实现 */
function todo(method, path) {
  const label = `${method} ${path}`;
  const handler = (req, res) => {
    res.status(501).json({
      ok: false,
      error: 'not implemented',
      endpoint: label,
      hint: '在 server/src/routes/index.js 中替换该路由的 handler',
    });
  };
  if (method === 'GET') router.get(path, handler);
  else router.post(path, handler);
}

/** 批量注册：['POST','/api/x'] */
function batch(method, paths) {
  paths.forEach((p) => todo(method, p));
}

// ==================== 基础 ====================
batch('POST', [
  '/api/cloud/register',
  '/api/cloud/login',
  '/api/cloud/bind-phone',
  '/api/cloud/restore',
]);
batch('GET', ['/api/cloud/status']);

// ==================== 班级 ====================
batch('POST', [
  '/api/class/create',
  '/api/class/join',
  '/api/class/remove-member',
  '/api/class/dissolve',
  '/api/class/update',
  '/api/class/update-nickname',
  '/api/class/bank/upload',
  '/api/class/bank/delete',
  '/api/class/add-members',
  '/api/class/study/report',
]);
batch('GET', [
  '/api/class/search',
  '/api/class/members',
  '/api/class/list',
  '/api/class/detail',
  '/api/class/banks',
  '/api/class/bank/detail',
  '/api/class/study/today',
]);

// ==================== 好友 ====================
batch('POST', [
  '/api/friend/code',
  '/api/friend/add',
  '/api/friend/remove',
  '/api/friend/send-bank',
  '/api/friend/inbox/ack',
  '/api/friend/apply',
  '/api/friend/request/handle',
]);
batch('GET', ['/api/friend/list', '/api/friend/inbox', '/api/friend/requests']);

// ==================== 题库共享 ====================
batch('POST', [
  '/api/share/send',
  '/api/share/respond',
  '/api/share/toggle',
  '/api/share/upload',
  '/api/share/revoke',
]);
batch('GET', ['/api/share/pending', '/api/share/pull', '/api/share/list']);

// ==================== 班级考试 ====================
batch('POST', [
  '/api/class/share-exam',
  '/api/class/publish-exam',
  '/api/class/publish-ranking',
  '/api/class/close-exam',
  '/api/exam/submit',
  '/api/exam/grade-subjective',
  '/api/exam/grade-by-teacher',
]);
batch('GET', [
  '/api/class/exams',
  '/api/class/ranking',
  '/api/exam/detail',
  '/api/exam/my-result',
  '/api/exam/monitor',
]);

// ==================== 会员 ====================
batch('POST', ['/api/vip/redeem']);
batch('GET', ['/api/vip/status']);

// ==================== 使用统计（可选） ====================
batch('POST', ['/api/heartbeat', '/api/ai-usage', '/api/crash']);

// ==================== 版本更新（可选） ====================
batch('GET', ['/api/update/check']);

module.exports = router;
