package iut.cse.trade_square_chat.repositories;

import iut.cse.trade_square_chat.models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u.blocklist from User u where u.id = ?1")
    List<Long> getBlocklist(Long id);

}
