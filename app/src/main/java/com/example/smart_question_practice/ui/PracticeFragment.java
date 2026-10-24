package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;

/**
 * Practice页。
 *
 * TODO 需要接入：PracticeService + AnswerMatcher（答题流程、判分）
 * 当前仅负责加载布局 fragment_practice.xml，页面上的交互与数据均为空。
 */
public class PracticeFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_practice;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
