package com.example.smart_question_practice.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 使用监控统计上报接口（独立于 AI 请求的 RetrofitClient，避免 base url 冲突）。
 * 服务端见 server/server.js：/api/heartbeat、/api/ai-usage、/api/crash。
 */
public interface StatsApi {

    @POST("api/heartbeat")
    Call<Void> heartbeat(@Body HeartbeatRequest body);

    @POST("api/ai-usage")
    Call<Void> aiUsage(@Body AiUsageRequest body);

    @POST("api/crash")
    Call<Void> crash(@Body CrashReport body);

    /** 心跳请求体 */
    class HeartbeatRequest {
        public String userId;
        public String appVersion;
        public String username;
        public String device;
        public boolean offline;
        public HeartbeatRequest(String userId, String appVersion, String username, String device) {
            this(userId, appVersion, username, device, false);
        }
        public HeartbeatRequest(String userId, String appVersion, String username, String device, boolean offline) {
            this.userId = userId;
            this.appVersion = appVersion;
            this.username = username;
            this.device = device;
            this.offline = offline;
        }
    }

    /** AI 用量上报体 */
    class AiUsageRequest {
        public String userId;
        public String username;
        public String device;
        public String model;
        public String scenario;
        public int promptTokens;
        public int completionTokens;
        public int totalTokens;
        public AiUsageRequest(String userId, String username, String device, String model, String scenario,
                              int promptTokens, int completionTokens, int totalTokens) {
            this.userId = userId;
            this.username = username;
            this.device = device;
            this.model = model;
            this.scenario = scenario;
            this.promptTokens = promptTokens;
            this.completionTokens = completionTokens;
            this.totalTokens = totalTokens;
        }
    }

    /** 崩溃上报体 */
    class CrashReport {
        public String userId;
        public String username;
        public String appVersion;
        public String device;
        public long crashTime;
        public String content;
        public CrashReport(String userId, String username, String appVersion, String device,
                           long crashTime, String content) {
            this.userId = userId;
            this.username = username;
            this.appVersion = appVersion;
            this.device = device;
            this.crashTime = crashTime;
            this.content = content;
        }
    }
}
