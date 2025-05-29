package org.example.jmsapplication.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.jmsapplication.dto.GroupMessageDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Listener for handling group deletion messages.
 *
 * <p>Listens to messages from the {@code group-events} queue
 * where the {@code operation} property is {@code DELETE}.</p>
 */
@Component
@Slf4j
public class DeleteGroupListener {

    /**
     * Handles incoming DELETE group messages from the JMS queue.
     *
     * @param message the received group message
     */
    @JmsListener(destination = "group-events", selector = "operation = 'DELETE'")
    public void onDelete(GroupMessageDTO message) {
        log.info("Delete listener received the message: {}", message);
        // Add delete handling logic here if needed
    }
}
