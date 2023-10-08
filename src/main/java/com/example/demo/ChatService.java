package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ChatService {

        @Autowired
        ChatBotRepository chatBotRepository;
        public List<ChatBot> getAllChatBots(){
            return chatBotRepository.findAll();
        }
        public ChatBot addChatBot(Long chatId,String message,Long userWhoSentMessage) {
            ChatBot chatBot = new ChatBot();
            chatBot.setChatId(chatId);
            chatBot.setMessage(message);
            chatBot.setUserWhoSentMessage(userWhoSentMessage);

            return chatBotRepository.save(chatBot);
        }
     /*
        // Getting a specific book by category from collection
  public List<ChatBot> getBookByCategory(String category){
            List<ChatBot> book = chatBotRepository.findByCategory(category);
            return book;
        }

        // Getting a specific book by book id from collection
        public Book getBookByBookId(long bookId){
            Book book = bookRepository.findByBookId(bookId);
            return book;
        }

         Adding/inserting a book into collection
        public Book addBook(long id,String isbnNumber, String bookName,String category) {
            Book book = new Book();
            book.setCategory(category);
            book.setBookId(id);
            book.setBookName(bookName);
            book.setIsbnNumber(isbnNumber);
            return bookRepository.save(book);
        }

        // Delete a book from collection
        public int deleteBook(long bookId){
            Book book = bookRepository.findByBookId(bookId);
            if(book != null){
                bookRepository.delete(book);
                return 1;
            }
            ret*//*  public List<ChatBot> getBookByCategory(String category){
            List<ChatBot> book = chatBotRepository.findByCategory(category);
            return book;
        }

        // Getting a specific book by book id from collection
        public Book getBookByBookId(long bookId){
            Book book = bookRepository.findByBookId(bookId);
            return book;
        }

        // Adding/inserting a book into collection
        public Book addBook(long id,String isbnNumber, String bookName,String category) {
            Book book = new Book();
            book.setCategory(category);
            book.setBookId(id);
            book.setBookName(bookName);
            book.setIsbnNumber(isbnNumber);
            return bookRepository.save(book);
        }

        // Delete a book from collection
        public int deleteBook(long bookId){
            Book book = bookRepository.findByBookId(bookId);
            if(book != null){
                bookRepository.delete(book);
                return 1;
            }
            return -1;*/
        }