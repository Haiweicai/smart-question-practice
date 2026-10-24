package com.example.smart_question_practice.ui.exam;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;
import com.example.smart_question_practice.ui.BaseFragment;

/**
 * ExamReview页。
 *
 * TODO 需要接入：ExamService（试卷回顾）
 * 当前仅负责加载布局 fragment_exam_review.xml，页面上的交互与数据均为空。
 */
public class ExamReviewFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exam_review;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
