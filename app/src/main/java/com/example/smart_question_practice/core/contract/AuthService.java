package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;
import com.example.smart_question_practice.data.room.User;

/**
 * 账号与会话。
 * 对应 HTTP 接口：api/cloud/register、api/cloud/login、api/cloud/status。
 */
public interface AuthService {

    void login(String account, String password, String device, Callback<User> callback);

    void register(String account, String password, String device, Callback<User> callback);

    /** 获取当前登录用户；未登录返回 null。 */
    User currentUser();

    void logout();
}
