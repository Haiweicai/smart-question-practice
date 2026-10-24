package com.example.smart_question_practice.data.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<String> fromJson(String json) {
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String toJson(List<String> list) {
        if (list == null) {
            return null;
        }
        return gson.toJson(list);
    }
}