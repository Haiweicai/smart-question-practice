package com.example.smart_question_practice.data.room;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 考试答题明细表：每道题的作答情况，实时写入，支撑进度恢复和判分。
 * questionId / questionType 与 Question 表一致，均为 String 类型。
 * 选择题存 "AB"，判断存 "正确"/"错误"，填空多空以 | 分隔，简答存文本。
 */
@Entity(tableName = "exam_answer",
        indices = {@Index("examRecordId"), @Index("questionId")})
public class ExamAnswer {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long examRecordId;    // 关联 ExamRecord.id
    private String questionId;    // 关联 Question.id
    private int questionIndex;    // 在试卷中的序号（0-based）
    private String questionType;  // 题型（复用 Question.type："single"等）
    private String userAnswer;    // 用户答案
    private boolean isAnswered;   // 是否已作答
    private Boolean isCorrect;    // 客观题判分结果，主观题为 null
    private Float score;          // 得分，主观题阅卷后填写，客观题判分后填写
    private boolean isObjective;  // 是否客观题（single/multiple/judge）
    private long answeredAt;      // 作答时间戳

    public ExamAnswer() {}

    public static boolean isObjectiveType(String type) {
        return "single".equals(type) || "multiple".equals(type) || "judge".equals(type);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getExamRecordId() { return examRecordId; }
    public void setExamRecordId(long examRecordId) { this.examRecordId = examRecordId; }
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
    public int getQuestionIndex() { return questionIndex; }
    public void setQuestionIndex(int questionIndex) { this.questionIndex = questionIndex; }
    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
    public boolean isAnswered() { return isAnswered; }
    public void setAnswered(boolean answered) { isAnswered = answered; }
    public Boolean getCorrect() { return isCorrect; }
    public void setCorrect(Boolean correct) { isCorrect = correct; }
    public Float getScore() { return score; }
    public void setScore(Float score) { this.score = score; }
    public boolean isObjective() { return isObjective; }
    public void setObjective(boolean objective) { isObjective = objective; }
    public long getAnsweredAt() { return answeredAt; }
    public void setAnsweredAt(long answeredAt) { this.answeredAt = answeredAt; }
}
