package com.example.smart_question_practice.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WrongSourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WrongSource source);

    @Query("SELECT * FROM wrong_source WHERE userId = :userId")
    List<WrongSource> getByUser(int userId);

    @Query("SELECT * FROM wrong_source WHERE questionId = :questionId AND userId = :userId")
    List<WrongSource> getByQuestion(String questionId, int userId);

    @Query("DELETE FROM wrong_source WHERE questionId = :questionId AND userId = :userId")
    void deleteByQuestion(String questionId, int userId);

    /** 仅删除单个来源（考试视图隔离删除：只删该场考试，刷题来源不受影响） */
    @Query("DELETE FROM wrong_source WHERE questionId = :questionId AND userId = :userId "
            + "AND sourceType = :sourceType AND sourceRef = :sourceRef")
    void deleteOne(String questionId, int userId, String sourceType, String sourceRef);

    @Query("SELECT COUNT(*) FROM wrong_source WHERE questionId = :questionId AND userId = :userId")
    int countByQuestion(String questionId, int userId);

    /** 删除即将被移出错题本的题目对应的来源（必须在记录置 isRemovedFromWrong=1 之前调用） */
    @Query("DELETE FROM wrong_source WHERE userId = :userId AND questionId IN ("
            + "SELECT questionId FROM answer_record "
            + "WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND userId = :userId)")
    void deleteForRemovedAll(int userId);

    @Query("DELETE FROM wrong_source WHERE userId = :userId AND questionId IN ("
            + "SELECT questionId FROM answer_record "
            + "WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND bankId = :bankId AND userId = :userId)")
    void deleteForRemovedByBank(String bankId, int userId);

    @Query("DELETE FROM wrong_source WHERE userId = :userId AND questionId IN ("
            + "SELECT questionId FROM answer_record "
            + "WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND questionType = :questionType AND userId = :userId)")
    void deleteForRemovedByType(String questionType, int userId);

    @Query("DELETE FROM wrong_source WHERE userId = :userId AND questionId IN ("
            + "SELECT questionId FROM answer_record "
            + "WHERE isCorrect = 0 AND isRemovedFromWrong = 0 AND bankId = :bankId "
            + "AND questionType = :questionType AND userId = :userId)")
    void deleteForRemovedByBankAndType(String bankId, String questionType, int userId);

    @Query("DELETE FROM wrong_source WHERE userId = :userId AND questionId IN (:questionIds)")
    void deleteByQuestions(List<String> questionIds, int userId);

    @Query("DELETE FROM wrong_source WHERE bankId = :bankId AND userId = :userId")
    void deleteByBank(String bankId, int userId);

    @Query("DELETE FROM wrong_source WHERE userId = :userId")
    void deleteByUser(int userId);
}
