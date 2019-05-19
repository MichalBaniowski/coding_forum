package pl.michal_baniowski.coding_forum.exception;

public class MessageSendException extends RuntimeException {
    public MessageSendException(String msg) {
        super(msg);
    }
}
