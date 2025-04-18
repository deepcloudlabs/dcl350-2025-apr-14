package com.example.securitycard.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "messagingStrategy", havingValue = "kafka")
@EnableAutoConfiguration(exclude = { RabbitAutoConfiguration.class })
public class DisableAmqpAutoConfiguration {

}
