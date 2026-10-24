package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.data.room.ExamAnswer;
import com.example.smart_question_practice.data.room.ExamConfig;
import com.example.smart_question_practice.data.room.ExamRecord;
import com.example.smart_question_practice.data.room.Question;

import java.util.List;

/**
 * 本地模拟考试：组卷、答题、交卷判分、历史记录。
 * 实现建议：基于 data.room 中的 ExamConfigDao / ExamRecordDao / ExamAnswerDao。
 */
public interface ExamService {

    /** 创建考试配置并发起一场考试，返回本次考试记录。 */
    void startExam(ExamConfig config, Callback<ExamRecord> callback);

    void listExamQuestions(long examRecordId, Callback<List<Question>> callback);

    void saveAnswer(long examRecordId, ExamAnswer answer, Callback<Void> callback);

    /** 正常交卷，由实现方完成客观题判分与统计。 */
    void submitExam(long examRecordId, Callback<ExamRecord> callback);

    /** 切后台超限等场景的强制交卷。 */
    void forceSubmitExam(long examRecordId, String reason, Callback<ExamRecord> callback);

    void listExamHistory(boolean includeLanExams, Callback<List<ExamRecord>> callback);

    void deleteExamRecord(long examRecordId, Callback<Void> callback);
}
