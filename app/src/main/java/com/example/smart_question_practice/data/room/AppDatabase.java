package com.example.smart_question_practice.data.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * 本地数据库定义（表结构属于对外契约，骨架中保留）。
 *
 * 说明：这里是全新空库（version = 1），不含原项目的历史迁移链。
 * 如果你在二次开发中新增字段，请自行升级 version 并编写 Migration，
 * 或使用 fallbackToDestructiveMigration() 仅用于开发期。
 */
@Database(entities = {
        User.class,
        QuestionBank.class,
        Question.class,
        AnswerRecord.class,
        ExamConfig.class,
        ExamRecord.class,
        ExamAnswer.class,
        LanUserEntity.class,
        LanExamSession.class,
        LanRankCache.class,
        WrongSource.class,
        WrongDeleteLog.class,
        PracticeProgressEntity.class
}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract UserDao userDao();

    public abstract QuestionBankDao bankDao();

    public abstract QuestionDao questionDao();

    public abstract AnswerRecordDao recordDao();

    public abstract ExamConfigDao examConfigDao();

    public abstract ExamRecordDao examRecordDao();

    public abstract ExamAnswerDao examAnswerDao();

    public abstract LanDao lanDao();

    public abstract WrongSourceDao wrongSourceDao();

    public abstract WrongDeleteLogDao wrongDeleteLogDao();

    public abstract PracticeProgressDao practiceProgressDao();

    public static AppDatabase getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "smart_question_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
