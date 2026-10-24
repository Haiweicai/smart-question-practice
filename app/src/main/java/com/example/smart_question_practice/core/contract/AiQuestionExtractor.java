package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;

/**
 * 用大模型从非结构化文本里抽取题目（常规解析失败时的兜底）。
 * 对应 HTTP 接口：network.AiQuestionApi（OpenAI 兼容的 /chat/completions）。
 * 实现需要自行设计 Prompt、JSON 校验与失败重试。
 */
public interface AiQuestionExtractor {

    void extract(String rawText, ExtractOptions options, Callback<ExtractResult> callback);

    class ExtractOptions {
        /** 模型名，例如 deepseek-chat、doubao-pro-32k。 */
        public String model;
        /** 单次最多抽取的题量。 */
        public int maxCount = 50;
        /** 是否要求补充解析（答案解释）。 */
        public boolean requireAnalysis = true;
    }

    class ExtractResult {
        /** 抽取出的题目 JSON，结构见 docs/API.md 的 question 字段。 */
        public String questionsJson;
        public int tokenUsage;
        public String rawResponse;
    }
}
