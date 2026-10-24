package com.example.smart_question_practice.ui.classexam;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;
import com.example.smart_question_practice.ui.BaseFragment;

/**
 * ExamGrading页。
 *
 * TODO 需要接入：ExamService + AiGrader（主观题批阅）
 * 当前仅负责加载布局 fragment_exam_grading.xml，页面上的交互与数据均为空。
 */
public class ExamGradingFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exam_grading;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
