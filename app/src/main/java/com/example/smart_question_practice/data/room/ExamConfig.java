package com.example.smart_question_practice.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 模拟考试配置表：保存用户组卷配置，支持复用与历史查看。
 * bankIds 为 JSON 数组字符串，如 ["1","3","5"]。
 */
@Entity(tableName = "exam_config", indices = {@Index("userId")})
public class ExamConfig {
    public static final int MODE_SINGLE_BANK = 1;
    public static final int MODE_MULTI_BANK = 2;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private int userId;          // 所属用户
    private String title;        // 考试名称
    private int mode;            // 1=单题库 2=多题库
    private String bankIds;      // JSON 数组，选中的题库 ID 列表
    private int singleCount;     // 单选题数
    private int multiCount;      // 多选题数
    private int judgeCount;      // 判断题数
    private int fillCount;       // 填空题数
    private int essayCount;      // 简答题数
    private int duration;        // 考试时长（分钟）
    private float singleScore;   // 单选每题分值
    private float multiScore;    // 多选每题分值
    private float judgeScore;    // 判断每题分值
    @ColumnInfo(defaultValue = "0")
    private float fillScore;     // 填空每题分值（主观题，预设上限）
    @ColumnInfo(defaultValue = "0")
    private float essayScore;    // 简答每题分值（主观题，预设上限）
    @ColumnInfo(defaultValue = "0") private boolean indefiniteMode; // 不定项选择模式：隐藏题型提示、统一复选框样式
    @ColumnInfo(defaultValue = "0") private boolean shuffleOptions; // 选择题选项顺序随机：单选/多选选项按场乱序
    private int maxSwitchCount;  // 允许切后台次数（0=不允许）
    private long createdAt;      // 创建时间戳
    /** 局域网批阅方式：0=考生自行批阅（默认） 1=发卷人批阅（考生交卷后等待发卷人批阅，不自行阅卷） */
    @ColumnInfo(defaultValue = "0") private int lanGradingMode;

    public ExamConfig() {
        this.singleScore = 1f;
        this.multiScore = 2f;
        this.judgeScore = 1f;
        this.fillScore = 5f;
        this.essayScore = 10f;
        this.duration = 60;
        this.maxSwitchCount = 3;  // 默认允许切屏 3 次，避免 LAN 考试中 0 次导致误强制交卷
    }

    /** 计算试卷总题数 */
    public int totalQuestions() {
        return singleCount + multiCount + judgeCount + fillCount + essayCount;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }
    public String getBankIds() { return bankIds; }
    public void setBankIds(String bankIds) { this.bankIds = bankIds; }
    public int getSingleCount() { return singleCount; }
    public void setSingleCount(int singleCount) { this.singleCount = singleCount; }
    public int getMultiCount() { return multiCount; }
    public void setMultiCount(int multiCount) { this.multiCount = multiCount; }
    public int getJudgeCount() { return judgeCount; }
    public void setJudgeCount(int judgeCount) { this.judgeCount = judgeCount; }
    public int getFillCount() { return fillCount; }
    public void setFillCount(int fillCount) { this.fillCount = fillCount; }
    public int getEssayCount() { return essayCount; }
    public void setEssayCount(int essayCount) { this.essayCount = essayCount; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public float getSingleScore() { return singleScore; }
    public void setSingleScore(float singleScore) { this.singleScore = singleScore; }
    public float getMultiScore() { return multiScore; }
    public void setMultiScore(float multiScore) { this.multiScore = multiScore; }
    public float getJudgeScore() { return judgeScore; }
    public void setJudgeScore(float judgeScore) { this.judgeScore = judgeScore; }
    public float getFillScore() { return fillScore; }
    public void setFillScore(float fillScore) { this.fillScore = fillScore; }
    public float getEssayScore() { return essayScore; }
    public void setEssayScore(float essayScore) { this.essayScore = essayScore; }
    public boolean isIndefiniteMode() { return indefiniteMode; }
    public void setIndefiniteMode(boolean indefiniteMode) { this.indefiniteMode = indefiniteMode; }
    public boolean isShuffleOptions() { return shuffleOptions; }
    public void setShuffleOptions(boolean shuffleOptions) { this.shuffleOptions = shuffleOptions; }
    public int getMaxSwitchCount() { return maxSwitchCount; }
    public void setMaxSwitchCount(int maxSwitchCount) { this.maxSwitchCount = maxSwitchCount; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public int getLanGradingMode() { return lanGradingMode; }
    public void setLanGradingMode(int lanGradingMode) { this.lanGradingMode = lanGradingMode; }

    /** 计算试卷满分 */
    public float totalScore() {
        return singleCount * singleScore + multiCount * multiScore + judgeCount * judgeScore
                + fillCount * fillScore + essayCount * essayScore;
    }
}
