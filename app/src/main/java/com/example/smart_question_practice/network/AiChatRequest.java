package com.example.smart_question_practice.network;

import java.util.List;

public class AiChatRequest {
    private String model;
    private List<Message> messages;
    private float temperature;
    /** 最大输出 token；null 时 Gson 不序列化该字段（沿用服务端默认值） */
    private Integer max_tokens;

    public AiChatRequest(String model, String systemPrompt, String userContent) {
        this(model, systemPrompt, userContent, null);
    }

    public AiChatRequest(String model, String systemPrompt, String userContent, Integer maxTokens) {
        this.model = model;
        this.messages = List.of(
                new Message("system", systemPrompt),
                new Message("user", userContent)
        );
        this.temperature = 0.1f;
        this.max_tokens = maxTokens;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
    public float getTemperature() { return temperature; }
    public void setTemperature(float temperature) { this.temperature = temperature; }
}
