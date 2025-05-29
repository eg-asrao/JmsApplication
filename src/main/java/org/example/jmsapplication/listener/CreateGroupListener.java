package org.example.jmsapplication.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.jmsapplication.dto.GroupMessageDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Listener for handling "CREATE" group messages from the JMS queue "group-events".
 * Processes messages indicating creation of a group.
 */
@Component
@Slf4j
public class CreateGroupListener {

    /**
     * Listens to messages on the "group-events" queue where the JMS selector filters messages with operation='CREATE'.
     *
     * @param message The incoming group creation message DTO.
     * @throws RuntimeException if processing fails, to allow message redelivery or dead-letter routing.
     */
    @JmsListener(destination = "group-events", selector = "operation = 'CREATE'")
    public void onCreate(GroupMessageDTO message) {
        try {
            // Your processing logic here
            log.info("Create listener received the message: {}", message);

            // Simulate failure for testing (commented out)
            // throw new RuntimeException("Simulating failure");

            // ... actual processing ...

        } catch (Exception e) {
            // Log error and rethrow to trigger retry or DLQ handling
            log.error("Error processing create group message: {}", message, e);
            throw new RuntimeException("Processing failed for message: " + message, e);
        }
    }
}
