package com.example.smart_question_practice.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnswerRecordDao {
    @Query("SELECT * FROM answer_record WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getWrongRecords(int userId);

    @Query("SELECT * FROM answer_record WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND userId = :userId ORDER BY lastAnswerTime DESC")
    List<AnswerRecord> getWrongRecordsSync(int userId);

    @Query("SELECT * FROM answer_record WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND bankId = :bankId AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getWrongRecordsByBank(String bankId, int userId);

    @Query("SELECT * FROM answer_record WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND questionType = :questionType AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getWrongRecordsByType(String questionType, int userId);

    @Query("SELECT * FROM answer_record WHERE isFavorite = 1 AND userId = :userId ORDER BY answerTime DESC")
    LiveData<List<AnswerRecord>> getFavoriteRecords(int userId);

    @Query("SELECT * FROM answer_record WHERE isFavorite = 1 AND userId = :userId ORDER BY answerTime DESC")
    List<AnswerRecord> getFavoriteRecordsSync(int userId);

    @Query("SELECT * FROM answer_record WHERE isFavorite = 1 AND bankId = :bankId AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getFavoriteRecordsByBank(String bankId, int userId);

    @Query("SELECT * FROM answer_record WHERE isFavorite = 1 AND questionType = :questionType AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getFavoriteRecordsByType(String questionType, int userId);

    @Query("SELECT * FROM answer_record WHERE questionId = :questionId AND userId = :userId")
    AnswerRecord getRecordByQuestionId(String questionId, int userId);

    @Query("SELECT * FROM answer_record WHERE bankId = :bankId AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getRecordsByBank(String bankId, int userId);

    @Query("SELECT * FROM answer_record WHERE bankId = :bankId AND userId = :userId ORDER BY lastAnswerTime DESC")
    List<AnswerRecord> getRecordsByBankSync(String bankId, int userId);

    @Query("SELECT * FROM answer_record WHERE userId = :userId")
    List<AnswerRecord> getAllRecordsSync(int userId);

    @Query("SELECT * FROM answer_record WHERE questionType = :questionType AND userId = :userId ORDER BY lastAnswerTime DESC")
    LiveData<List<AnswerRecord>> getRecordsByType(String questionType, int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE userId = :userId")
    int getTotalCount(int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE isCorrect = 1 AND userId = :userId")
    int getCorrectCount(int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE isCorrect = 0 AND userId = :userId")
    int getWrongCount(int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE isFavorite = 1 AND userId = :userId")
    int getFavoriteCount(int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE bankId = :bankId AND userId = :userId")
    int getTotalCountByBank(String bankId, int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE bankId = :bankId AND isCorrect = 1 AND userId = :userId")
    int getCorrectCountByBank(String bankId, int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE bankId = :bankId AND isCorrect = 0 AND isRemovedFromWrong = 0 AND userId = :userId")
    int getWrongCountByBank(String bankId, int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE questionType = :questionType AND userId = :userId")
    int getTotalCountByType(String questionType, int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE questionType = :questionType AND isCorrect = 1 AND userId = :userId")
    int getCorrectCountByType(String questionType, int userId);

    @Query("SELECT COUNT(*) FROM answer_record WHERE questionType = :questionType AND isCorrect = 0 AND isRemovedFromWrong = 0 AND userId = :userId")
    int getWrongCountByType(String questionType, int userId);

    @Query("SELECT SUM(score) FROM answer_record WHERE userId = :userId")
    int getTotalScore(int userId);

    @Query("SELECT SUM(score) FROM answer_record WHERE bankId = :bankId AND userId = :userId")
    int getTotalScoreByBank(String bankId, int userId);

    @Insert
    void insertRecord(AnswerRecord record);

    @Update
    void updateRecord(AnswerRecord record);

    @Delete
    void deleteRecord(AnswerRecord record);

    @Query("UPDATE answer_record SET isFavorite = :isFavorite WHERE questionId = :questionId AND userId = :userId")
    void updateFavorite(String questionId, boolean isFavorite, int userId);

    // 批量取消收藏
    @Query("UPDATE answer_record SET isFavorite = 0 WHERE isFavorite = 1 AND userId = :userId")
    void removeAllFavorites(int userId);

    @Query("UPDATE answer_record SET isFavorite = 0 WHERE isFavorite = 1 AND bankId = :bankId AND userId = :userId")
    void removeAllFavoritesByBank(String bankId, int userId);

    @Query("UPDATE answer_record SET isFavorite = 0 WHERE isFavorite = 1 AND questionType = :questionType AND userId = :userId")
    void removeAllFavoritesByType(String questionType, int userId);

    @Query("UPDATE answer_record SET isFavorite = 0 WHERE isFavorite = 1 AND bankId = :bankId AND questionType = :questionType AND userId = :userId")
    void removeAllFavoritesByBankAndType(String bankId, String questionType, int userId);

    @Query("UPDATE answer_record SET isRemovedFromWrong = 1, isCorrect = 1 WHERE questionId = :questionId AND userId = :userId")
    void removeFromWrong(String questionId, int userId);

    /** 重做次数 +1（错题模式下每重做一题调用一次） */
    @Query("UPDATE answer_record SET retryCount = retryCount + 1 WHERE questionId = :questionId AND userId = :userId")
    void incrementRetryCount(String questionId, int userId);

    @Query("UPDATE answer_record SET isRemovedFromWrong = 0, isCorrect = 0 WHERE questionId = :questionId AND userId = :userId")
    void restoreToWrong(String questionId, int userId);

    /** 撤销删除时重新激活错题记录（不改写来源，来源由日志单独恢复） */
    @Query("UPDATE answer_record SET isRemovedFromWrong = 0, isCorrect = 0 WHERE questionId = :questionId AND userId = :userId")
    void reactivateRecord(String questionId, int userId);

    @Query("UPDATE answer_record SET isRemovedFromWrong = 1, isCorrect = 1 WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND userId = :userId")
    void removeAllFromWrong(int userId);

    @Query("UPDATE answer_record SET isRemovedFromWrong = 1, isCorrect = 1 WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND bankId = :bankId AND userId = :userId")
    void removeAllFromWrongByBank(String bankId, int userId);

    @Query("UPDATE answer_record SET isRemovedFromWrong = 1, isCorrect = 1 WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND questionType = :questionType AND userId = :userId")
    void removeAllFromWrongByType(String questionType, int userId);

    @Query("UPDATE answer_record SET isRemovedFromWrong = 1, isCorrect = 1 WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND bankId = :bankId AND questionType = :questionType AND userId = :userId")
    void removeAllFromWrongByBankAndType(String bankId, String questionType, int userId);

    @Query("DELETE FROM answer_record WHERE bankId = :bankId AND userId = :userId")
    void deleteRecordsByBank(String bankId, int userId);

    @Query("DELETE FROM answer_record WHERE userId = :userId")
    void deleteAllRecords(int userId);

    @Query("SELECT questionType, COUNT(*) as count FROM answer_record WHERE userId = :userId GROUP BY questionType")
    List<QuestionTypeStat> getQuestionTypeStats(int userId);

    @Query("SELECT bankId, COUNT(*) as totalCount, SUM(CASE WHEN isCorrect = 1 THEN 1 ELSE 0 END) as correctCount FROM answer_record WHERE userId = :userId GROUP BY bankId ORDER BY correctCount * 1.0 / totalCount DESC")
    List<BankAccuracyStat> getBankAccuracyStats(int userId);
}
