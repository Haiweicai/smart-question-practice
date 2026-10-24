package com.example.smart_question_practice.ui.question;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;
import com.example.smart_question_practice.ui.BaseFragment;

/**
 * Essay页。
 *
 * TODO 需要接入：PracticeService + AiGrader（简答题视图）
 * 当前仅负责加载布局 fragment_essay.xml，页面上的交互与数据均为空。
 */
public class EssayFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_essay;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
