package org.example.jmsapplication.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.jmsapplication.dto.GroupMessageDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending group-related messages to the JMS queue.
 * Uses {@link JmsTemplate} to send messages to the "group-events" queue.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupMessageProducer {

    /** Spring's JMS template used to send messages to the broker. */
    private final JmsTemplate jmsTemplate;

    /**
     * Sends a group operation message (CREATE or DELETE) to the "group-events" destination.
     * A custom JMS property "operation" is added for message filtering by listeners.
     *
     * @param messageDTO the group message data containing groupId, parentGroupId, operation, and timestamp
     */
    public void sendGroupMessage(GroupMessageDTO messageDTO) {
        log.info("Sending message: {}", messageDTO);
        jmsTemplate.convertAndSend("group-events", messageDTO, m -> {
            // Attach operation type to message properties for filtering
            m.setStringProperty("operation", messageDTO.getOperation());
            return m;
        });
    }
}
