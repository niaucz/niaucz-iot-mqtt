package com.example.mqtt.mqtt;

import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class IotMqttPubConfig {

    @Autowired
    private IotMqttConfig iotMqttConfig;

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "iotMqttInputChannel")
    public MessageHandler mqttOutbound() {
        //TODO clientID生成
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("Pub-" + UUID.randomUUID(), mqttClientFactory);
        messageHandler.setAsync(false);
        messageHandler.setDefaultTopic(iotMqttConfig.defaultTopic);
        return messageHandler;
    }
}
