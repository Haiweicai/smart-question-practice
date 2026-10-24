package com.example.smart_question_practice.core.stub;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.core.contract.AiGrader;
import com.example.smart_question_practice.core.contract.AiQuestionExtractor;
import com.example.smart_question_practice.core.contract.AnswerMatcher;
import com.example.smart_question_practice.core.contract.AuthService;
import com.example.smart_question_practice.core.contract.ClassService;
import com.example.smart_question_practice.core.contract.ExamService;
import com.example.smart_question_practice.core.contract.FriendService;
import com.example.smart_question_practice.core.contract.LanExamService;
import com.example.smart_question_practice.core.contract.PracticeService;
import com.example.smart_question_practice.core.contract.QuestionBankService;
import com.example.smart_question_practice.core.contract.QuestionParser;
import com.example.smart_question_practice.core.contract.StatsService;
import com.example.smart_question_practice.core.contract.UpdateService;
import com.example.smart_question_practice.data.room.ExamAnswer;
import com.example.smart_question_practice.data.room.ExamConfig;
import com.example.smart_question_practice.data.room.ExamRecord;
import com.example.smart_question_practice.data.room.LanExamSession;
import com.example.smart_question_practice.data.room.Question;
import com.example.smart_question_practice.data.room.QuestionBank;
import com.example.smart_question_practice.data.room.User;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * 所有契约接口的兜底空实现。
 *
 * 骨架默认装配它，所以 App 下载下来即可编译、可运行，点击任何按钮只会收到
 * 「尚未实现」的提示，而不会崩溃。二次开发者实现某个接口后，在
 * {@link com.example.smart_question_practice.core.ServiceLocator} 里替换即可。
 */
public final class NoOpServices {

    private static final String MSG = "功能尚未实现，请参考 docs/INTERFACES.md";

    private NoOpServices() {
    }

    private static <T> void fail(Callback<T> callback) {
        if (callback != null) {
            callback.onError(MSG);
        }
    }

    private static <T> void empty(Callback<List<T>> callback) {
        if (callback != null) {
            callback.onSuccess(Collections.emptyList());
        }
    }

    public static final AuthService AUTH = new AuthService() {
        @Override
        public void login(String account, String password, String device, Callback<User> callback) {
            fail(callback);
        }

        @Override
        public void register(String account, String password, String device, Callback<User> callback) {
            fail(callback);
        }

        @Override
        public User currentUser() {
            return null;
        }

        @Override
        public void logout() {
        }
    };

    public static final QuestionBankService BANK = new QuestionBankService() {
        @Override
        public void listBanks(Callback<List<QuestionBank>> callback) {
            empty(callback);
        }

        @Override
        public void createBank(String name, Callback<QuestionBank> callback) {
            fail(callback);
        }

        @Override
        public void deleteBank(long bankId, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void listQuestions(long bankId, Callback<List<Question>> callback) {
            empty(callback);
        }

        @Override
        public void saveQuestion(Question question, Callback<Question> callback) {
            fail(callback);
        }

        @Override
        public void deleteQuestion(long questionId, Callback<Void> callback) {
            fail(callback);
        }
    };

    public static final QuestionParser PARSER = new QuestionParser() {
        @Override
        public List<String> supportedExtensions() {
            return Collections.emptyList();
        }

        @Override
        public void parse(File file, ParseOptions options, Callback<ParseResult> callback) {
            fail(callback);
        }
    };

    public static final AnswerMatcher MATCHER = new AnswerMatcher() {
        @Override
        public boolean isCorrect(Question question, String userAnswer) {
            return false;
        }

        @Override
        public double score(Question question, String userAnswer) {
            return 0;
        }
    };

    public static final PracticeService PRACTICE = new PracticeService() {
        @Override
        public void startPractice(PracticeRequest request, Callback<List<Question>> callback) {
            empty(callback);
        }

        @Override
        public void submitAnswer(long questionId, String userAnswer, boolean correct, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void saveProgress(long bankId, int currentIndex, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void listWrongQuestions(Callback<List<Question>> callback) {
            empty(callback);
        }

        @Override
        public void listFavoriteQuestions(Callback<List<Question>> callback) {
            empty(callback);
        }

        @Override
        public void setFavorite(long questionId, boolean favorite, Callback<Void> callback) {
            fail(callback);
        }
    };

    public static final ExamService EXAM = new ExamService() {
        @Override
        public void startExam(ExamConfig config, Callback<ExamRecord> callback) {
            fail(callback);
        }

        @Override
        public void listExamQuestions(long examRecordId, Callback<List<Question>> callback) {
            empty(callback);
        }

        @Override
        public void saveAnswer(long examRecordId, ExamAnswer answer, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void submitExam(long examRecordId, Callback<ExamRecord> callback) {
            fail(callback);
        }

        @Override
        public void forceSubmitExam(long examRecordId, String reason, Callback<ExamRecord> callback) {
            fail(callback);
        }

        @Override
        public void listExamHistory(boolean includeLanExams, Callback<List<ExamRecord>> callback) {
            empty(callback);
        }

        @Override
        public void deleteExamRecord(long examRecordId, Callback<Void> callback) {
            fail(callback);
        }
    };

    public static final ClassService CLASSP = new ClassService() {
        @Override
        public void createClass(ClassInfo info, Callback<Integer> callback) {
            fail(callback);
        }

        @Override
        public void searchClass(String code, Callback<ClassInfo> callback) {
            fail(callback);
        }

        @Override
        public void joinClass(String code, Callback<Integer> callback) {
            fail(callback);
        }

        @Override
        public void listMyClasses(Callback<List<ClassInfo>> callback) {
            empty(callback);
        }

        @Override
        public void listMembers(int classId, Callback<List<Member>> callback) {
            empty(callback);
        }

        @Override
        public void removeMember(int classId, int userId, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void addMembers(int classId, List<Integer> userIds, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void dissolveClass(int classId, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void uploadSharedBank(int classId, String bankName, String questionsJson, Callback<Integer> callback) {
            fail(callback);
        }

        @Override
        public void listSharedBanks(int classId, Callback<List<SharedBank>> callback) {
            empty(callback);
        }

        @Override
        public void deleteSharedBank(int classId, int sharedBankId, Callback<Void> callback) {
            fail(callback);
        }
    };

    public static final FriendService FRIEND = new FriendService() {
        @Override
        public void myCode(Callback<String> callback) {
            fail(callback);
        }

        @Override
        public void applyFriend(String code, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void listRequests(Callback<List<FriendRequest>> callback) {
            empty(callback);
        }

        @Override
        public void handleRequest(String requestId, boolean accept, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void listFriends(Callback<List<Friend>> callback) {
            empty(callback);
        }

        @Override
        public void removeFriend(String friendPublicId, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void sendBank(String toPublicId, String bankName, String questionsJson, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void listInbox(Callback<List<InboxItem>> callback) {
            empty(callback);
        }

        @Override
        public void ackInbox(int itemId, Callback<Void> callback) {
            fail(callback);
        }
    };

    public static final LanExamService LAN_EXAM = new LanExamService() {
        @Override
        public void hostSession(LanExamSession session, Callback<String> callback) {
            fail(callback);
        }

        @Override
        public void discoverSessions(int timeoutMs, Callback<List<LanExamSession>> callback) {
            empty(callback);
        }

        @Override
        public void joinSession(String sessionId, Callback<LanExamSession> callback) {
            fail(callback);
        }

        @Override
        public void submitPaper(String sessionId, String answersJson, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void finishSession(String sessionId, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void cancelSession(String sessionId, Callback<Void> callback) {
            fail(callback);
        }

        @Override
        public void listRank(String sessionId, Callback<List<RankItem>> callback) {
            empty(callback);
        }
    };

    public static final AiGrader AI_GRADER = new AiGrader() {
        @Override
        public void grade(GradingRequest request, Callback<GradingResult> callback) {
            fail(callback);
        }
    };

    public static final AiQuestionExtractor AI_EXTRACTOR = new AiQuestionExtractor() {
        @Override
        public void extract(String rawText, ExtractOptions options, Callback<ExtractResult> callback) {
            fail(callback);
        }
    };

    public static final StatsService STATS = new StatsService() {
        @Override
        public StatsSummary getSummary(long fromTime, long toTime) {
            return new StatsSummary();
        }

        @Override
        public List<TypeAccuracy> getTypeAccuracy(long fromTime, long toTime) {
            return Collections.emptyList();
        }

        @Override
        public List<DailyCount> getDailyCounts(long fromTime, long toTime) {
            return Collections.emptyList();
        }

        @Override
        public List<BankAccuracy> getBankAccuracy() {
            return Collections.emptyList();
        }
    };

    public static final UpdateService UPDATE = new UpdateService() {
        @Override
        public void checkUpdate(Callback<UpdateInfo> callback) {
            fail(callback);
        }

        @Override
        public void downloadApk(UpdateInfo info, ProgressListener listener, Callback<String> callback) {
            fail(callback);
        }
    };
}
