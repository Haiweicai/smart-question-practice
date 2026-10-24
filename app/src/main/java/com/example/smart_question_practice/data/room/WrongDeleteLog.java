package com.example.smart_question_practice.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 错题删除日志（v17 新增）。
 *
 * 每次"移出错题本/清空"操作，按被删除的来源逐行记录：
 * - 一次用户操作共享同一个 batchId，支持一键撤销整批
 * - sourceType/sourceRef/sourceName/wrongCount/lastWrongTime 为来源快照，撤销时原样写回
 * - recordSoftRemoved 标记该来源删除后错题记录是否被一并软移除（最后一个来源被删时为 true）
 * - userId 即操作人（本机单用户体系下为当前登录用户）
 *
 * 数据隔离语义：考试视图删除只记录/删除 exam 来源；刷题来源不受影响。
 */
@Entity(tableName = "wrong_delete_log",
        indices = {@Index("userId"), @Index(value = {"userId", "batchId"})})
public class WrongDeleteLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    /** 同一次删除操作的批次号（时间戳字符串） */
    private String batchId;
    private long deleteTime;
    private String questionId;
    private String sourceType;
    private String sourceRef;
    private String bankId;
    private String sourceName;
    private int wrongCount;
    private long lastWrongTime;
    /** 删除该来源后 answer_record 是否被软移除（撤销时需要重新激活） */
    private boolean recordSoftRemoved;
    /** 是否已撤销，防止重复恢复 */
    @ColumnInfo(defaultValue = "0")
    private boolean restored;

    public WrongDeleteLog() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getBatchId() { return batchId; }
    public void setBatchId(String batchId) { this.batchId = batchId; }
    public long getDeleteTime() { return deleteTime; }
    public void setDeleteTime(long deleteTime) { this.deleteTime = deleteTime; }
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    public String getSourceRef() { return sourceRef; }
    public void setSourceRef(String sourceRef) { this.sourceRef = sourceRef; }
    public String getBankId() { return bankId; }
    public void setBankId(String bankId) { this.bankId = bankId; }
    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    public int getWrongCount() { return wrongCount; }
    public void setWrongCount(int wrongCount) { this.wrongCount = wrongCount; }
    public long getLastWrongTime() { return lastWrongTime; }
    public void setLastWrongTime(long lastWrongTime) { this.lastWrongTime = lastWrongTime; }
    public boolean isRecordSoftRemoved() { return recordSoftRemoved; }
    public void setRecordSoftRemoved(boolean recordSoftRemoved) { this.recordSoftRemoved = recordSoftRemoved; }
    public boolean isRestored() { return restored; }
    public void setRestored(boolean restored) { this.restored = restored; }
}
