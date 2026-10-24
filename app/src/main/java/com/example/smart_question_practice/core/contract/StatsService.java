package com.example.smart_question_practice.core.contract;

import java.util.List;

/**
 * 学习统计：刷题量、正确率、题型分布、错题趋势。
 * 实现建议：基于 data.room 中的 AnswerRecordDao（含 BankAccuracyStat / QuestionTypeStat 聚合结果）。
 */
public interface StatsService {

    StatsSummary getSummary(long fromTime, long toTime);

    List<TypeAccuracy> getTypeAccuracy(long fromTime, long toTime);

    List<DailyCount> getDailyCounts(long fromTime, long toTime);

    List<BankAccuracy> getBankAccuracy();

    class StatsSummary {
        public int totalAnswered;
        public int totalCorrect;
        public double accuracy;
        public int studySeconds;
        public int wrongCount;
        public int bankCount;
    }

    class TypeAccuracy {
        public String type;
        public int total;
        public int correct;
        public double accuracy;
    }

    class DailyCount {
        /** yyyy-MM-dd */
        public String date;
        public int answered;
        public int correct;
    }

    class BankAccuracy {
        public String bankId;
        public String bankName;
        public int total;
        public int correct;
        public double accuracy;
    }
}
