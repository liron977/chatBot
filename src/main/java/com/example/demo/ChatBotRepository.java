package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatBotRepository extends MongoRepository<ChatBot, String> {

}
