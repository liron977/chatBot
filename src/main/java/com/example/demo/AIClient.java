package com.example.demo;


public interface AIClient {
    Answer ask(String question);
    void reset();
}