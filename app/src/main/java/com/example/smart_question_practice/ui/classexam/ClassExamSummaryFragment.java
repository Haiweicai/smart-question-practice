package com.example.smart_question_practice.ui.classexam;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;
import com.example.smart_question_practice.ui.BaseFragment;

/**
 * ClassExamSummary页。
 *
 * TODO 需要接入：ExamService（考试详情汇总）
 * 当前仅负责加载布局 fragment_class_exam_summary.xml，页面上的交互与数据均为空。
 */
public class ClassExamSummaryFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class_exam_summary;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
