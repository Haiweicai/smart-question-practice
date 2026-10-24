package com.example.smart_question_practice.data.room;

public class BankAccuracyStat {
    public String bankId;
    public int totalCount;
    public int correctCount;

    public BankAccuracyStat() {}

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }
}
