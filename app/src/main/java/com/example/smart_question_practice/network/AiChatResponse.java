package com.example.smart_question_practice.network;

import java.util.List;

public class AiChatResponse {
    private List<Choice> choices;
    private Usage usage;

    public String getReplyContent() {
        if (choices == null || choices.isEmpty()) return "";
        return choices.get(0).getMessage().getContent();
    }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }

    public static class Choice {
        private Message message;

        public Message getMessage() { return message; }
        public void setMessage(Message message) { this.message = message; }

        public static class Message {
            private String content;

            public String getContent() { return content; }
            public void setContent(String content) { this.content = content; }
        }
    }

    /** OpenAI 兼容的用量字段，用于按 token 计费 */
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

        public int getPromptTokens() { return prompt_tokens; }
        public int getCompletionTokens() { return completion_tokens; }
        public int getTotalTokens() { return total_tokens; }
    }

    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }
}
