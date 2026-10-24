/**
 * SQLite 连接与建表。
 *
 * - 数据库文件位置：process.env.DB_FILE 或 server/data/app.db
 * - 表结构：server/db/schema.sql（全部使用 IF NOT EXISTS，可重复执行）
 *
 * 二次开发提示：
 *   - 生产环境请改用 PostgreSQL / MySQL，或把 SQLite 放到持久化磁盘
 *   - 所有查询建议写参数化语句（db.prepare(...).run/get/all），不要拼字符串
 */
const fs = require('fs');
const path = require('path');
const Database = require('better-sqlite3');

const DATA_DIR = path.join(__dirname, '..', 'data');
const SCHEMA_FILE = path.join(__dirname, '..', 'db', 'schema.sql');

let db = null;

function getDb() {
  if (db) return db;

  if (!fs.existsSync(DATA_DIR)) {
    fs.mkdirSync(DATA_DIR, { recursive: true });
  }

  const file = process.env.DB_FILE || path.join(DATA_DIR, 'app.db');
  db = new Database(file);
  db.pragma('journal_mode = WAL');
  db.pragma('foreign_keys = ON');

  const schema = fs.readFileSync(SCHEMA_FILE, 'utf8');
  db.exec(schema);

  return db;
}

module.exports = { getDb };
