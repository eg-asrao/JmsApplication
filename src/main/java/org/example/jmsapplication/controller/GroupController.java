package org.example.jmsapplication.controller;

import lombok.RequiredArgsConstructor;

import org.example.jmsapplication.dto.GroupMessageDTO;
import org.example.jmsapplication.producer.GroupMessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller that exposes endpoints for managing clinician groups.
 * Sends JMS messages to notify about group creation or deletion events.
 */
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupMessageProducer producer;

    /**
     * Endpoint to create a group by sending a "CREATE" operation message.
     *
     * @param groupId        ID of the group to create
     * @param parentGroupId  ID of the parent group
     * @return HTTP response indicating success
     */
    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestParam String groupId, @RequestParam String parentGroupId) {
        sendMessage(groupId, parentGroupId, "CREATE");
        return ResponseEntity.ok("Group creation message sent");
    }

    /**
     * Endpoint to delete a group by sending a "DELETE" operation message.
     *
     * @param groupId    ID of the group to delete
     * @param parentGroupId  ID of the parent group
     * @return HTTP response indicating success
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam String groupId, @RequestParam String parentGroupId) {
        sendMessage(groupId, parentGroupId, "DELETE");
        return ResponseEntity.ok("Group deletion message sent");
    }

    /**
     * Helper method to build and send a group operation message.
     *
     * @param groupId        ID of the group
     * @param parentGroupId  ID of the parent group
     * @param operation      Operation type ("CREATE" or "DELETE")
     */
    private void sendMessage(String groupId, String parentGroupId, String operation) {
        GroupMessageDTO message = new GroupMessageDTO(groupId, parentGroupId, operation, LocalDateTime.now());
        producer.sendGroupMessage(message);
    }
}
