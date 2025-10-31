package iut.cse.trade_square_chat.controllers;

import iut.cse.trade_square_chat.models.Entities.Message;
import iut.cse.trade_square_chat.models.DTOs.MessageResponse;
import iut.cse.trade_square_chat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(@Payload Message message) {
        System.out.println("Received message from: " + message.getSenderId() +
                " to: " + message.getReceiverId());

        MessageResponse response = messageService.handleMessage(message);

        // Send response back to sender
        messagingTemplate.convertAndSendToUser(
                message.getSenderId().toString(),
                "/queue/reply",
                response
        );

        // If message was delivered, forward to receiver
        if (response.getStatus().equals("DELIVERED")) {
            messagingTemplate.convertAndSendToUser(
                    message.getReceiverId().toString(),
                    "/queue/messages",
                    message
            );
        }
    }
}