package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.data.room.Question;

import java.io.File;
import java.util.List;

/**
 * 题库文件解析：PDF / Word / Excel / TXT → 结构化题目。
 * 这是原项目的核心算法之一，骨架中只保留契约。
 */
public interface QuestionParser {

    /** 支持的文件扩展名，例如 pdf、docx、xlsx、txt。 */
    List<String> supportedExtensions();

    void parse(File file, ParseOptions options, Callback<ParseResult> callback);

    class ParseOptions {
        /** 是否启用 AI 兜底识别。 */
        public boolean useAiFallback = true;
        /** 单选/多选/判断/填空/简答 的期望题量，0 表示不限。 */
        public int expectedCount = 0;
    }

    class ParseResult {
        public final List<Question> questions;
        public final int rawBlockCount;
        public final List<String> warnings;

        public ParseResult(List<Question> questions, int rawBlockCount, List<String> warnings) {
            this.questions = questions;
            this.rawBlockCount = rawBlockCount;
            this.warnings = warnings;
        }
    }
}
