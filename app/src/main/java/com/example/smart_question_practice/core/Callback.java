package com.example.smart_question_practice.core;

/**
 * 统一的异步回调。所有业务接口都用它把结果交回 UI 层。
 * 实现方需要在主线程回调（可用 Handler 切线程）。
 */
public interface Callback<T> {

    void onSuccess(T data);

    void onError(String message);
}
