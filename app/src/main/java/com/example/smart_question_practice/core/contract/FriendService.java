package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;

import java.util.List;

/**
 * 好友：好友码、申请审批、题库直达分享、收件箱。
 * 对应 HTTP 接口：api/friend/*、api/share/*（见 network.BizApi）。
 * 服务端骨架见 server/src/routes/friend.js。
 */
public interface FriendService {

    /** 生成/刷新自己的好友码。 */
    void myCode(Callback<String> callback);

    void applyFriend(String code, Callback<Void> callback);

    void listRequests(Callback<List<FriendRequest>> callback);

    void handleRequest(String requestId, boolean accept, Callback<Void> callback);

    void listFriends(Callback<List<Friend>> callback);

    void removeFriend(String friendPublicId, Callback<Void> callback);

    /** 把一个本地题库直发给好友。 */
    void sendBank(String toPublicId, String bankName, String questionsJson, Callback<Void> callback);

    void listInbox(Callback<List<InboxItem>> callback);

    void ackInbox(int itemId, Callback<Void> callback);

    class Friend {
        public String publicId;
        public String username;
        public String nickname;
        public long addTime;
        public boolean online;
    }

    class FriendRequest {
        public String requestId;
        public String fromPublicId;
        public String fromUsername;
        public long createTime;
        public String message;
    }

    class InboxItem {
        public int id;
        public String type;      // bank / exam / text
        public String title;
        public String payload;   // 题库 JSON 或消息体
        public String fromName;
        public long createTime;
        public boolean read;
    }
}
