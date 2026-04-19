package com.permission.api.service.consumer;

import com.permission.api.dto.AccountRegistrationEvent;
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
public class AccountRegistrationConsumer {

    private final UserAccountService userAccountService;

    @KafkaListener(
            topics = "${kafka.topics.account-registration}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(AccountRegistrationEvent event) {
        log.info("Received account registration event: {}", event);
        try {
            userAccountService.processAccountSystemRegistration(
                    event.getIdUser(), event.getIdAccount(), event.getIdSystem());
        } catch (Exception e) {
            log.error("Error processing account registration event: {}", event, e);
        }
    }
}
