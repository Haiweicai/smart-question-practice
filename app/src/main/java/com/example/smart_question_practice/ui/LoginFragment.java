package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.smart_question_practice.R;

/**
 * 登录页。
 *
 * 骨架阶段：展示布局后自动进入首页，方便你先跑通整个应用外壳。
 * 二次开发：删除自动跳转，改为调用 {@code core.contract.AuthService#login(...)}，
 * 成功后再 navigate 到 {@code R.id.action_loginFragment_to_homeFragment}。
 */
public class LoginFragment extends BaseFragment {

    private static final long DEMO_DELAY_MS = 1200L;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 替换为真实的登录逻辑
        handler.postDelayed(() -> {
            if (isAdded()) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_homeFragment);
            }
        }, DEMO_DELAY_MS);
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }
}
