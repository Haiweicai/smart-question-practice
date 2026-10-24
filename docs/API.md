# HTTP 接口参考

客户端定义：`app/src/main/java/com/example/smart_question_practice/network/BizApi.java`
服务端骨架：`server/src/routes/index.js`（全部返回 501）

## 通用约定

- 传输：JSON，`Content-Type: application/json`
- 成功：`{"ok": true, ...}`；失败：`{"ok": false, "error": "原因"}`
- 需要鉴权的接口带 `token`（GET 放 query，POST 放 body），值来自构建参数 `PANEL_TOKEN`
- 用户身份用 `userId` + `device` 二元组（同一账号可在多设备登录，互不覆盖）
- 时间戳统一为**毫秒**，会员到期时间 `expireAt` 为**秒**
- 域名前缀由 `SERVER_URL` 决定，下文路径省略前缀

## 一、云端账号

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/cloud/register` | `username, passwordMd5, device` | `ok, publicId` |
| POST | `api/cloud/login` | `username, passwordMd5, device, publicId?` | `ok, publicId, rebound` |
| GET | `api/cloud/status` | `publicId, device, token` | `ok, active` |
| POST | `api/cloud/bind-phone` | `publicId, phone, device` | `ok, publicId` |
| POST | `api/cloud/restore` | `username, passwordMd5, device` | `ok, publicId` |

- `publicId` 为 8 位公开 ID，用于好友码/班级展示
- `rebound=true` 表示该账号已绑定其他设备，本次是换绑
- 服务端表：`cloud_users`

## 二、班级

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/class/create` | `name, description, school, grade, subject, userId, username, device, publicId` | `ok, classId, code` |
| GET | `api/class/search` | `code` | `ok, class{classId,name,teacherName,subject,memberCount,expireAt}` |
| POST | `api/class/join` | `code, userId, username, device, role` | `ok, classId` |
| GET | `api/class/list` | `userId, device` | `ok, classes[]` |
| GET | `api/class/detail` | `classId, userId, device` | `ok, class{classId,name,code,description,subject,teacherName,role,memberCount,bankCount,expireAt}` |
| GET | `api/class/members` | `classId` | `ok, total, members[]{userId,username,device,role,joinedAt,online,lastSeen}` |
| POST | `api/class/remove-member` | `classId, teacherUserId, teacherDevice, targetUserId, targetDevice` | `ok` |
| POST | `api/class/add-members` | `classId, teacherUserId, teacherDevice, members[]{userId,device,username}` | `ok, added, skipped` |
| POST | `api/class/dissolve` | `classId, teacherUserId, teacherDevice` | `ok` |
| POST | `api/class/update` | `classId, teacherUserId, teacherDevice, name, description` | `ok` |
| POST | `api/class/update-nickname` | `classId, userId, device, username` | `ok` |

### 班级共享题库

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/class/bank/upload` | `classId, teacherUserId, teacherDevice, username, sourceBankId, bankName, bankDescription, questionsJson` | `ok, id, duplicated` |
| GET | `api/class/banks` | `classId, userId, device` | `ok, banks[]{id,bankName,bankDescription,questionCount,uploaderUsername,sourceBankId,createdAt,mine}` |
| GET | `api/class/bank/detail` | `classId, id, userId, device` | `ok, bank{id,bankName,bankDescription,questionCount,uploaderUsername,questionsJson}` |
| POST | `api/class/bank/delete` | `classId, id, teacherUserId, teacherDevice` | `ok` |

### 班级学情

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/class/study/report` | `classId, userId, device, seconds` | `ok` |
| GET | `api/class/study/today` | `classId, userId, device` | `ok, day, totalSeconds, members[]{userId,device,username,role,seconds,online,lastSeen}` |

- 服务端表：`classes` / `class_members` / `class_banks` / `class_study_log`

## 三、好友

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/friend/code` | `userId, device, username` | `ok, code, expiresIn, ttlSec` |
| POST | `api/friend/apply` | `userId, device, username, friendCode` | `ok, autoAccepted, friendUsername` |
| GET | `api/friend/requests` | `userId, device` | `ok, requests[]{id,fromUserId,fromUsername,fromDevice,createdAt}` |
| POST | `api/friend/request/handle` | `userId, device, requestId, accept` | `ok` |
| GET | `api/friend/list` | `userId, device` | `ok, friends[]{friendUserId,friendUsername,friendDevice,addedAt,online,lastSeen}` |
| POST | `api/friend/remove` | `userId, device, friendUserId, friendDevice` | `ok` |
| POST | `api/friend/send-bank` | `userId, username, device, friendUserId, friendDevice, bankName, bankDescription, questionsJson` | `ok` |
| GET | `api/friend/inbox` | `userId, device` | `ok, messages[]{id,senderUsername,bankName,bankDescription,questionsJson,questionCount,sentAt}` |
| POST | `api/friend/inbox/ack` | `userId, device, messageId, accepted` | `ok` |

> 兼容字段：`api/friend/code` 旧版还有 `api/friend/add`（免审批直接加好友），骨架保留路由，可按需实现或废弃。

### 题库直达分享

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/share/send` | `userId, username, device, targets[]{friendUserId,friendDevice}, bankName, bankDescription, payloadJson` | `ok, sentCount` |
| GET | `api/share/pending` | `userId, device` | `ok, offers[]{id,senderUsername,bankName,bankDescription,payloadJson,questionCount,createdAt}` |
| POST | `api/share/respond` | `userId, device, offerId, accept` | `ok` |

### 共享码（跨网分享）

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/share/toggle` | `userId, device, localBankId, bankName, isShared` | `ok` |
| POST | `api/share/upload` | `userId, username, device, localBankId, bankName, bankDescription, questionsJson, expireDays` | `ok, shareCode, expireAt` |
| GET | `api/share/pull` | `code` | `ok, bank{bankName,bankDescription,senderUsername,questionsJson,questionCount,expireAt}` |
| POST | `api/share/revoke` | `userId, device, shareCode` | `ok` |
| GET | `api/share/list` | `userId, device` | `ok, shares[]{shareCode,bankName,createdAt,expireAt,pullCount,revoked}` |

- 服务端表：`friend_codes` / `friends` / `friend_requests` / `inbox_messages` / `share_links`

## 四、班级考试

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| POST | `api/class/share-exam` | `classId, teacherUserId, teacherDevice, examName, questionsJson, examConfig` | `ok, examId` |
| POST | `api/class/publish-exam` | `classId, examId, teacherUserId, teacherDevice, startAt, endAt, shuffle, lanGradingMode, targetUserIds[]` | `ok` |
| GET | `api/class/exams` | `classId, userId, device` | `ok, exams[]{examId,name,status,myStatus,totalQuestions,duration,submittedCount,totalStudents,participantCount,maxSwitchCount,hasSubjective,rankingPublished,totalScore,startAt,endAt,createdAt}` |
| GET | `api/exam/detail` | `examId, userId, device` | `ok, exam{examId,name,questionsJson,totalQuestions,totalScore,startAt,endAt,myStartTime,deadline,submissionId,examConfig}` |
| POST | `api/exam/submit` | `examId, submissionId, userId, device, answersJson, durationUsed, switchCount, forced` | `ok, result{objectiveScore,subjectiveScore,totalScore,accuracy,correctCount,objectiveTotal,needsReview,byType}` |
| GET | `api/exam/my-result` | `examId, userId, device` | `ok, result{examName,fullScore,totalScore,objectiveScore,subjectiveScore,accuracy,maxSwitchCount,durationUsed,switchCount,totalQuestions,answeredCount,forced,pendingReview,rankingPublished,byType,subjByType}` |
| POST | `api/exam/grade-subjective` | `submissionId, userId, device, gradedAnswers` | `ok, result{objectiveScore,subjectiveScore,totalScore,needsReview,status}` |
| POST | `api/exam/grade-by-teacher` | `examId, submissionId, teacherUserId, teacherDevice, gradedAnswers` | `ok, result{...}` |
| GET | `api/class/ranking` | `examId, userId, device` | `ok, ranking[]{rank,username,userId,device,totalScore,objectiveScore,subjectiveScore,accuracy,correctCount,durationUsed,switchCount,forced,isMe,submittedAt,status}, stats, myRank, totalStudents, submittedCount, myScore, averageScore, maxScore, minScore, avgDuration, rankingPublished` |
| POST | `api/class/publish-ranking` | `classId, examId, teacherUserId, teacherDevice` | `ok` |
| POST | `api/class/close-exam` | `classId, examId, teacherUserId, teacherDevice, action` | `ok` |
| GET | `api/exam/monitor` | `examId, teacherUserId, teacherDevice` | `ok, examStatus, startAt, endAt, students[]{userId,username,device,submissionStatus,switchCount,durationUsed,online,myStartTime,lastSeen,submittedAt,totalScore}, summary{totalStudents,notStarted,ongoing,submitted,forced}` |

### examConfig 字段

```json
{
  "mode": 1,
  "bankIds": "12,34",
  "singleCount": 20, "multiCount": 5, "judgeCount": 5, "fillCount": 5, "essayCount": 1,
  "duration": 60,
  "singleScore": 2, "multiScore": 3, "judgeScore": 1, "fillScore": 2, "essayScore": 10,
  "indefiniteMode": false,
  "shuffleOptions": true,
  "maxSwitchCount": 3,
  "lanGradingMode": 0
}
```

- 服务端表：`class_exams` / `exam_submissions`

## 五、会员

| 方法 | 路径 | 请求 | 响应 |
| --- | --- | --- | --- |
| GET | `api/vip/status` | `userId, device, publicId` | `ok, whitelisted, vipActive, expireAt, monthlyCount, nextPrice, serverTime, phone` |
| POST | `api/vip/redeem` | `userId, device, publicId, code` | `ok, expireAt, monthlyCount, nextPrice` |

- 服务端表：`vip_memberships` / `vip_whitelist` / `vip_codes`
- 价格与白名单策略由你决定，骨架只固定字段

## 六、使用统计（可选）

| 方法 | 路径 | 请求 |
| --- | --- | --- |
| POST | `api/heartbeat` | `{userId, appVersion, username, device, offline}` |
| POST | `api/ai-usage` | `{userId, username, device, model, scenario, promptTokens, completionTokens, totalTokens}` |
| POST | `api/crash` | `{userId, username, appVersion, device, crashTime, content}` |

这三个接口返回空响应（`Call<Void>`）。服务端表：`heartbeats` / `ai_usage` / `crash_reports`。

## 七、版本更新（可选）

| 方法 | 路径 | 响应 |
| --- | --- | --- |
| GET | `api/update/check?versionCode=1` | `{versionCode, versionName, downloadUrl, changelog, forceUpdate, sizeBytes}` |

## 题目 JSON 结构

`questionsJson` / `payloadJson` 中每题字段：

```json
{
  "id": "q_1700000000000_1",
  "type": "single",
  "stem": "以下哪项是正确的？",
  "options": "[\"A. ...\",\"B. ...\",\"C. ...\",\"D. ...\"]",
  "answer": "B",
  "analysis": "因为……",
  "difficulty": 2,
  "blanks": 0,
  "referenceAnswer": "",
  "fullScore": 2,
  "stemImages": "[]",
  "optionImages": "[]",
  "answerImages": "[]",
  "analysisImages": "[]"
}
```

- `type`：`single` / `multi` / `judge` / `fill` / `essay`
- `options`、`*Images` 为 JSON 数组字符串（与 Room 实体保持一致）
- 多选 `answer` 用连续字母，如 `AC`；填空 `answer` 用 `|` 分隔多空
