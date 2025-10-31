package iut.cse.trade_square_chat.models.Enums;

public enum MessageStatus {
    UNSENT,    // Message saved but receiver was offline
    SENT,      // Message delivered to online receiver
    READ,      // Message has been read by receiver
    BLOCKED    // Message blocked (sender is in receiver's blocklist)
}