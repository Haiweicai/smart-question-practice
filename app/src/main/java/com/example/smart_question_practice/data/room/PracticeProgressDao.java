package com.example.smart_question_practice.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PracticeProgressDao {
    @Query("SELECT * FROM practice_progress WHERE userId = :userId AND bankId = :bankId LIMIT 1")
    PracticeProgressEntity getProgress(int userId, String bankId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveProgress(PracticeProgressEntity progress);

    @Query("DELETE FROM practice_progress WHERE userId = :userId AND bankId = :bankId")
    void clearProgress(int userId, String bankId);
}
