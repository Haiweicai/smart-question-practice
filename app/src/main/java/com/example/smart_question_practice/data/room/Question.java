package com.example.smart_question_practice.data.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "question", indices = {@Index(value = {"bankId"})})
public class Question implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String bankId;
    private String type;
    private String stem;
    private String analysis;
    private int difficulty;
    private int order;
    private String options;
    private String answer;
    private int blanks;
    private String referenceAnswer;
    private int fullScore;

    // 图片字段（JSON List<String>，存图片路径）
    private String stemImages;
    private String optionImages;
    private String answerImages;
    private String analysisImages;

    @androidx.room.Ignore
    private transient boolean abnormal;

    @androidx.room.Ignore
    private transient String abnormalReason;

    @androidx.room.Ignore
    private transient int warningLevel; // 0=正常 1=警告 2=异常

    public Question() {}

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBankId() { return bankId; }
    public void setBankId(String bankId) { this.bankId = bankId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStem() { return stem; }
    public void setStem(String stem) { this.stem = stem; }
    public String getAnalysis() { return analysis; }
    public void setAnalysis(String analysis) { this.analysis = analysis; }
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public int getBlanks() { return blanks; }
    public void setBlanks(int blanks) { this.blanks = blanks; }
    public String getReferenceAnswer() { return referenceAnswer; }
    public void setReferenceAnswer(String referenceAnswer) { this.referenceAnswer = referenceAnswer; }
    public int getFullScore() { return fullScore; }
    public void setFullScore(int fullScore) { this.fullScore = fullScore; }

    public boolean isAbnormal() { return abnormal; }
    public void setAbnormal(boolean abnormal) { this.abnormal = abnormal; }

    public String getAbnormalReason() { return abnormalReason; }
    public void setAbnormalReason(String abnormalReason) { this.abnormalReason = abnormalReason; }

    public int getWarningLevel() { return warningLevel; }
    public void setWarningLevel(int warningLevel) { this.warningLevel = warningLevel; }

    public String getStemImages() { return stemImages; }
    public void setStemImages(String stemImages) { this.stemImages = stemImages; }
    public String getOptionImages() { return optionImages; }
    public void setOptionImages(String optionImages) { this.optionImages = optionImages; }
    public String getAnswerImages() { return answerImages; }
    public void setAnswerImages(String answerImages) { this.answerImages = answerImages; }
    public String getAnalysisImages() { return analysisImages; }
    public void setAnalysisImages(String analysisImages) { this.analysisImages = analysisImages; }

    /**
     * 创建当前 Question 的浅拷贝，用于 DiffUtil 检测内容变化。
     * 因为 ViewModel 中直接修改原对象会导致 DiffUtil 的 areContentsTheSame
     * 始终返回 true（oldItem 和 newItem 是同一引用），从而跳过 bind 刷新 UI。
     */
    public Question copy() {
        Question q = new Question();
        q.id = this.id;
        q.bankId = this.bankId;
        q.type = this.type;
        q.stem = this.stem;
        q.analysis = this.analysis;
        q.difficulty = this.difficulty;
        q.order = this.order;
        q.options = this.options;
        q.answer = this.answer;
        q.blanks = this.blanks;
        q.referenceAnswer = this.referenceAnswer;
        q.fullScore = this.fullScore;
        q.stemImages = this.stemImages;
        q.optionImages = this.optionImages;
        q.answerImages = this.answerImages;
        q.analysisImages = this.analysisImages;
        // 复制 transient 字段（非持久化但需在运行时保持的状态）
        q.abnormal = this.abnormal;
        q.abnormalReason = this.abnormalReason;
        q.warningLevel = this.warningLevel;
        return q;
    }
}