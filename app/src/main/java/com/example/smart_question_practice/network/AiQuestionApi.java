package com.example.smart_question_practice.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AiQuestionApi {

    @POST("chat/completions")
    Call<AiChatResponse> extractQuestions(
            @Header("Authorization") String authorization,
            @Body AiChatRequest request
    );
}
