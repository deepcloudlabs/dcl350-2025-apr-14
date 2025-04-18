package com.example.securitycard.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "messagingStrategy", havingValue = "amqp")
@EnableAutoConfiguration(exclude = { 
		KafkaAutoConfiguration.class 
})
public class DisableKafkaAutoConfiguration {

}
