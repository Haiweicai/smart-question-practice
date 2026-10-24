package com.example.smart_question_practice.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExamConfigDao {
    @Insert
    long insert(ExamConfig config);

    @Query("SELECT * FROM exam_config WHERE userId = :userId ORDER BY createdAt DESC")
    LiveData<List<ExamConfig>> getConfigsByUser(int userId);

    @Query("SELECT * FROM exam_config WHERE id = :id")
    ExamConfig getConfigById(long id);

    @Query("SELECT * FROM exam_config WHERE userId = :userId ORDER BY createdAt DESC")
    List<ExamConfig> getHistoryConfigs(int userId);

    @Delete
    void delete(ExamConfig config);
}
