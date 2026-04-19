package com.permission.api.service.consumer;

import com.permission.api.dto.UserRegistrationEvent;
import com.permission.api.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.consumer.enabled", havingValue = "true", matchIfMissing = true)
public class UserRegistrationConsumer {

    private final UserAccountService userAccountService;

    @KafkaListener(
            topics = "${kafka.topics.user-registration}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(UserRegistrationEvent event) {
        log.info("Received user registration event: {}", event);
        try {
            userAccountService.processUserRegistration(event);
        } catch (Exception e) {
            log.error("Error processing user registration event: {}", event, e);
        }
    }
}
