package org.example.jmsapplication.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.jmsapplication.dto.GroupMessageDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Listener for handling messages sent to the Dead Letter Queue (DLQ).
 *
 * <p>This listener processes messages that could not be delivered or processed
 * successfully and have been moved to the DLQ for further inspection or handling.</p>
 */
@Slf4j
@Component
public class DeadLetterQueueListener {

    /**
     * Handles messages received on the Dead Letter Queue.
     *
     * @param failedMessage the message that failed processing and was routed to the DLQ
     */
    @JmsListener(destination = "ActiveMQ.DLQ")
    public void handleDeadMessages(GroupMessageDTO failedMessage) {
        log.warn("Message moved to DLQ: {}", failedMessage);
        // Additional handling like alerting or retrying can be added here
    }
}
