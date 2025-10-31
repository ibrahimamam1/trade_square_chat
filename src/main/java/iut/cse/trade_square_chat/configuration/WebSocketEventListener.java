package iut.cse.trade_square_chat.configuration;

import iut.cse.trade_square_chat.models.Entities.Message;
import iut.cse.trade_square_chat.services.MessageService;
import iut.cse.trade_square_chat.services.OnlineUserTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Component
public class WebSocketEventListener {

    @Autowired
    private OnlineUserTracker onlineUserTracker;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = headerAccessor.getFirstNativeHeader("userId");

        if (userId != null) {
            Long userIdLong = Long.parseLong(userId);
            onlineUserTracker.addUser(userIdLong);

            // Send any unsent messages to the newly connected user
            List<Message> unsentMessages = messageService.getUnsentMessages(userIdLong);
            for (Message message : unsentMessages) {
                messagingTemplate.convertAndSendToUser(
                        userId,
                        "/queue/messages",
                        message
                );
            }
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = headerAccessor.getFirstNativeHeader("userId");

        if (userId != null) {
            Long userIdLong = Long.parseLong(userId);
            onlineUserTracker.removeUser(userIdLong);
        }
    }
}