package iut.cse.trade_square_chat.repositories;

import iut.cse.trade_square_chat.models.Entities.Message;
import iut.cse.trade_square_chat.models.Enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiverIdAndStatus(Long receiverId, MessageStatus status);

    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Message> findByReceiverId(Long receiverId);

    List<Message> findBySenderId(Long senderId);
}