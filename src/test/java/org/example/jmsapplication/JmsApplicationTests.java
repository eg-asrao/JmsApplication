package org.example.jmsapplication;

import org.example.jmsapplication.controller.GroupController;
import org.example.jmsapplication.dto.GroupMessageDTO;
import org.example.jmsapplication.producer.GroupMessageProducer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class JmsApplicationTests {


    private final GroupMessageProducer producer = mock(GroupMessageProducer.class);
    private final GroupController controller = new GroupController(producer);

    @Test
    void createGroup_sendsCreateMessage() {
        String groupId = "G001";
        String parentGroupId = "P001";

        ResponseEntity<String> response = controller.createGroup(groupId, parentGroupId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Group creation message sent", response.getBody());

        ArgumentCaptor<GroupMessageDTO> captor = ArgumentCaptor.forClass(GroupMessageDTO.class);
        verify(producer, times(1)).sendGroupMessage(captor.capture());

        GroupMessageDTO sentMessage = captor.getValue();
        assertEquals(groupId, sentMessage.getGroupId());
        assertEquals(parentGroupId, sentMessage.getParentGroupId());
        assertEquals("CREATE", sentMessage.getOperation());
        assertNotNull(sentMessage.getTimestamp());
        assertTrue(sentMessage.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void deleteGroup_sendsDeleteMessage() {
        String groupId = "G002";
        String parentGroupId = "P002";

        ResponseEntity<String> response = controller.deleteGroup(groupId, parentGroupId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Group deletion message sent", response.getBody());

        ArgumentCaptor<GroupMessageDTO> captor = ArgumentCaptor.forClass(GroupMessageDTO.class);
        verify(producer, times(1)).sendGroupMessage(captor.capture());

        GroupMessageDTO sentMessage = captor.getValue();
        assertEquals(groupId, sentMessage.getGroupId());
        assertEquals(parentGroupId, sentMessage.getParentGroupId());
        assertEquals("DELETE", sentMessage.getOperation());
        assertNotNull(sentMessage.getTimestamp());
        assertTrue(sentMessage.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

}
