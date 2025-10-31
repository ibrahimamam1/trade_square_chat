package iut.cse.trade_square_chat.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypingIndicator {
    private Long senderId;
    private Long receiverId;
    private boolean typing;
}
