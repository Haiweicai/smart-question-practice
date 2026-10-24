package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.data.room.LanExamSession;

import java.util.List;

/**
 * 局域网考试：同一 WiFi 下由一台设备发卷，其他设备接收并作答。
 * 对应 data.room 中的 LanExamSession / LanRankCache / LanDao。
 * 骨架只保留契约，通信实现（UDP 广播 + HTTP 或 WebSocket）由二次开发者补全。
 */
public interface LanExamService {

    /** 发起人：创建会话并开始广播。 */
    void hostSession(LanExamSession session, Callback<String> callback);

    /** 考生：扫描局域网内可加入的会话。 */
    void discoverSessions(int timeoutMs, Callback<List<LanExamSession>> callback);

    /** 考生：加入指定会话。 */
    void joinSession(String sessionId, Callback<LanExamSession> callback);

    /** 考生：提交答卷到发起端。 */
    void submitPaper(String sessionId, String answersJson, Callback<Void> callback);

    /** 发起人：结束会话。 */
    void finishSession(String sessionId, Callback<Void> callback);

    void cancelSession(String sessionId, Callback<Void> callback);

    void listRank(String sessionId, Callback<List<RankItem>> callback);

    class RankItem {
        public int rank;
        public String studentName;
        public float score;
        public int durationUsed;
    }
}
