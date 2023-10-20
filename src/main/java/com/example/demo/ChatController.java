package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
   ChatService chatService;
    @RequestMapping("/addNewChat")
    @ResponseBody
    public String addMessage(@RequestParam("chatId") long chatId, @RequestParam("message") String message,
                          @RequestParam("userWhoSentMessage")Long userWhoSentMessage){
        if(chatService.addChatBot(chatId,message,userWhoSentMessage) != null){
            return "Book got  Added Successfully";
        }else{
            return "Something went wrong !";
        }
    }
}
