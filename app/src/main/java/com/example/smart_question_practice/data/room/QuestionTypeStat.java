package com.example.smart_question_practice.data.room;

public class QuestionTypeStat {
    public String questionType;
    public int count;

    public QuestionTypeStat() {}

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
