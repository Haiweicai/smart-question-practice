package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.data.room.Question;

import java.util.List;

/**
 * 刷题流程：组题、记录作答、错题与收藏。
 * 实现建议：基于 data.room 中的 AnswerRecordDao / WrongSourceDao / PracticeProgressDao。
 */
public interface PracticeService {

    void startPractice(PracticeRequest request, Callback<List<Question>> callback);

    void submitAnswer(long questionId, String userAnswer, boolean correct, Callback<Void> callback);

    void saveProgress(long bankId, int currentIndex, Callback<Void> callback);

    void listWrongQuestions(Callback<List<Question>> callback);

    void listFavoriteQuestions(Callback<List<Question>> callback);

    void setFavorite(long questionId, boolean favorite, Callback<Void> callback);

    class PracticeRequest {
        public long[] bankIds;
        public int questionCount;
        /** 0=顺序练习 1=随机练习 2=错题重练 3=收藏练习 */
        public int mode;
    }
}
