package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;

import java.util.List;

/**
 * 班级：创建、加入、成员管理、班级共享题库。
 * 对应 HTTP 接口：api/class/*（见 network.BizApi）。
 * 服务端骨架见 server/src/routes/class.js。
 */
public interface ClassService {

    void createClass(ClassInfo info, Callback<Integer> callback);

    void searchClass(String code, Callback<ClassInfo> callback);

    void joinClass(String code, Callback<Integer> callback);

    void listMyClasses(Callback<List<ClassInfo>> callback);

    void listMembers(int classId, Callback<List<Member>> callback);

    void removeMember(int classId, int userId, Callback<Void> callback);

    void addMembers(int classId, List<Integer> userIds, Callback<Void> callback);

    void dissolveClass(int classId, Callback<Void> callback);

    void uploadSharedBank(int classId, String bankName, String questionsJson, Callback<Integer> callback);

    void listSharedBanks(int classId, Callback<List<SharedBank>> callback);

    void deleteSharedBank(int classId, int sharedBankId, Callback<Void> callback);

    class ClassInfo {
        public int id;
        public String name;
        public String description;
        public String school;
        public String grade;
        public String subject;
        public String code;
        public String role;
        public long createTime;
        public int memberCount;
        public String ownerName;
    }

    class Member {
        public int userId;
        public String username;
        public String nickname;
        public String role;
        public long joinTime;
    }

    class SharedBank {
        public int id;
        public String name;
        public int questionCount;
        public String uploaderName;
        public long uploadTime;
    }
}
