package iut.cse.trade_square_chat.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String status; // "DELIVERED" or "NOT_DELIVERED"
    private String reason; // null if delivered, error message if not
    private Long messageId; // ID of saved message if successful
    private LocalDateTime timestamp;
}