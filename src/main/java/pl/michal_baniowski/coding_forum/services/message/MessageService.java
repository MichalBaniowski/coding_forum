package pl.michal_baniowski.coding_forum.services.message;

import pl.michal_baniowski.coding_forum.exception.MessageSendException;

public interface MessageService {
    void sendMessage(String message, String addressee, String subject) throws MessageSendException;
}
