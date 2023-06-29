package com.parking.buddy.controller;

import com.parking.buddy.entity.Chat;
import com.parking.buddy.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/chats/")
    public Chat createChat(@RequestBody Chat chat){
        return chatService.createChat(chat);
    }

    @GetMapping("/chats")
    public List<Chat> getChatByBookingId(@RequestParam Long bookingId){
        return chatService.getChatByBookingId(bookingId);
    }

    @DeleteMapping("/chats")
    public String deleteChat(@RequestParam Chat chat){
        return chatService.deleteChat(chat);
    }


}