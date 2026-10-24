package com.example.smart_question_practice.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WrongDeleteLogDao {

    @Insert
    void insertAll(List<WrongDeleteLog> logs);

    @Query("SELECT * FROM wrong_delete_log WHERE userId = :userId AND batchId = :batchId ORDER BY id")
    List<WrongDeleteLog> getByBatch(int userId, String batchId);

    @Query("UPDATE wrong_delete_log SET restored = 1 WHERE userId = :userId AND batchId = :batchId")
    void markRestored(int userId, String batchId);

    /** 清理 N 天以前已撤销的日志（未撤销日志保留，供后续追溯/恢复） */
    @Query("DELETE FROM wrong_delete_log WHERE restored = 1 AND deleteTime < :before")
    void cleanRestoredBefore(long before);
}
