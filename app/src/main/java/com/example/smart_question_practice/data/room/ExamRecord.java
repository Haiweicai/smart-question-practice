package com.example.smart_question_practice.data.room;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 模拟考试主记录表，关联配置与答题明细，支撑考试恢复和历史统计。
 * questionIds / questionTypes 为 JSON 数组字符串（题目 String ID 与题型）。
 * status: 0=进行中 1=已交卷 2=强制交卷 3=已阅卷
 *
 * 局域网标记 lanFlag：
 * 0=独立考试（普通考试/发卷人自考），1=被邀请考试（考生端），2=考生答卷（发卷人批阅模式，代考生保存）
 */
@Entity(tableName = "exam_record",
        indices = {@Index("userId"), @Index("examConfigId"), @Index("status")})
public class ExamRecord {
    public static final int STATUS_ONGOING = 0;
    public static final int STATUS_SUBMITTED = 1;
    public static final int STATUS_FORCED = 2;
    public static final int STATUS_REVIEWED = 3;

    /** 局域网标记：0=独立考试 1=被邀请考试（考生端） 2=考生答卷（发卷人批阅） */
    public static final int LAN_INDEPENDENT = 0;
    public static final int LAN_STUDENT = 1;
    public static final int LAN_HOST_PAPER = 2;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long examConfigId;    // 关联 ExamConfig.id
    private int userId;           // 所属用户
    private long startTime;       // 开始时间戳（ms）
    private long endTime;         // 结束时间戳（ms），未结束为 0
    private int durationUsed;     // 实际用时（秒）
    private int switchCount;      // 实际切后台次数
    private int status;           // 状态
    private float objectiveScore; // 客观题得分
    private float subjectiveScore;// 主观题得分
    private float totalScore;     // 总分
    private int totalQuestions;   // 总题数
    private String questionIds;   // JSON 数组，本次考试题目 ID 按顺序排列
    private String questionTypes; // JSON 数组，对应每道题的题型
    private boolean forcedSubmit; // 是否因切后台超限被强制交卷
    private int lanFlag;          // 局域网标记（LAN_INDEPENDENT/LAN_STUDENT/LAN_HOST_PAPER）
    private String lanStudentName;// lanFlag=2 时对应考生用户名
    private String lanSessionId;  // 局域网会话 ID（两端关联排行榜缓存用）
    private String optionOrders;  // 选项随机排列快照 JSON：{questionId:[displayPos→originalPos]}，null=未启用

    public ExamRecord() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getExamConfigId() { return examConfigId; }
    public void setExamConfigId(long examConfigId) { this.examConfigId = examConfigId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }
    public int getDurationUsed() { return durationUsed; }
    public void setDurationUsed(int durationUsed) { this.durationUsed = durationUsed; }
    public int getSwitchCount() { return switchCount; }
    public void setSwitchCount(int switchCount) { this.switchCount = switchCount; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public float getObjectiveScore() { return objectiveScore; }
    public void setObjectiveScore(float objectiveScore) { this.objectiveScore = objectiveScore; }
    public float getSubjectiveScore() { return subjectiveScore; }
    public void setSubjectiveScore(float subjectiveScore) { this.subjectiveScore = subjectiveScore; }
    public float getTotalScore() { return totalScore; }
    public void setTotalScore(float totalScore) { this.totalScore = totalScore; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public String getQuestionIds() { return questionIds; }
    public void setQuestionIds(String questionIds) { this.questionIds = questionIds; }
    public String getQuestionTypes() { return questionTypes; }
    public void setQuestionTypes(String questionTypes) { this.questionTypes = questionTypes; }
    public boolean isForcedSubmit() { return forcedSubmit; }
    public void setForcedSubmit(boolean forcedSubmit) { this.forcedSubmit = forcedSubmit; }
    public int getLanFlag() { return lanFlag; }
    public void setLanFlag(int lanFlag) { this.lanFlag = lanFlag; }
    public String getLanStudentName() { return lanStudentName; }
    public void setLanStudentName(String lanStudentName) { this.lanStudentName = lanStudentName; }
    public String getLanSessionId() { return lanSessionId; }
    public void setLanSessionId(String lanSessionId) { this.lanSessionId = lanSessionId; }
    public String getOptionOrders() { return optionOrders; }
    public void setOptionOrders(String optionOrders) { this.optionOrders = optionOrders; }
}
