package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

        System.out.println(user.getFirstName() + " wrote " + msg.getText());
        invokeAddMessageFunction(chatId,message,userWhoSentMessage);
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
    public void invokeAddMessageFunction(long chatId, String message, long userWhoSentMessage) {
        try {
            // Set up the URL and open the connection
            URL url = new URL("http://localhost:8989/addNewChat");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method and properties
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parameters = "chatId=" + chatId + "&message=" + message + "&userWhoSentMessage=" + userWhoSentMessage;

            // Send the POST request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = parameters.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
