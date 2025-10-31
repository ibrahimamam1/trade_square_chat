package iut.cse.trade_square_chat.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @MessageMapping("/typing")
    public void handleTyping(@Payload TypingIndicator indicator) {
        messagingTemplate.convertAndSendToUser(
                indicator.getReceiverId().toString(),
                "/queue/typing",
                indicator
        );
    }

    @MessageMapping("/read")
    public void markAsRead(@Payload Long messageId) {
        Message updated = messageService.markMessageAsReadAndReturn(messageId);

        // notify sender that receiver has read it
        messagingTemplate.convertAndSendToUser(
                updated.getSenderId().toString(),
                "/queue/read-receipts",
                updated
        );
    }

    @GetMapping("/history/{userA}/{userB}")
    public List<Message> getHistory(@PathVariable Long userA, @PathVariable Long userB) {
        return messageService.getConversation(userA, userB);
    }

    @MessageMapping("/delete")
    public void deleteMessage(@Payload Long messageId) {
        messageService.deleteMessage(messageId);
    }

    @MessageMapping("/edit")
    public void editMessage(@Payload Message editedMessage) {
        Message updated = messageService.updateMessage(editedMessage);

        messagingTemplate.convertAndSendToUser(
                editedMessage.getReceiverId().toString(),
                "/queue/messages",
                updated
        );
    }

    @MessageMapping("/group")
    public void sendGroupMessage(@Payload GroupMessage message) {
        message.getMemberIds().forEach(member -> {
            messagingTemplate.convertAndSendToUser(
                    member.toString(), "/queue/group", message
            );
        });
    }

}