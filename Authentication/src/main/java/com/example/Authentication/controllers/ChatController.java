package com.example.Authentication.controllers;
import com.example.Authentication.models.ChatRoom;
import com.example.Authentication.models.Message;
import com.example.Authentication.security.jwt.JwtUtils;
import com.example.Authentication.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    ChatService service;
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    JwtUtils jwtUtils;
    private final Path rootLocation = Paths.get("fileStorage");

    //1- Gjej Listen e Room
    @GetMapping("/chatRoom/get")
    public List<ChatRoom> getAllChatRooms() {
        return service.findAllChatRooms();

    }

    //2- Krijo nje mesazh e ruan ne databaze dhe e ben push ne socket
    @PostMapping("/message/{id}")
    public Message saveAndPushToSocket(@PathVariable("id") String roomId,
                                       @RequestBody Message message
                                       //,@RequestParam("file") MultipartFile file
    ) throws Exception {
        ChatRoom chatRoom = service.findChatRoomById(roomId);

        List<Message> messages = chatRoom.getMessages();
        // nqs e kam modifikuar nje msg
        if (message.getId() != null) {
            for (Message msgOfChatroom : messages) {
                if (msgOfChatroom.getId().equals(message.getId())) {
                    msgOfChatroom.setContent(message.getContent());
                    break;
                }
            }

        } else {
            // nqs kam shtuar nje msg te ri
            message.setId(UUID.randomUUID());
            message.setTimestamp(new Date());
            messages.add(message);
        }

        chatRoom.setMessages(messages);

        service.udateChatRoom(chatRoom);
        this.template.convertAndSend("/topic/message", message);
        return message;

    }

    //3 - Gjej nje room ne duke i dhene id si path variabel
    @GetMapping("/chatRoom/{id}")
    public ChatRoom findChatRoomById(@PathVariable("id") String idChatRoom) {
        return service.findChatRoomById(idChatRoom);
    }

    //4 - Krijoj nje rooom te ri
    @PutMapping("/chatRoom")
    public ChatRoom createAndPushNewChatRoomToSocket(@RequestBody ChatRoom chatRoom) {
        service.createRoom(chatRoom);
        this.template.convertAndSend("/topic/rooms", chatRoom);
        return chatRoom;
    }



}