package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Demo8Application {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(Demo8Application.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);

        ChatGpt chatGpt=new ChatGpt( "https://api.openai.com/v1/chat/completions","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");
               System.out.println(chatGpt.chatGPT("hello, how are you? Can you tell me what's a Fibonacci Number?"));
       // bot.sendText(bot.userWhoSentMessage, "Hello World!");*/
    }

}
