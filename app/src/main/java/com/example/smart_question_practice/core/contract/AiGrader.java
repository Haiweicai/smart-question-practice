package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;

/**
 * 主观题（简答题）AI 评分。
 * 对应 HTTP 接口：api/exam/grade-subjective。
 */
public interface AiGrader {

    void grade(GradingRequest request, Callback<GradingResult> callback);

    class GradingRequest {
        public String questionText;
        public String referenceAnswer;
        public String userAnswer;
        public double maxScore;
    }

    class GradingResult {
        public double score;
        public String comment;
        public String[] strengths;
        public String[] weaknesses;
        public String[] suggestions;
    }
}
