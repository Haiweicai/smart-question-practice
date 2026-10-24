package com.example.smart_question_practice.data.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

/**
 * 错题来源记录（v16 新增）。
 *
 * 同一道题可能在多个场景答错：
 * - sourceType="practice"：刷题模式答错，sourceRef=题库ID（bankId），sourceName=题库名快照
 * - sourceType="exam"：考试答错，sourceRef=组卷配置ID（examConfigId 字符串），sourceName=试卷名快照
 *
 * 每个（题目 × 用户 × 来源类型 × 来源对象）一行，重复答错累计 wrongCount。
 * bankId 记录题目所属题库，用于错题本按题库筛选时只显示该题库相关来源。
 */
@Entity(tableName = "wrong_source",
        primaryKeys = {"questionId", "userId", "sourceType", "sourceRef"},
        indices = {@Index("userId"), @Index("bankId")})
public class WrongSource {

    public static final String TYPE_PRACTICE = "practice";
    public static final String TYPE_EXAM = "exam";

    @NonNull
    private String questionId;
    private int userId;
    @NonNull
    private String sourceType;
    /** practice → bankId；exam → examConfigId 字符串 */
    @NonNull
    private String sourceRef;
    /** 题目所属题库（筛选联动用） */
    private String bankId;
    /** 来源名称快照：题库名 / 组卷时取的试卷名（可空，刷题来源展示时会以题库现名兜底） */
    private String sourceName;
    private int wrongCount;
    private long lastWrongTime;

    public WrongSource() {}

    @androidx.room.Ignore
    public WrongSource(@NonNull String questionId, int userId, @NonNull String sourceType,
                       @NonNull String sourceRef, String bankId, String sourceName,
                       int wrongCount, long lastWrongTime) {
        this.questionId = questionId;
        this.userId = userId;
        this.sourceType = sourceType;
        this.sourceRef = sourceRef;
        this.bankId = bankId;
        this.sourceName = sourceName;
        this.wrongCount = wrongCount;
        this.lastWrongTime = lastWrongTime;
    }

    @NonNull
    public String getQuestionId() { return questionId; }
    public void setQuestionId(@NonNull String questionId) { this.questionId = questionId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    @NonNull
    public String getSourceType() { return sourceType; }
    public void setSourceType(@NonNull String sourceType) { this.sourceType = sourceType; }
    @NonNull
    public String getSourceRef() { return sourceRef; }
    public void setSourceRef(@NonNull String sourceRef) { this.sourceRef = sourceRef; }
    public String getBankId() { return bankId; }
    public void setBankId(String bankId) { this.bankId = bankId; }
    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    public int getWrongCount() { return wrongCount; }
    public void setWrongCount(int wrongCount) { this.wrongCount = wrongCount; }
    public long getLastWrongTime() { return lastWrongTime; }
    public void setLastWrongTime(long lastWrongTime) { this.lastWrongTime = lastWrongTime; }
}
