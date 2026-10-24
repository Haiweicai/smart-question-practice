package com.example.smart_question_practice.data.room;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_record", indices = {@Index(value = {"bankId"}), @Index(value = {"questionId"}, unique = true), @Index(value = {"questionType"}), @Index(value = {"userId"})})
public class AnswerRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String bankId;
    private String questionId;
    private String questionType;
    private String userAnswer;
    private boolean isCorrect;
    private int score;
    private long answerTime;
    private long lastAnswerTime;
    private boolean isFavorite;
    private int wrongCount;
    private int correctCount;
    /** 重做次数：在错题本（错题模式）中该题被重做的累计次数，用于错题自动删除阈值判断 */
    @androidx.room.ColumnInfo(defaultValue = "0")
    private int retryCount;
    private boolean isRemovedFromWrong;
    private int userId;
    /** AI 主观题评分（1~10），仅刷题模式 AI 评判后写入；客观题/旧数据为 null（可空列，无 DEFAULT） */
    private Integer aiScore;

    public AnswerRecord() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBankId() { return bankId; }
    public void setBankId(String bankId) { this.bankId = bankId; }
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public long getAnswerTime() { return answerTime; }
    public void setAnswerTime(long answerTime) { this.answerTime = answerTime; }
    public long getLastAnswerTime() { return lastAnswerTime; }
    public void setLastAnswerTime(long lastAnswerTime) { this.lastAnswerTime = lastAnswerTime; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public int getWrongCount() { return wrongCount; }
    public void setWrongCount(int wrongCount) { this.wrongCount = wrongCount; }
    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }
    public int getRetryCount() { return retryCount; }
    public void setRetryCount(int retryCount) { this.retryCount = retryCount; }
    public boolean isRemovedFromWrong() { return isRemovedFromWrong; }
    public void setRemovedFromWrong(boolean removedFromWrong) { isRemovedFromWrong = removedFromWrong; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getAiScore() { return aiScore; }
    public void setAiScore(Integer aiScore) { this.aiScore = aiScore; }
}