package com.parking.buddy.service;

import com.parking.buddy.entity.Chat;
import com.parking.buddy.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;


    public Chat createChat(Chat chat) {
        chat.setSentOn(new Date());
        return chatRepository.save(chat);
    }

    public List<Chat> getChatByBookingId(Long bookingId){

        return chatRepository.findByBookingId(bookingId);

    }

    public String deleteChat(Chat chat){
        try {
            chatRepository.delete(chat);
        }
        catch (Exception e) {
            return e.toString() ;
        }
        return "Chat Deleted Successfully";
    }




}