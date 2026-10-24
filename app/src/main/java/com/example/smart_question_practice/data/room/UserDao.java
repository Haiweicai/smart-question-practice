package com.example.smart_question_practice.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    User getUserById(int id);

    @Insert
    void insertUser(User user);

    @Query("UPDATE user SET username = :newUsername WHERE id = :userId")
    void updateUsername(int userId, String newUsername);

    @Query("UPDATE user SET password = :newPassword WHERE id = :userId")
    void updatePassword(int userId, String newPassword);

    @Query("DELETE FROM user WHERE id = :userId")
    void deleteUserById(int userId);
}