package iut.cse.trade_square_chat.services;

import iut.cse.trade_square_chat.models.Entities.Message;
import iut.cse.trade_square_chat.repositories.MessageRepository;
import iut.cse.trade_square_chat.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;
    UserRepository userRepository;

    public String handleMessage(Message message) {
        //verify destination
        Long receiverId = message.getReceiverId();
        Long senderId = message.getSenderId();

        if(receiverId == null || !messageRepository.existsById(receiverId)) {
            //return json that has status: not delivered and reason: receiver id not found
        }

        //is sender blocked or no??
        List<Long> blocklist = userRepository.getBlocklist(receiverId);
        if(blocklist.contains(senderId)) {
            //return json with status: delivered
        }

        //is receiver online?? yes forward message to him, mark message as sent

        //else mark message as unsent

        //save message
        messageRepository.save(message);
        return "ok";
    }
}
