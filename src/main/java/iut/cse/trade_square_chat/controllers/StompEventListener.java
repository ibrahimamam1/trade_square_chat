package iut.cse.trade_square_chat.controllers;

import iut.cse.trade_square_chat.services.MessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class StompEventListener {
    MessageService messageService;
    public StompEventListener(MessageService messageService) {this.messageService = messageService;}

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        System.out.println("Someone connected at: " + event.getTimestamp());
    }
}
