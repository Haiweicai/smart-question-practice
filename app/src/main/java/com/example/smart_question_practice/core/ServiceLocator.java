package com.example.smart_question_practice.core;

import androidx.annotation.NonNull;

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
import com.example.smart_question_practice.core.stub.NoOpServices;

/**
 * 全局服务注册表。
 *
 * 用法（二次开发第一步）：
 * <pre>
 * public class App extends Application {
 *     public void onCreate() {
 *         super.onCreate();
 *         ServiceLocator.installAuth(new MyAuthService());
 *         ServiceLocator.installBank(new MyQuestionBankService());
 *         // ...
 *     }
 * }
 * </pre>
 *
 * 默认为 {@link NoOpServices}，所以未实现的模块点击只会提示「尚未实现」。
 */
public final class ServiceLocator {

    private static volatile AuthService auth = NoOpServices.AUTH;
    private static volatile QuestionBankService bank = NoOpServices.BANK;
    private static volatile QuestionParser parser = NoOpServices.PARSER;
    private static volatile AnswerMatcher matcher = NoOpServices.MATCHER;
    private static volatile PracticeService practice = NoOpServices.PRACTICE;
    private static volatile ExamService exam = NoOpServices.EXAM;
    private static volatile ClassService classService = NoOpServices.CLASSP;
    private static volatile FriendService friend = NoOpServices.FRIEND;
    private static volatile LanExamService lanExam = NoOpServices.LAN_EXAM;
    private static volatile AiGrader aiGrader = NoOpServices.AI_GRADER;
    private static volatile AiQuestionExtractor aiExtractor = NoOpServices.AI_EXTRACTOR;
    private static volatile StatsService stats = NoOpServices.STATS;
    private static volatile UpdateService update = NoOpServices.UPDATE;

    private ServiceLocator() {
    }

    public static AuthService auth() { return auth; }
    public static QuestionBankService bank() { return bank; }
    public static QuestionParser parser() { return parser; }
    public static AnswerMatcher matcher() { return matcher; }
    public static PracticeService practice() { return practice; }
    public static ExamService exam() { return exam; }
    public static ClassService classService() { return classService; }
    public static FriendService friend() { return friend; }
    public static LanExamService lanExam() { return lanExam; }
    public static AiGrader aiGrader() { return aiGrader; }
    public static AiQuestionExtractor aiExtractor() { return aiExtractor; }
    public static StatsService stats() { return stats; }
    public static UpdateService update() { return update; }

    public static void installAuth(@NonNull AuthService impl) { auth = impl; }
    public static void installBank(@NonNull QuestionBankService impl) { bank = impl; }
    public static void installParser(@NonNull QuestionParser impl) { parser = impl; }
    public static void installMatcher(@NonNull AnswerMatcher impl) { matcher = impl; }
    public static void installPractice(@NonNull PracticeService impl) { practice = impl; }
    public static void installExam(@NonNull ExamService impl) { exam = impl; }
    public static void installClass(@NonNull ClassService impl) { classService = impl; }
    public static void installFriend(@NonNull FriendService impl) { friend = impl; }
    public static void installLanExam(@NonNull LanExamService impl) { lanExam = impl; }
    public static void installAiGrader(@NonNull AiGrader impl) { aiGrader = impl; }
    public static void installAiExtractor(@NonNull AiQuestionExtractor impl) { aiExtractor = impl; }
    public static void installStats(@NonNull StatsService impl) { stats = impl; }
    public static void installUpdate(@NonNull UpdateService impl) { update = impl; }
}
