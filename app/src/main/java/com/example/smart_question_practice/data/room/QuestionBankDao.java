package com.example.smart_question_practice.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionBankDao {
    @Query("SELECT * FROM question_bank WHERE isDeleted = 0 AND userId = :userId ORDER BY updateTime DESC")
    LiveData<List<QuestionBank>> getAllBanks(int userId);

    @Query("SELECT * FROM question_bank WHERE isDeleted = 1 AND userId = :userId ORDER BY updateTime DESC")
    LiveData<List<QuestionBank>> getDeletedBanks(int userId);

    @Query("SELECT * FROM question_bank WHERE isDeleted = 1 AND userId = :userId ORDER BY updateTime DESC")
    List<QuestionBank> getDeletedBanksSync(int userId);

    @Insert
    void insertBank(QuestionBank bank);

    @Update
    void updateBank(QuestionBank bank);

    @Query("UPDATE question_bank SET isDeleted = 1, updateTime = :updateTime WHERE id = :bankId AND userId = :userId")
    void softDeleteBank(String bankId, long updateTime, int userId);

    @Query("UPDATE question_bank SET isDeleted = 0, updateTime = :updateTime WHERE id = :bankId AND userId = :userId")
    void restoreBank(String bankId, long updateTime, int userId);

    @Delete
    void permanentlyDeleteBank(QuestionBank bank);

    @Query("DELETE FROM question_bank WHERE id = :bankId AND userId = :userId")
    void permanentlyDeleteBankById(String bankId, int userId);

    @Query("DELETE FROM question_bank WHERE isDeleted = 1 AND userId = :userId")
    void clearRecycleBin(int userId);

    @Query("SELECT * FROM question_bank WHERE id = :bankId AND userId = :userId")
    QuestionBank getBankById(String bankId, int userId);

    @Query("SELECT COUNT(*) FROM question_bank WHERE name LIKE :namePattern AND isDeleted = 0 AND userId = :userId")
    int countBanksByName(String namePattern, int userId);

    @Query("SELECT COUNT(*) FROM question_bank WHERE isDeleted = 1 AND userId = :userId")
    int getDeletedBankCount(int userId);

    @Query("SELECT * FROM question_bank WHERE name = :name AND userId = :userId LIMIT 1")
    QuestionBank getBankByName(String name, int userId);

    @Query("SELECT * FROM question_bank WHERE isDeleted = 0 AND userId = :userId ORDER BY updateTime DESC")
    List<QuestionBank> getAllBanksSync(int userId);

    @Query("UPDATE question_bank SET isShared = :isShared WHERE id = :bankId AND userId = :userId")
    void updateShared(String bankId, int userId, boolean isShared);

    @Query("DELETE FROM question_bank WHERE userId = :userId")
    void deleteAllBanks(int userId);
}
