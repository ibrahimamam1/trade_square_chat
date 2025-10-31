package iut.cse.trade_square_chat.models.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    Long id;
    List<Long> blocklist;
}
