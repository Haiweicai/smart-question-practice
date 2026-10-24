package com.example.smart_question_practice.data.room;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 局域网考试会话。同一 sessionId 在发起端与考生端各存一条本地记录。
 * String 字段可空（Room 迁移铁律：可空列不加 NOT NULL、无 DEFAULT）。
 */
@Entity(tableName = "lan_exam_session",
        indices = {@Index("sessionId"), @Index("localUserId"), @Index("status")})
public class LanExamSession {
    public static final int STATUS_PENDING = 0;    // 待开始
    public static final int STATUS_ONGOING = 1;    // 进行中
    public static final int STATUS_FINISHED = 2;   // 已结束
    public static final int STATUS_CANCELLED = 3;  // 已取消

    public static final int MODE_TOGETHER = 0;     // 与考生一起考试（旧称，= 考生自行批阅）
    public static final int MODE_MONITOR = 1;      // 仅监督考试（旧称，= 发卷人批阅）

    /** 批阅方式（改版后语义，与 MODE_* 同值，会话 mode 列直接复用） */
    public static final int GRADING_SELF = 0;      // 考生自行批阅（发卷人一同考试）
    public static final int GRADING_HOST = 1;      // 发卷人批阅（发卷人不参与考试）

    public static final int ROLE_HOST = 0;         // 发起人
    public static final int ROLE_STUDENT = 1;      // 考生

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String sessionId;        // 两端共享的会话 ID
    private int hostUserId;          // 发起人 userId（考生端未知时为 -1）
    private String hostUsername;
    private String hostIp;
    private int localUserId;         // 本机用户 ID（两端各自记录，用于"我的考试记录"过滤）
    private int mode;
    private int role;
    private int status;
    private long startTime;
    private long endTime;
    private int durationMinutes;
    private int maxSwitchCount;
    private int totalQuestions;
    private String examName;
    private String participantsJson; // 参与考生快照（阶段4填充）
    private String rankJson;         // 排行榜结果（阶段5填充）

    public LanExamSession() {
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public int getHostUserId() { return hostUserId; }
    public void setHostUserId(int hostUserId) { this.hostUserId = hostUserId; }
    public String getHostUsername() { return hostUsername; }
    public void setHostUsername(String hostUsername) { this.hostUsername = hostUsername; }
    public String getHostIp() { return hostIp; }
    public void setHostIp(String hostIp) { this.hostIp = hostIp; }
    public int getLocalUserId() { return localUserId; }
    public void setLocalUserId(int localUserId) { this.localUserId = localUserId; }
    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }
    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public int getMaxSwitchCount() { return maxSwitchCount; }
    public void setMaxSwitchCount(int maxSwitchCount) { this.maxSwitchCount = maxSwitchCount; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public String getParticipantsJson() { return participantsJson; }
    public void setParticipantsJson(String participantsJson) { this.participantsJson = participantsJson; }
    public String getRankJson() { return rankJson; }
    public void setRankJson(String rankJson) { this.rankJson = rankJson; }
}
