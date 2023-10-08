package com.example.demo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


public class ChatBot {

    @Id
    private Long userWhoSentMessage;
    private String message;
    private Long chatId;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserWhoSentMessage(Long userWhoSentMessage) {
        this.userWhoSentMessage = userWhoSentMessage;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public Long getUserWhoSentMessage() {
        return userWhoSentMessage;
    }

    public Long getChatId() {
        return chatId;
    }
}