package com.example.smart_question_practice.network;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 业务接口（班级/好友/题库共享），服务端见 server/server.js。
 * 所有需要登录的接口请求体都带 token 字段（BuildConfig.PANEL_TOKEN）。
 */
public interface BizApi {

    // ==================== 班级模块 ====================
    @POST("api/class/create")
    Call<BasicResp> classCreate(@Body ClassCreateReq body);

    @GET("api/class/search")
    Call<ClassSearchResp> classSearch(@Query("code") String code, @Query("token") String token);

    @POST("api/class/join")
    Call<BasicResp> classJoin(@Body ClassJoinReq body);

    @GET("api/class/members")
    Call<ClassMembersResp> classMembers(@Query("classId") int classId, @Query("token") String token);

    @GET("api/class/list")
    Call<ClassListResp> classList(@Query("userId") String userId, @Query("device") String device,
                                  @Query("token") String token);

    @POST("api/class/remove-member")
    Call<BasicResp> classRemoveMember(@Body ClassRemoveMemberReq body);

    @POST("api/class/dissolve")
    Call<BasicResp> classDissolve(@Body ClassDissolveReq body);

    @GET("api/class/detail")
    Call<ClassDetailResp> classDetail(@Query("classId") int classId, @Query("userId") String userId,
                                      @Query("device") String device, @Query("token") String token);

    @POST("api/class/update")
    Call<BasicResp> classUpdate(@Body ClassUpdateReq body);

    @POST("api/class/update-nickname")
    Call<BasicResp> classUpdateNickname(@Body ClassNicknameReq body);

    // ==================== 班级共享题库 ====================
    @POST("api/class/bank/upload")
    Call<ClassBankUploadResp> classBankUpload(@Body ClassBankUploadReq body);

    @GET("api/class/banks")
    Call<ClassBanksResp> classBanks(@Query("classId") int classId, @Query("userId") String userId,
                                    @Query("device") String device, @Query("token") String token);

    @GET("api/class/bank/detail")
    Call<ClassBankDetailResp> classBankDetail(@Query("classId") int classId, @Query("id") int id,
                                              @Query("userId") String userId, @Query("device") String device,
                                              @Query("token") String token);

    @POST("api/class/bank/delete")
    Call<BasicResp> classBankDelete(@Body ClassBankDeleteReq body);

    @POST("api/class/add-members")
    Call<ClassAddMembersResp> classAddMembers(@Body ClassAddMembersReq body);

    // ==================== 班级学情 ====================
    @POST("api/class/study/report")
    Call<BasicResp> classStudyReport(@Body StudyReportReq body);

    @GET("api/class/study/today")
    Call<StudyTodayResp> classStudyToday(@Query("classId") int classId,
                                         @Query("userId") String userId,
                                         @Query("device") String device,
                                         @Query("token") String token);

    // ==================== 好友模块 ====================
    @POST("api/friend/code")
    Call<FriendCodeResp> friendCode(@Body FriendCodeReq body);

    @POST("api/friend/add")
    Call<FriendAddResp> friendAdd(@Body FriendAddReq body);

    @GET("api/friend/list")
    Call<FriendListResp> friendList(@Query("userId") String userId, @Query("device") String device,
                                    @Query("token") String token);

    @POST("api/friend/remove")
    Call<BasicResp> friendRemove(@Body FriendRemoveReq body);

    @POST("api/friend/send-bank")
    Call<BasicResp> friendSendBank(@Body FriendSendBankReq body);

    @GET("api/friend/inbox")
    Call<InboxResp> friendInbox(@Query("userId") String userId, @Query("device") String device,
                                @Query("token") String token);

    @POST("api/friend/inbox/ack")
    Call<BasicResp> friendInboxAck(@Body InboxAckReq body);

    // ==================== 好友申请（审批制） ====================
    @POST("api/friend/apply")
    Call<FriendApplyResp> friendApply(@Body FriendApplyReq body);

    @GET("api/friend/requests")
    Call<FriendRequestsResp> friendRequests(@Query("userId") String userId, @Query("device") String device,
                                            @Query("token") String token);

    @POST("api/friend/request/handle")
    Call<BasicResp> friendRequestHandle(@Body FriendRequestHandleReq body);

    // ==================== 题库直达分享 ====================
    @POST("api/share/send")
    Call<ShareSendResp> shareSend(@Body ShareSendReq body);

    @GET("api/share/pending")
    Call<SharePendingResp> sharePending(@Query("userId") String userId, @Query("device") String device,
                                        @Query("token") String token);

    @POST("api/share/respond")
    Call<BasicResp> shareRespond(@Body ShareRespondReq body);

    // ==================== 题库共享模块 ====================
    @POST("api/share/toggle")
    Call<BasicResp> shareToggle(@Body ShareToggleReq body);

    @POST("api/share/upload")
    Call<ShareUploadResp> shareUpload(@Body ShareUploadReq body);

    @GET("api/share/pull")
    Call<SharePullResp> sharePull(@Query("code") String code);

    @POST("api/share/revoke")
    Call<BasicResp> shareRevoke(@Body ShareRevokeReq body);

    @GET("api/share/list")
    Call<ShareListResp> shareList(@Query("userId") String userId, @Query("device") String device,
                                  @Query("token") String token);

    // ==================== 班级考试模块（第二阶段） ====================
    @POST("api/class/share-exam")
    Call<ShareExamResp> classShareExam(@Body ShareExamReq body);

    @GET("api/class/exams")
    Call<ClassExamsResp> classExams(@Query("classId") int classId, @Query("userId") String userId,
                                    @Query("device") String device, @Query("token") String token);

    @POST("api/class/publish-exam")
    Call<BasicResp> classPublishExam(@Body PublishExamReq body);

    @POST("api/class/publish-ranking")
    Call<BasicResp> classPublishRanking(@Body CloseExamReq body);

    @GET("api/exam/detail")
    Call<ExamDetailResp> examDetail(@Query("examId") int examId, @Query("userId") String userId,
                                    @Query("device") String device, @Query("token") String token);

    @POST("api/exam/submit")
    Call<ExamSubmitResp> examSubmit(@Body ExamSubmitReq body);

    @POST("api/exam/grade-subjective")
    Call<GradeResp> examGradeSubjective(@Body GradeSubjectiveReq body);

    @GET("api/class/ranking")
    Call<RankingResp> classRanking(@Query("examId") int examId, @Query("userId") String userId,
                                   @Query("device") String device, @Query("token") String token);

    @GET("api/exam/my-result")
    Call<MyExamResultResp> examMyResult(@Query("examId") int examId, @Query("userId") String userId,
                                        @Query("device") String device, @Query("token") String token);

    @POST("api/exam/grade-by-teacher")
    Call<GradeResp> examGradeByTeacher(@Body GradeByTeacherReq body);

    @GET("api/exam/monitor")
    Call<MonitorResp> examMonitor(@Query("examId") int examId, @Query("teacherUserId") String teacherUserId,
                                  @Query("teacherDevice") String teacherDevice, @Query("token") String token);

    @POST("api/class/close-exam")
    Call<BasicResp> classCloseExam(@Body CloseExamReq body);

    // ==================== 会员体系 ====================
    @GET("api/vip/status")
    Call<VipStatusResp> vipStatus(@Query("userId") String userId, @Query("device") String device,
                                  @Query("publicId") String publicId, @Query("token") String token);

    @POST("api/vip/redeem")
    Call<VipRedeemResp> vipRedeem(@Body VipRedeemReq body);

    // ==================== 云端账号（8 位 ID + 一机一绑） ====================

    @POST("api/cloud/register")
    Call<CloudAuthResp> cloudRegister(@Body CloudAuthReq body);

    @POST("api/cloud/login")
    Call<CloudAuthResp> cloudLogin(@Body CloudAuthReq body);

    @GET("api/cloud/status")
    Call<CloudStatusResp> cloudStatus(@Query("publicId") String publicId, @Query("device") String device,
                                      @Query("token") String token);

    @POST("api/cloud/bind-phone")
    Call<CloudAuthResp> cloudBindPhone(@Body CloudAuthReq body);

    @POST("api/cloud/restore")
    Call<CloudAuthResp> cloudRestore(@Body CloudAuthReq body);

    // ==================== 通用响应 ====================
    class BasicResp {
        public boolean ok;
        public String error;
        public Integer classId;
        public String code;
        public String role;
        public Long expireAt;
    }

    // ==================== 会员 ====================
    class VipStatusResp {
        public boolean ok;
        public String error;
        public boolean whitelisted;   // 白名单（永久免费）
        public boolean vipActive;     // 会员是否有效（可创建班级）
        public long expireAt;         // 到期时间（秒），0=未开通
        public int monthlyCount;      // 已兑换月卡次数（前3次享新手价）
        public double nextPrice;      // 下一张月卡价格
        public long serverTime;       // 服务器当前时间
        public String phone;          // 已绑定的手机号（未绑定为 null）
    }

    class VipRedeemReq {
        public String token, userId, device, code, publicId;
    }

    class VipRedeemResp {
        public boolean ok;
        public String error;
        public long expireAt;
        public int monthlyCount;
        public double nextPrice;
    }

    // ==================== 班级 ====================
    class ClassCreateReq {
        public String token, userId, username, device, name, description, school, grade, subject, publicId;
        public int expireDays = 365;
    }

    // ==================== 云端账号 ====================

    class CloudAuthReq {
        public String token, username, passwordMd5, userId, device, phone, publicId;
    }

    class CloudAuthResp {
        public boolean ok;
        public String error;
        public String publicId;
        public boolean rebound;
    }

    class CloudStatusResp {
        public boolean ok;
        public String error;
        public boolean active;
    }

    class ClassSearchResp {
        public boolean ok;
        public String error;
        @SerializedName("class")
        public ClassInfo cls;

        public static class ClassInfo {
            public int classId;
            public String name, teacherName, subject;
            public int memberCount;
            public long expireAt;
        }
    }

    class ClassJoinReq {
        public String token, code, userId, username, device, role = "student";
    }

    class ClassMembersResp {
        public boolean ok;
        public int total;
        public List<Member> members;

        public static class Member {
            public String userId, username, device, role;
            public long joinedAt;
            public boolean online;
            public long lastSeen;
        }
    }

    class ClassListResp {
        public boolean ok;
        public List<Item> classes;

        public static class Item {
            public int classId;
            public String name, role, code, teacherName, description;
            public int memberCount;
        }
    }

    class ClassRemoveMemberReq {
        public String token, teacherUserId, teacherDevice, targetUserId, targetDevice;
        public int classId;
    }

    class ClassDissolveReq {
        public String token, teacherUserId, teacherDevice;
        public int classId;
    }

    class ClassDetailResp {
        public boolean ok;
        public String error;
        @SerializedName("class")
        public Detail cls;

        public static class Detail {
            public int classId;
            public String name, code, description, subject, teacherName, role;
            public int memberCount, bankCount;
            public long expireAt;
        }
    }

    class ClassUpdateReq {
        public String token, teacherUserId, teacherDevice, name, description;
        public int classId;
    }

    class ClassNicknameReq {
        public String token, userId, device, username;
        public int classId;
    }

    // ==================== 班级共享题库 ====================
    class ClassBankUploadReq {
        public String token, teacherUserId, teacherDevice, username;
        public String sourceBankId, bankName, bankDescription, questionsJson;
        public int classId;
    }

    class ClassBankUploadResp {
        public boolean ok;
        public String error;
        public int id;
        /** 同一题库重复上传时为 true（服务端去重） */
        public boolean duplicated;
    }

    class ClassBanksResp {
        public boolean ok;
        public String error;
        public List<Item> banks;

        public static class Item {
            public int id, questionCount;
            public String bankName, bankDescription, uploaderUsername, sourceBankId;
            public long createdAt;
            public boolean mine;
        }
    }

    class ClassBankDetailResp {
        public boolean ok;
        public String error;
        public Bank bank;

        public static class Bank {
            public int id, questionCount;
            public String bankName, bankDescription, uploaderUsername, questionsJson;
        }
    }

    class ClassBankDeleteReq {
        public String token, teacherUserId, teacherDevice;
        public int classId, id;
    }

    class ClassAddMembersReq {
        public String token, teacherUserId, teacherDevice;
        public int classId;
        public List<MemberInfo> members;

        public static class MemberInfo {
            public String userId, device, username;
        }
    }

    class ClassAddMembersResp {
        public boolean ok;
        public String error;
        public int added, skipped;
    }

    // ==================== 班级学情 ====================
    class StudyReportReq {
        public String token, userId, device;
        public int classId, seconds;
    }

    class StudyTodayResp {
        public boolean ok;
        public String error, day;
        public int totalSeconds;
        public List<MemberStudy> members;

        public static class MemberStudy {
            public String userId, device, username, role;
            public int seconds;
            public boolean online;
            public long lastSeen;
        }
    }

    // ==================== 好友 ====================
    class FriendCodeReq {
        public String token, userId, device, username;
    }

    class FriendCodeResp {
        public boolean ok;
        public String error;
        public String code;
        /** 当前码剩余有效秒数；到期后应重新拉取 */
        public long expiresIn;
        /** 轮换周期（秒），服务端默认 1800 */
        public long ttlSec;
    }

    class FriendAddReq {
        public String token, userId, device, friendCode;
    }

    class FriendAddResp {
        public boolean ok;
        public String error;
        public String friendUserId, friendUsername;
    }

    class FriendListResp {
        public boolean ok;
        public List<Item> friends;

        public static class Item {
            public String friendUserId, friendUsername, friendDevice;
            public long addedAt;
            public boolean online;
            public long lastSeen;
        }
    }

    class FriendRemoveReq {
        public String token, userId, device, friendUserId, friendDevice;
    }

    class FriendSendBankReq {
        public String token, userId, username, device, friendUserId, friendDevice;
        public String bankName, bankDescription, questionsJson;
    }

    class InboxResp {
        public boolean ok;
        public List<Item> messages;

        public static class Item {
            public int id;
            public String senderUsername, bankName, bankDescription, questionsJson;
            public int questionCount;
            public long sentAt;
        }
    }

    class InboxAckReq {
        public String token, userId, device;
        public int messageId;
        public boolean accepted;
    }

    // ==================== 好友申请（审批制） ====================
    class FriendApplyReq {
        public String token, userId, device, username, friendCode;
    }

    class FriendApplyResp {
        public boolean ok;
        public String error;
        public boolean autoAccepted;
        public String friendUsername;
    }

    class FriendRequestsResp {
        public boolean ok;
        public List<Item> requests;

        public static class Item {
            public int id;
            public String fromUserId, fromUsername, fromDevice;
            public long createdAt;
        }
    }

    class FriendRequestHandleReq {
        public String token, userId, device;
        public int requestId;
        public boolean accept;
    }

    // ==================== 题库直达分享 ====================
    class ShareSendReq {
        public String token, userId, device, username;
        public List<Target> targets;
        public String bankName, bankDescription, payloadJson;

        public static class Target {
            public String friendUserId, friendDevice;

            public Target(String friendUserId, String friendDevice) {
                this.friendUserId = friendUserId;
                this.friendDevice = friendDevice;
            }
        }
    }

    class ShareSendResp {
        public boolean ok;
        public String error;
        public int sentCount;
    }

    class SharePendingResp {
        public boolean ok;
        public List<Item> offers;

        public static class Item {
            public int id;
            public String senderUsername, bankName, bankDescription, payloadJson;
            public int questionCount;
            public long createdAt;
        }
    }

    class ShareRespondReq {
        public String token, userId, device;
        public int offerId;
        public boolean accept;
    }

    // ==================== 题库共享 ====================
    class ShareToggleReq {
        public String token, userId, device, localBankId, bankName;
        public boolean isShared;
    }

    class ShareUploadReq {
        public String token, userId, username, device, localBankId, bankName, bankDescription, questionsJson;
        public int expireDays = 7;
    }

    class ShareUploadResp {
        public boolean ok;
        public String error;
        public String shareCode;
        public long expireAt;
    }

    class SharePullResp {
        public boolean ok;
        public String error;
        public Bank bank;

        public static class Bank {
            public String bankName, bankDescription, senderUsername, questionsJson;
            public int questionCount;
            public long expireAt;
        }
    }

    class ShareRevokeReq {
        public String token, userId, device, shareCode;
    }

    class ShareListResp {
        public boolean ok;
        public List<Item> shares;

        public static class Item {
            public String shareCode, bankName;
            public long createdAt, expireAt;
            public int pullCount;
            public boolean revoked;
        }
    }

    // ==================== 班级考试 ====================
    class ShareExamReq {
        public String token, teacherUserId, teacherDevice, examName, questionsJson;
        public int classId;
        public ExamConfigDto examConfig;
    }

    /** 考试配置，与服务端 class_exams 表字段对齐（复用 App ExamConfig 语义） */
    class ExamConfigDto {
        public int mode = 1;
        public String bankIds;
        public int singleCount, multiCount, judgeCount, fillCount, essayCount;
        public int duration;
        public double singleScore, multiScore, judgeScore, fillScore, essayScore;
        public boolean indefiniteMode, shuffleOptions;
        public int maxSwitchCount, lanGradingMode;
    }

    class ShareExamResp {
        public boolean ok;
        public String error;
        public int examId;
    }

    class ClassExamsResp {
        public boolean ok;
        public String error;
        public List<Item> exams;

        public static class Item {
            public int examId;
            public String name, status, myStatus;
            public int totalQuestions, duration, submittedCount, totalStudents, lanGradingMode;
            public int participantCount, maxSwitchCount;
            public boolean hasSubjective, rankingPublished;
            public double totalScore;
            public Long startAt, endAt, createdAt;
        }
    }

    class PublishExamReq {
        public String token, teacherUserId, teacherDevice;
        public int classId, examId;
        public long startAt, endAt;
        public boolean shuffle;
        public int lanGradingMode;
        public java.util.List<String> targetUserIds;
    }

    class ExamDetailResp {
        public boolean ok;
        public String error;
        public Exam exam;

        public static class Exam {
            public int examId, totalQuestions, submissionId;
            public String name, questionsJson;
            public double totalScore;
            public long startAt, endAt, myStartTime, deadline;
            public ExamConfigDto examConfig;
        }
    }

    class ExamSubmitReq {
        public String token, userId, device, answersJson;
        public int submissionId, examId, durationUsed, switchCount;
        public boolean forced;
    }

    class ExamSubmitResp {
        public boolean ok;
        public String error;
        public Result result;

        public static class Result {
            public double objectiveScore, subjectiveScore, totalScore, accuracy;
            public int correctCount, objectiveTotal;
            public boolean needsReview;
            /** 客观题按题型统计：{"single":{"c":对,"n":总},...} */
            public java.util.Map<String, TypeCount> byType;
        }

        public class TypeCount {
            public int c, n;
        }
    }

    /** 学生查看自己的考试成绩（历史记录回看） */
    class MyExamResultResp {
        public boolean ok;
        public String error;
        public Result result;

        public static class Result {
            public String examName;
            public double fullScore, totalScore, objectiveScore, subjectiveScore, accuracy;
            public int maxSwitchCount, durationUsed, switchCount, totalQuestions, answeredCount;
            public boolean forced, pendingReview, rankingPublished;
            /** 客观题按题型 {c,n} */
            public java.util.Map<String, ExamSubmitResp.TypeCount> byType;
            /** 主观题按题型 {c,n} */
            public java.util.Map<String, ExamSubmitResp.TypeCount> subjByType;
        }
    }

    class GradeSubjectiveReq {
        public String token, userId, device, gradedAnswers;
        public int submissionId;
    }

    class GradeByTeacherReq {
        public String token, teacherUserId, teacherDevice, gradedAnswers;
        public int examId, submissionId;
    }

    class GradeResp {
        public boolean ok;
        public String error;
        public Result result;

        public static class Result {
            public double objectiveScore, subjectiveScore, totalScore;
            public boolean needsReview;
            public String status;
        }
    }

    class RankingResp {
        public boolean ok;
        public String error;
        public List<Item> ranking;
        public Stats stats;
        public int myRank, totalStudents, submittedCount;
        public Double myScore, averageScore;
        // 学生分支补充（教师分支走 stats）
        public Double maxScore, minScore;
        public int avgDuration;
        public boolean rankingPublished;

        public static class Item {
            public int rank, correctCount, durationUsed, switchCount;
            public Integer submissionId;
            public String username, userId, device, status;
            public Double totalScore, objectiveScore, subjectiveScore;
            public double accuracy;
            public boolean forced, isMe;
            public Long submittedAt;
        }

        public static class Stats {
            public int totalStudents, submittedCount, notSubmittedCount;
            public double averageScore, maxScore, minScore, passRate;
            public java.util.Map<String, Double> accuracyByType;
        }
    }

    class MonitorResp {
        public boolean ok;
        public String error;
        public String examStatus;
        public Long startAt, endAt;
        public List<Student> students;
        public Summary summary;

        public static class Student {
            public String userId, username, device, submissionStatus;
            public int switchCount, durationUsed;
            public boolean online;
            public Long myStartTime, lastSeen, submittedAt;
            public Double totalScore;
        }

        public static class Summary {
            public int totalStudents, notStarted, ongoing, submitted, forced;
        }
    }

    class CloseExamReq {
        public String token, teacherUserId, teacherDevice, action;
        public int classId, examId;
    }
}
