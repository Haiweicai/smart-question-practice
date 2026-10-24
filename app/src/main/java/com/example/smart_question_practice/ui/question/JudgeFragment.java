package com.example.smart_question_practice.ui.question;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;
import com.example.smart_question_practice.ui.BaseFragment;

/**
 * Judge页。
 *
 * TODO 需要接入：PracticeService（判断题视图）
 * 当前仅负责加载布局 fragment_judge.xml，页面上的交互与数据均为空。
 */
public class JudgeFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_judge;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
