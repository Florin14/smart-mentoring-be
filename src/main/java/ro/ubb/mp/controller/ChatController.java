package ro.ubb.mp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import ro.ubb.mp.controller.dto.request.MessageRequestDTO;
import ro.ubb.mp.dao.model.Message;
import ro.ubb.mp.dao.model.Submission;
import ro.ubb.mp.dao.model.User;
import ro.ubb.mp.service.message.MessageService;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    // Sending message to a public chat room
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendPublicMessage(MessageRequestDTO message) {
        message.setTimestamp(LocalDateTime.now());
        return messageService.saveMessage(message);
    }

    // Sending a private message to a specific user
    @MessageMapping("/chat.privateMessage/{receiverId}")
    public void sendPrivateMessage(MessageRequestDTO message, @PathVariable Long receiverId) {
        message.setTimestamp(LocalDateTime.now());
        User receiver = messageService.findUserById(receiverId);
        messageService.saveMessage(message);


        // Send private message only to the intended user
        messagingTemplate.convertAndSendToUser(receiver.getUsername(), "/queue/private", message);
    }
}
