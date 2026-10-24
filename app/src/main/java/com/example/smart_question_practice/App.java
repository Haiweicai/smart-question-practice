package com.example.smart_question_practice;

import android.app.Application;

/**
 * 应用入口。
 *
 * 骨架阶段只做最小初始化；业务初始化（登录态、云同步、崩溃上报等）
 * 请在各接口的实现类完成后，在这里接入 {@link core.ServiceLocator}。
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 在这里注入你自己实现的 ServiceLocator 实现
        // ServiceLocator.install(new MyQuestionBankService(), new MyExamService(), ...);
    }
}
