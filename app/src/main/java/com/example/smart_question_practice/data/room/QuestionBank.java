package com.example.smart_question_practice.data.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_bank", indices = {@Index(value = {"isDeleted"}), @Index(value = {"userId"})})
public class QuestionBank {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String description;
    private long createTime;
    private long updateTime;
    private int questionCount;
    private String typeStats;
    private String tags;
    private boolean isDeleted;
    private int userId;
    /** 是否对外共享（跨网分享/好友直达前置条件，默认私有） */
    private boolean isShared;

    public QuestionBank() {}

    @Ignore
    public QuestionBank(String id, String name, int questionCount) {
        this.id = id;
        this.name = name;
        this.questionCount = questionCount;
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
        this.isDeleted = false;
        this.isShared = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public long getCreateTime() { return createTime; }
    public void setCreateTime(long createTime) { this.createTime = createTime; }
    public long getUpdateTime() { return updateTime; }
    public void setUpdateTime(long updateTime) { this.updateTime = updateTime; }
    public int getQuestionCount() { return questionCount; }
    public void setQuestionCount(int questionCount) { this.questionCount = questionCount; }
    public String getTypeStats() { return typeStats; }
    public void setTypeStats(String typeStats) { this.typeStats = typeStats; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public boolean isShared() { return isShared; }
    public void setShared(boolean shared) { isShared = shared; }
}