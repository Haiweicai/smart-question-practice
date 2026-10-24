package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.data.room.Question;
import com.example.smart_question_practice.data.room.QuestionBank;

import java.util.List;

/**
 * 题库与题目的本地读写。
 * 实现建议：基于 data.room 中的 QuestionBankDao / QuestionDao。
 */
public interface QuestionBankService {

    void listBanks(Callback<List<QuestionBank>> callback);

    void createBank(String name, Callback<QuestionBank> callback);

    void deleteBank(long bankId, Callback<Void> callback);

    void listQuestions(long bankId, Callback<List<Question>> callback);

    void saveQuestion(Question question, Callback<Question> callback);

    void deleteQuestion(long questionId, Callback<Void> callback);
}
