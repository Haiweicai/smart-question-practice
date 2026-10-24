package com.example.smart_question_practice.network;

import com.example.smart_question_practice.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 客户端工厂，按 base url 区分三套 API：
 * <ul>
 *   <li>业务后端（{@link BizApi}）——地址来自 BuildConfig.SERVER_URL</li>
 *   <li>统计后端（{@link StatsApi}）——同样指向业务后端</li>
 *   <li>AI 服务（{@link AiQuestionApi}）——OpenAI 兼容接口，密钥来自 BuildConfig</li>
 * </ul>
 *
 * 所有地址与密钥都通过环境变量注入（见 app/build.gradle 的 buildConfigField），
 * 仓库里不保存任何真实值。
 */
public final class RetrofitClient {

    private static final String ARK_BASE_URL = "https://ark.cn-beijing.volces.com/api/v3/";
    private static final String DEEPSEEK_BASE_URL = "https://api.deepseek.com/v1/";

    private static volatile RetrofitClient instance;

    private final BizApi bizApi;
    private final StatsApi statsApi;
    private final AiQuestionApi aiQuestionApi;

    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit server = new Retrofit.Builder()
                .baseUrl(normalizeBaseUrl(BuildConfig.SERVER_URL))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bizApi = server.create(BizApi.class);
        statsApi = server.create(StatsApi.class);

        Retrofit ai = new Retrofit.Builder()
                .baseUrl(DEEPSEEK_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        aiQuestionApi = ai.create(AiQuestionApi.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public BizApi bizApi() {
        return bizApi;
    }

    public StatsApi statsApi() {
        return statsApi;
    }

    public AiQuestionApi aiQuestionApi() {
        return aiQuestionApi;
    }

    /** Retrofit 要求 baseUrl 以 "/" 结尾。 */
    private static String normalizeBaseUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "http://127.0.0.1:3000/";
        }
        return url.endsWith("/") ? url : url + "/";
    }

    /** 根据模型名返回对应的 base url。 */
    public static String baseUrlByModel(String model) {
        if (model != null && model.toLowerCase().contains("doubao")) {
            return ARK_BASE_URL;
        }
        return DEEPSEEK_BASE_URL;
    }

    /** 根据模型名返回对应的 API Key。 */
    public static String apiKeyByModel(String model) {
        if (model != null && model.toLowerCase().contains("doubao")) {
            return BuildConfig.ARK_API_KEY;
        }
        return BuildConfig.DEEPSEEK_API_KEY;
    }
}
