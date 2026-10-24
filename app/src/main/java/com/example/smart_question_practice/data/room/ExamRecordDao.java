package com.example.smart_question_practice.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExamRecordDao {
    @Insert
    long insert(ExamRecord record);

    @Update
    void update(ExamRecord record);

    @Delete
    void delete(ExamRecord record);

    @Delete
    void deleteAll(List<ExamRecord> records);

    @Query("DELETE FROM exam_record WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM exam_record WHERE id = :id")
    ExamRecord getRecordById(long id);

    @Query("SELECT * FROM exam_record WHERE userId = :userId AND status = :status LIMIT 1")
    ExamRecord getRecordByStatus(int userId, int status);

    @Query("SELECT * FROM exam_record WHERE userId = :userId AND status != 0 ORDER BY endTime DESC")
    LiveData<List<ExamRecord>> getHistoryRecords(int userId);

    @Query("UPDATE exam_record SET switchCount = :count WHERE id = :recordId")
    void updateSwitchCount(long recordId, int count);

    /** 局域网发卷人批阅：按会话+考生名查重（答卷重复回传时忽略） */
    @Query("SELECT * FROM exam_record WHERE lanSessionId = :sessionId AND lanStudentName = :studentName LIMIT 1")
    ExamRecord getLanStudentPaper(String sessionId, String studentName);

    /** 局域网排行榜发布：按会话查本机考生记录（回填最终成绩用） */
    @Query("SELECT * FROM exam_record WHERE lanSessionId = :sessionId AND lanFlag = 1 LIMIT 1")
    ExamRecord getLanStudentRecordBySession(String sessionId);

    /** 局域网发卷人批阅：某会话待批阅答卷数（含主观题、状态为已交卷） */
    @Query("SELECT COUNT(*) FROM exam_record WHERE lanSessionId = :sessionId AND lanFlag = 2 AND status = 1")
    int countPendingLanPapers(String sessionId);

    /** 局域网发卷人批阅：某会话已收答卷总数 */
    @Query("SELECT COUNT(*) FROM exam_record WHERE lanSessionId = :sessionId AND lanFlag = 2")
    int countLanPapers(String sessionId);

    /** 局域网发卷人批阅：待批阅答卷列表（考试记录页用） */
    @Query("SELECT * FROM exam_record WHERE lanFlag = 2 ORDER BY endTime DESC")
    LiveData<List<ExamRecord>> getLanHostPapers();

    /** 我发布的考试：独立考试(0) + 考生答卷(2)，已交卷/已阅卷，按 endTime 倒序 */
    @Query("SELECT * FROM exam_record WHERE userId = :userId AND status != 0 " +
           "AND lanFlag IN (0, 2) ORDER BY endTime DESC")
    LiveData<List<ExamRecord>> getPublishedRecords(int userId);

    /** 我被邀请的考试：考生端记录(1)，按 endTime 倒序 */
    @Query("SELECT * FROM exam_record WHERE userId = :userId AND status != 0 " +
           "AND lanFlag = 1 ORDER BY endTime DESC")
    LiveData<List<ExamRecord>> getInvitedRecords(int userId);

    /** 我给别人的阅卷结果：考生答卷(2)，已批阅(3)，按 endTime 倒序 */
    @Query("SELECT * FROM exam_record WHERE userId = :userId AND lanFlag = 2 " +
           "AND status = 3 ORDER BY endTime DESC")
    LiveData<List<ExamRecord>> getReviewedByMeRecords(int userId);

    /** 我被阅卷的结果：考生端(1)，已批阅(3)，按 endTime 倒序 */
    @Query("SELECT * FROM exam_record WHERE userId = :userId AND lanFlag = 1 " +
           "AND status = 3 ORDER BY endTime DESC")
    LiveData<List<ExamRecord>> getReviewedForMeRecords(int userId);
}
