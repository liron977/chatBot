package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class GoogleBardExample {
    public static void testMmain(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = args[0];
        AIClient client = new GoogleBardClient(token, Duration.ofMinutes(10));

        printChosenAnswer(client.ask("hey"));
    }

    private static void printChosenAnswer(Answer answer) {
        if (answer.getStatus() == AnswerStatus.OK) {
            log.info("Markdown Output: \n {}", answer.markdown());
        } else {
            log.info("No Answer: {}", answer);
        }
    }
}