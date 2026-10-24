package com.example.smart_question_practice.data.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 刷题进度实体，用于跨进程持久化。
 * 使用 Room 数据库替代 SharedPreferences/文件存储，解决 MIUI 等系统上数据不落盘的问题。
 */
@Entity(tableName = "practice_progress")
public class PracticeProgressEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public int userId;
    public String bankId;
    public int currentIndex;
    public int totalCount;
    public String practiceMode;
    public String questionIdsJson;      // List<String> 的 JSON
    public String optionPermsJson;      // Map<String, List<Integer>> 的 JSON
    public String userAnswersJson;      // Map<String, String> 的 JSON
    public String submittedJson;        // Map<String, Boolean> 的 JSON
    public String answerStatusJson;     // Map<String, Boolean> 的 JSON
    public long savedTime;
}
