package com.samp.service.impl;

import com.samp.dto.event.QueryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyQueryEvent(QueryEvent event) {
        log.info("Broadcasting WebSocket notification for Query ID {} to /topic/queries and /topic/notifications", event.getQueryId());
        
        messagingTemplate.convertAndSend("/topic/queries", event);
        messagingTemplate.convertAndSend("/topic/notifications", event);

        if (event.getStudentEmail() != null) {
            messagingTemplate.convertAndSendToUser(
                    event.getStudentEmail(),
                    "/queue/notifications",
                    event
            );
        }
    }
}
