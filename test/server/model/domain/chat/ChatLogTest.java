package server.model.domain.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatLogTest {
    private ChatLog log;
    
    @BeforeEach
    void setUp() {
        log = new ChatLog();
    }
    
    private void addMessages() {
        for (int i = 0; i < 4; i++) {
            log.add(new Message(i + 1, "Message #" + (i + 1), LocalDate.now(), LocalTime.now(), new UnreadStatus(), null, null));
        }
        for (int i = 4; i < 10; i++) {
            log.add(new Message(i + 1, "Message #" + (i + 1), LocalDate.now(), LocalTime.now(), new ReadStatus(), null, null));
        }
    }
    
    @Test
    void getUnreadMessages() {
        addMessages();
        List<Message> messages = log.getUnreadMessages();
        assertEquals(4, messages.size());
    }
    
    @Test
    void isChatLockedTrue() {
        addMessages();
        log.setLocked(true);
        assertTrue(log.isChatLocked());
    }
    
    @Test
    void isChatLockedFalse() {
        addMessages();
        assertFalse(log.isChatLocked());
        log.setLocked(false);
        assertFalse(log.isChatLocked());
    }
}