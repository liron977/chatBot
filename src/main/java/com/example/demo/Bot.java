package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{



    private Long userWhoSentMessage;
    private String message;
    private  Long chatId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserWhoSentMessage() {
        return userWhoSentMessage;
    }

    public void setUserWhoSentMessage(Long userWhoSentMessage) {
        this.userWhoSentMessage = userWhoSentMessage;
    }

    @Override
    public String getBotUsername() {
        return "LironChatBot";
    }

    @Override
    public String getBotToken() {
        return "6512384578:AAETwe7C5g7I0ELPwSR4dkoOPbwkWgLCTjA";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        User user = msg.getFrom();
        setUserWhoSentMessage(user.getId());
        chatId = msg.getChatId();
        System.out.println(user.getId()+chatId);
        setMessage(msg.getText());

        ChatController chatController = new ChatController();


        String result = chatController.addMessage(chatId, msg.getText(), user.getId());
        System.out.println(result);
        System.out.println(user.getFirstName() + " wrote " + msg.getText());
        sendText(userWhoSentMessage, "Hello World!");

    }
    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

}
