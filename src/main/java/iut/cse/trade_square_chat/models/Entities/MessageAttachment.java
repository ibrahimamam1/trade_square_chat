package iut.cse.trade_square_chat.models.Entities;

import iut.cse.trade_square_chat.models.Enums.AttachmentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MessageAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long messageId;
    private String fileName;
    private String filePath;
    AttachmentType attachmentType;
}
