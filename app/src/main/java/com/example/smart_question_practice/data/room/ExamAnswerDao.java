package com.example.smart_question_practice.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExamAnswerDao {
    @Insert
    void insert(ExamAnswer answer);

    @Insert
    void insertAll(List<ExamAnswer> answers);

    @Update
    void update(ExamAnswer answer);

    @Query("SELECT * FROM exam_answer WHERE examRecordId = :recordId ORDER BY questionIndex ASC")
    List<ExamAnswer> getAnswersByRecord(long recordId);

    @Query("SELECT * FROM exam_answer WHERE examRecordId = :recordId AND questionIndex = :index")
    ExamAnswer getAnswerByIndex(long recordId, int index);

    @Query("SELECT * FROM exam_answer WHERE id = :answerId")
    ExamAnswer getAnswerById(long answerId);

    @Query("SELECT COUNT(*) FROM exam_answer WHERE examRecordId = :recordId AND isAnswered = 1")
    int getAnsweredCount(long recordId);

    @Query("UPDATE exam_answer SET userAnswer = :answer, isAnswered = :answered, answeredAt = :time " +
            "WHERE examRecordId = :recordId AND questionIndex = :index")
    void updateAnswer(long recordId, int index, String answer, boolean answered, long time);

    @Query("DELETE FROM exam_answer WHERE examRecordId = :recordId")
    void deleteByRecord(long recordId);
}
