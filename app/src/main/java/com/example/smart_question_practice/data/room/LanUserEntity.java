package com.example.smart_question_practice.data.room;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 局域网考试历史用户表：记录曾发现/邀请过的同局域网用户，
 * 即便对方当前离线也保留在列表中（红色状态灯）。
 * ip 设唯一索引作为同用户的去重键；username/deviceName 为可空 String，
 * 依据 Room 迁移铁律：可空列在 CREATE TABLE 中不加 NOT NULL。
 */
@Entity(tableName = "lan_user",
        indices = {@Index("userId"), @Index(value = "ip", unique = true)})
public class LanUserEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int userId;          // 本机用户 id（归属）
    private String username;     // 对方用户名
    private String ip;           // 对方 IP（去重键）
    private String deviceName;   // 对方设备名
    private long lastSeen;       // 最后在线时间戳（ms）
    private int invitedCount;    // 累计邀请次数

    public LanUserEntity() {
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public long getLastSeen() { return lastSeen; }
    public void setLastSeen(long lastSeen) { this.lastSeen = lastSeen; }
    public int getInvitedCount() { return invitedCount; }
    public void setInvitedCount(int invitedCount) { this.invitedCount = invitedCount; }
}
