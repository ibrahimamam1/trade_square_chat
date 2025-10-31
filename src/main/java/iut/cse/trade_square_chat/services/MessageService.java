package iut.cse.trade_square_chat.services;

import iut.cse.trade_square_chat.models.Entities.Message;
import iut.cse.trade_square_chat.models.DTOs.MessageResponse;
import iut.cse.trade_square_chat.models.Enums.MessageStatus;
import iut.cse.trade_square_chat.repositories.MessageRepository;
import iut.cse.trade_square_chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnlineUserTracker onlineUserTracker;

    public MessageResponse handleMessage(Message message) {
        Long receiverId = message.getReceiverId();
        Long senderId = message.getSenderId();

        // Verify receiver exists
        if (receiverId == null || !userRepository.existsById(receiverId)) {
            return MessageResponse.builder()
                    .status("NOT_DELIVERED")
                    .reason("Receiver ID not found")
                    .messageId(null)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        // Verify sender exists
        if (senderId == null || !userRepository.existsById(senderId)) {
            return MessageResponse.builder()
                    .status("NOT_DELIVERED")
                    .reason("Sender ID not found")
                    .messageId(null)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        // Check if sender is blocked
        List<Long> blocklist = userRepository.getBlocklist(receiverId);
        if (blocklist.contains(senderId)) {
            // Pretend message was delivered (don't inform sender they're blocked)
            message.setStatus(MessageStatus.BLOCKED);
            message.setTimestamp(LocalDateTime.now());
            Message savedMessage = messageRepository.save(message);

            return MessageResponse.builder()
                    .status("DELIVERED")
                    .reason(null)
                    .messageId(savedMessage.getId())
                    .timestamp(savedMessage.getTimestamp())
                    .build();
        }

        // Check if receiver is online
        boolean isReceiverOnline = onlineUserTracker.isUserOnline(receiverId);

        if (isReceiverOnline) {
            message.setStatus(MessageStatus.SENT);
        } else {
            message.setStatus(MessageStatus.UNSENT);
        }

        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        return MessageResponse.builder()
                .status("DELIVERED")
                .reason(null)
                .messageId(savedMessage.getId())
                .timestamp(savedMessage.getTimestamp())
                .build();
    }

    public void markMessageAsRead(Long messageId) {
        messageRepository.findById(messageId).ifPresent(message -> {
            message.setStatus(MessageStatus.READ);
            message.setReadTimestamp(LocalDateTime.now());
            messageRepository.save(message);
        });
    }

    public List<Message> getUnsentMessages(Long userId) {
        return messageRepository.findByReceiverIdAndStatus(userId, MessageStatus.UNSENT);
    }
}