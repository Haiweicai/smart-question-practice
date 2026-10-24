package com.example.smart_question_practice.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.smart_question_practice.data.room.ExamConfig;
import com.example.smart_question_practice.data.room.Question;
import com.example.smart_question_practice.data.room.QuestionBank;

import java.util.List;

/**
 * 局域网历史用户 DAO。
 * 采用「先 updateSeen 再 insert IGNORE」模式做 upsert，
 * 避免唯一索引上的 REPLACE 抹掉 invitedCount 计数。
 */
@Dao
public interface LanDao {

    /** 历史用户，按最后在线时间倒序（最近的在前） */
    @Query("SELECT * FROM lan_user WHERE userId = :userId ORDER BY lastSeen DESC")
    List<LanUserEntity> getHistory(int userId);

    /** 新增；ip 唯一索引冲突时忽略（由 updateSeen 先行更新） */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(LanUserEntity entity);

    /** 仅刷新在线信息与时间，不影响 invitedCount */
    @Query("UPDATE lan_user SET username = :username, deviceName = :deviceName, lastSeen = :lastSeen "
            + "WHERE userId = :userId AND ip = :ip")
    int updateSeen(int userId, String ip, String username, String deviceName, long lastSeen);

    /** 邀请次数 +1（阶段 2 使用，此处先备好） */
    @Query("UPDATE lan_user SET invitedCount = invitedCount + 1 WHERE userId = :userId AND ip = :ip")
    void incrementInvited(int userId, String ip);

    /** 删除某个历史用户 */
    @Query("DELETE FROM lan_user WHERE userId = :userId AND ip = :ip")
    void delete(int userId, String ip);

    // ==================== 局域网考试会话（阶段2） ====================

    /** 新增会话记录（发起端 role=HOST、考生端 role=STUDENT 各存一条） */
    @Insert
    long insertSession(LanExamSession session);

    /** 更新会话状态与结束时间 */
    @Query("UPDATE lan_exam_session SET status = :status, endTime = :endTime WHERE id = :id")
    void finishSession(long id, int status, long endTime);

    /** 阶段5：考试结束，排行榜 JSON 随会话一并落库 */
    @Query("UPDATE lan_exam_session SET rankJson = :rankJson, status = :status, endTime = :endTime WHERE id = :id")
    void finishSessionWithRank(long id, String rankJson, int status, long endTime);

    /** 改版阶段5：发布排行榜（不结束会话），仅更新 rankJson */
    @Query("UPDATE lan_exam_session SET rankJson = :rankJson WHERE id = :id")
    void updateRankJson(long id, String rankJson);

    // ==================== 排行榜缓存（考生端，EXAM_RANK_PUBLISH） ====================

    /** 按 sessionId 读取已发布的排行榜缓存 */
    @Query("SELECT * FROM lan_rank_cache WHERE sessionId = :sessionId")
    LanRankCache getRankCache(String sessionId);

    /** 读取全部已发布排行榜的 sessionId（历史记录列表批量预取徽标用） */
    @Query("SELECT sessionId FROM lan_rank_cache")
    List<String> getAllRankCacheSessionIds();

    /** 写入/覆盖排行榜缓存（重复发布直接覆盖） */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRankCache(LanRankCache cache);

    /** 按两端共享的 sessionId 查询本机会话 */
    @Query("SELECT * FROM lan_exam_session WHERE sessionId = :sessionId LIMIT 1")
    LanExamSession getSessionBySessionId(String sessionId);

    // ==================== 试卷传输（阶段3） ====================
    // 说明：以下为局域网试卷组包/入库专用跨表操作，全部经由 lan 前缀的
    // 专属题库（id 形如 "lan_xxxxxxxx"）进行，不触碰用户既有题库数据。

    /** 组卷抽题：按题库集合 + 题型取候选题（与 ExamRepository 抽题同源数据） */
    @Query("SELECT * FROM question WHERE bankId IN (:bankIds) AND type = :type")
    List<Question> getByBankIdsAndType(List<String> bankIds, String type);

    /** 新建 LAN 接收题库 */
    @Insert
    long insertBank(QuestionBank bank);

    /** 插入单道接收题目 */
    @Insert
    void insertOneQuestion(Question question);

    /** 新建接收端考试配置，返回新 configId */
    @Insert
    long insertConfig(ExamConfig config);

    /** 清理 LAN 专属题库的旧题目（仅限 lan_ 前缀题库，防重复接收） */
    @Query("DELETE FROM question WHERE bankId = :bankId")
    void deleteQuestionsByBank(String bankId);

    /** 清理 LAN 专属旧题库（仅限 lan_ 前缀题库） */
    @Query("DELETE FROM question_bank WHERE id = :bankId")
    void deleteBank(String bankId);
}
