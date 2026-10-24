package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.data.room.Question;

/**
 * 客观题判分：单选、多选、判断、填空。
 * 多选题需处理漏选/错选规则，填空题需处理多空、大小写、空白与同义词。
 */
public interface AnswerMatcher {

    boolean isCorrect(Question question, String userAnswer);

    /** 返回本次得分（用于部分给分的题型）。 */
    double score(Question question, String userAnswer);
}
