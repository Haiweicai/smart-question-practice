package com.example.smart_question_practice.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question WHERE bankId = :bankId ORDER BY `order` ASC")
    LiveData<List<Question>> getQuestionsByBank(String bankId);

    @Query("SELECT * FROM question WHERE bankId = :bankId ORDER BY `order` ASC")
    List<Question> getQuestionsByBankSync(String bankId);

    @Query("SELECT * FROM question WHERE bankId = :bankId AND type = :type ORDER BY `order` ASC")
    LiveData<List<Question>> getQuestionsByBankAndType(String bankId, String type);

    @Query("SELECT * FROM question WHERE bankId = :bankId AND type = :type ORDER BY `order` ASC")
    List<Question> getQuestionsByBankAndTypeSync(String bankId, String type);

    @Query("SELECT * FROM question WHERE id = :questionId")
    Question getQuestionById(String questionId);

    @Query("SELECT * FROM question WHERE id IN (:questionIds)")
    List<Question> getQuestionsByIds(List<String> questionIds);

    @Query("SELECT COUNT(*) FROM question WHERE bankId = :bankId")
    int getQuestionCount(String bankId);

    @Query("SELECT COUNT(*) FROM question WHERE bankId = :bankId AND type = :type")
    int getQuestionCountByType(String bankId, String type);

    @Query("SELECT type, COUNT(*) as count FROM question WHERE bankId = :bankId GROUP BY type")
    List<QuestionTypeCount> getQuestionTypeStats(String bankId);

    @Query("SELECT * FROM question WHERE bankId = :bankId ORDER BY RANDOM() LIMIT :limit")
    List<Question> getRandomQuestions(String bankId, int limit);

    @Query("SELECT * FROM question WHERE bankId = :bankId AND type = :type ORDER BY RANDOM() LIMIT :limit")
    List<Question> getRandomQuestionsByType(String bankId, String type, int limit);

    @Query("SELECT * FROM question WHERE bankId = :bankId AND difficulty = :difficulty ORDER BY `order` ASC")
    List<Question> getQuestionsByDifficulty(String bankId, int difficulty);

    @Insert
    void insertQuestion(Question question);

    @Insert
    void insertQuestions(List<Question> questions);

    @Update
    void updateQuestion(Question question);

    @Update
    void updateQuestions(List<Question> questions);

    @Delete
    void deleteQuestion(Question question);

    @Delete
    void deleteQuestions(List<Question> questions);

    @Query("DELETE FROM question WHERE bankId = :bankId")
    void deleteQuestionsByBank(String bankId);

    @Query("DELETE FROM question")
    void deleteAllQuestions();

    class QuestionTypeCount {
        public String type;
        public int count;
    }

    // ==================== 模拟考试模块：按多题库 + 题型查询 ====================

    @Query("SELECT COUNT(*) FROM question WHERE bankId IN (:bankIds) AND type = :type")
    int countByBankIdsAndType(List<String> bankIds, String type);

    @Query("SELECT * FROM question WHERE bankId IN (:bankIds) AND type = :type")
    List<Question> getByBankIdsAndType(List<String> bankIds, String type);
}
