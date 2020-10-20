package com.example.Authentication.repository;

import com.example.Authentication.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<ChatRoom, String> {


    ChatRoom findChatRoomByChatNameEquals(String chatName);
    ChatRoom findChatRoomById(String id);
}