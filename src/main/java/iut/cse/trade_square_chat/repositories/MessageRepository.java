package iut.cse.trade_square_chat.repositories;

import iut.cse.trade_square_chat.models.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
