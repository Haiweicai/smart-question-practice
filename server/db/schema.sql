-- ============================================================
-- 智能刷题 · 服务端表结构骨架
-- 所有表均可重复创建；字段语义与 App 端 network/BizApi.java 的 DTO 对齐。
-- ============================================================

-- ---------- 云端账号（8 位 publicId + 一机一绑） ----------
CREATE TABLE IF NOT EXISTS cloud_users (
  public_id      TEXT PRIMARY KEY,          -- 8 位公开 ID
  username       TEXT NOT NULL,
  password_md5   TEXT NOT NULL,
  phone          TEXT,
  bound_device   TEXT,                      -- 一机一绑
  created_at     INTEGER NOT NULL,
  last_login_at  INTEGER NOT NULL DEFAULT 0
);

-- ---------- 班级 ----------
CREATE TABLE IF NOT EXISTS classes (
  id            INTEGER PRIMARY KEY AUTOINCREMENT,
  name          TEXT NOT NULL,
  description   TEXT,
  school        TEXT,
  grade         TEXT,
  subject       TEXT,
  code          TEXT NOT NULL UNIQUE,       -- 加入班级用的邀请码
  owner_user_id TEXT NOT NULL,
  owner_device  TEXT,
  expire_at     INTEGER NOT NULL DEFAULT 0,
  created_at    INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS class_members (
  class_id     INTEGER NOT NULL,
  user_id      TEXT NOT NULL,
  device       TEXT NOT NULL DEFAULT '',
  username     TEXT,
  nickname     TEXT,
  role         TEXT NOT NULL DEFAULT 'student',  -- teacher / student
  joined_at    INTEGER NOT NULL,
  PRIMARY KEY (class_id, user_id, device)
);

-- 班级共享题库
CREATE TABLE IF NOT EXISTS class_banks (
  id               INTEGER PRIMARY KEY AUTOINCREMENT,
  class_id         INTEGER NOT NULL,
  source_bank_id   TEXT,
  bank_name        TEXT NOT NULL,
  bank_description TEXT,
  questions_json   TEXT NOT NULL,
  question_count   INTEGER NOT NULL DEFAULT 0,
  uploader_user_id TEXT NOT NULL,
  uploader_device  TEXT NOT NULL DEFAULT '',
  uploader_name    TEXT,
  created_at       INTEGER NOT NULL
);

-- ---------- 班级考试 ----------
CREATE TABLE IF NOT EXISTS class_exams (
  id                INTEGER PRIMARY KEY AUTOINCREMENT,
  class_id          INTEGER NOT NULL,
  name              TEXT NOT NULL,
  questions_json    TEXT NOT NULL,
  exam_config_json  TEXT,
  total_score       REAL NOT NULL DEFAULT 0,
  duration          INTEGER NOT NULL DEFAULT 0,
  lan_grading_mode  INTEGER NOT NULL DEFAULT 0,
  status            TEXT NOT NULL DEFAULT 'draft',   -- draft / published / closed
  ranking_published INTEGER NOT NULL DEFAULT 0,
  start_at          INTEGER,
  end_at            INTEGER,
  created_at        INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS exam_submissions (
  id                INTEGER PRIMARY KEY AUTOINCREMENT,
  exam_id           INTEGER NOT NULL,
  user_id           TEXT NOT NULL,
  device            TEXT NOT NULL DEFAULT '',
  username          TEXT,
  answers_json      TEXT,
  objective_score   REAL NOT NULL DEFAULT 0,
  subjective_score  REAL NOT NULL DEFAULT 0,
  total_score       REAL NOT NULL DEFAULT 0,
  correct_count     INTEGER NOT NULL DEFAULT 0,
  duration_used     INTEGER NOT NULL DEFAULT 0,
  switch_count      INTEGER NOT NULL DEFAULT 0,
  forced            INTEGER NOT NULL DEFAULT 0,
  needs_review      INTEGER NOT NULL DEFAULT 0,
  status            TEXT NOT NULL DEFAULT 'ongoing', -- ongoing / submitted / graded
  started_at        INTEGER NOT NULL DEFAULT 0,
  submitted_at      INTEGER NOT NULL DEFAULT 0,
  UNIQUE (exam_id, user_id, device)
);

-- ---------- 好友 ----------
CREATE TABLE IF NOT EXISTS friend_codes (
  user_id    TEXT NOT NULL,
  device     TEXT NOT NULL DEFAULT '',
  username   TEXT,
  code       TEXT NOT NULL UNIQUE,
  expire_at  INTEGER NOT NULL,
  PRIMARY KEY (user_id, device)
);

CREATE TABLE IF NOT EXISTS friends (
  user_id        TEXT NOT NULL,
  device         TEXT NOT NULL DEFAULT '',
  friend_user_id TEXT NOT NULL,
  friend_device  TEXT NOT NULL DEFAULT '',
  friend_name    TEXT,
  added_at       INTEGER NOT NULL,
  PRIMARY KEY (user_id, device, friend_user_id, friend_device)
);

CREATE TABLE IF NOT EXISTS friend_requests (
  id           INTEGER PRIMARY KEY AUTOINCREMENT,
  from_user_id TEXT NOT NULL,
  from_device  TEXT NOT NULL DEFAULT '',
  from_name    TEXT,
  to_user_id   TEXT NOT NULL,
  to_device    TEXT NOT NULL DEFAULT '',
  status       TEXT NOT NULL DEFAULT 'pending',  -- pending / accepted / rejected
  created_at   INTEGER NOT NULL
);

-- 好友之间直发的题库 / 消息
CREATE TABLE IF NOT EXISTS inbox_messages (
  id               INTEGER PRIMARY KEY AUTOINCREMENT,
  from_user_id     TEXT NOT NULL,
  from_device      TEXT NOT NULL DEFAULT '',
  from_name        TEXT,
  to_user_id       TEXT NOT NULL,
  to_device        TEXT NOT NULL DEFAULT '',
  type             TEXT NOT NULL DEFAULT 'bank',
  bank_name        TEXT,
  bank_description TEXT,
  questions_json   TEXT,
  question_count   INTEGER NOT NULL DEFAULT 0,
  read             INTEGER NOT NULL DEFAULT 0,
  accepted         INTEGER,
  created_at       INTEGER NOT NULL
);

-- ---------- 题库共享码 ----------
CREATE TABLE IF NOT EXISTS share_links (
  code             TEXT PRIMARY KEY,
  user_id          TEXT NOT NULL,
  device           TEXT NOT NULL DEFAULT '',
  username         TEXT,
  local_bank_id    TEXT,
  bank_name        TEXT NOT NULL,
  bank_description TEXT,
  questions_json   TEXT NOT NULL,
  question_count   INTEGER NOT NULL DEFAULT 0,
  pull_count       INTEGER NOT NULL DEFAULT 0,
  revoked          INTEGER NOT NULL DEFAULT 0,
  expire_at        INTEGER NOT NULL,
  created_at       INTEGER NOT NULL
);

-- ---------- 会员 ----------
CREATE TABLE IF NOT EXISTS vip_memberships (
  user_id       TEXT PRIMARY KEY,
  expire_at     INTEGER NOT NULL DEFAULT 0,
  monthly_count INTEGER NOT NULL DEFAULT 0,
  phone         TEXT
);

CREATE TABLE IF NOT EXISTS vip_whitelist (
  user_id    TEXT PRIMARY KEY,
  note       TEXT,
  created_at INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS vip_codes (
  code         TEXT PRIMARY KEY,
  days         INTEGER NOT NULL DEFAULT 30,
  used_by      TEXT,
  used_at      INTEGER,
  created_at   INTEGER NOT NULL
);

-- ---------- 班级学情 ----------
CREATE TABLE IF NOT EXISTS class_study_log (
  class_id   INTEGER NOT NULL,
  user_id    TEXT NOT NULL,
  device     TEXT NOT NULL DEFAULT '',
  day        TEXT NOT NULL,                 -- yyyy-MM-dd（UTC+8）
  seconds    INTEGER NOT NULL DEFAULT 0,
  last_seen  INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (class_id, user_id, device, day)
);

-- ---------- 使用统计（可选模块） ----------
CREATE TABLE IF NOT EXISTS heartbeats (
  user_id       TEXT NOT NULL,
  device        TEXT NOT NULL DEFAULT '',
  username      TEXT,
  app_version   TEXT,
  last_seen     INTEGER NOT NULL,
  PRIMARY KEY (user_id, device)
);

CREATE TABLE IF NOT EXISTS ai_usage (
  id                INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id           TEXT NOT NULL,
  device            TEXT NOT NULL DEFAULT '',
  username          TEXT,
  model             TEXT NOT NULL,
  scenario          TEXT,
  prompt_tokens     INTEGER NOT NULL DEFAULT 0,
  completion_tokens INTEGER NOT NULL DEFAULT 0,
  total_tokens      INTEGER NOT NULL DEFAULT 0,
  created_at        INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS crash_reports (
  id          INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id     TEXT,
  username    TEXT,
  app_version TEXT,
  device      TEXT,
  crash_time  INTEGER NOT NULL,
  content     TEXT,
  created_at  INTEGER NOT NULL
);

-- ---------- 索引 ----------
CREATE INDEX IF NOT EXISTS idx_class_members_user ON class_members(user_id);
CREATE INDEX IF NOT EXISTS idx_class_banks_class ON class_banks(class_id);
CREATE INDEX IF NOT EXISTS idx_exams_class ON class_exams(class_id);
CREATE INDEX IF NOT EXISTS idx_submissions_exam ON exam_submissions(exam_id);
CREATE INDEX IF NOT EXISTS idx_inbox_to ON inbox_messages(to_user_id, to_device);
CREATE INDEX IF NOT EXISTS idx_requests_to ON friend_requests(to_user_id, to_device);
CREATE INDEX IF NOT EXISTS idx_share_user ON share_links(user_id);
CREATE INDEX IF NOT EXISTS idx_study_class_day ON class_study_log(class_id, day);
