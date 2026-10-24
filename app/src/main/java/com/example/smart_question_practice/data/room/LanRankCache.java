package com.example.smart_question_practice.data.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 考生端排行榜缓存（阶段5改版）：发卷人发布排行榜（RANK_PUBLISH）后，
 * 考生端按 sessionId 缓存一份，供考试记录徽标与结果页排行榜模块离线查看。
 */
@Entity(tableName = "lan_rank_cache")
public class LanRankCache {

    @PrimaryKey
    @NonNull
    private String sessionId = "";   // 会话 ID（主键，重复发布直接覆盖）
    private String examName;    // 考试名称
    private String rankJson;    // List<ExamRankEntry> 序列化 JSON
    private long timestamp;     // 发布时间

    public LanRankCache() {
    }

    @NonNull
    public String getSessionId() { return sessionId; }
    public void setSessionId(@NonNull String sessionId) { this.sessionId = sessionId; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public String getRankJson() { return rankJson; }
    public void setRankJson(String rankJson) { this.rankJson = rankJson; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
