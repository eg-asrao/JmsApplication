package org.example.jmsapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a message related to group operations.
 * This DTO is used for JMS messaging to communicate group create/delete events.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageDTO implements Serializable {

    /**
     * Unique identifier of the group.
     */
    private String groupId;

    /**
     * Identifier of the parent group to which this group belongs.
     */
    private String parentGroupId;

    /**
     * The operation type: "CREATE" or "DELETE".
     */
    private String operation;

    /**
     * Timestamp of when the message was created.
     */
    private LocalDateTime timestamp;
}
