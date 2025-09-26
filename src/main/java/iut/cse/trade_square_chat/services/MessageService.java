package iut.cse.trade_square_chat.services;

import iut.cse.trade_square_chat.models.Entities.Message;
import iut.cse.trade_square_chat.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    MessageRepository messageRepository;

    public void handleMessage(Message message) {
        //verify sender

        //is sender blocked or no??


        //is receiver online?? yes forward message to him, mark message as sent

        //else mark message as unsent

        //save message
        messageRepository.save(message);
    }
}
