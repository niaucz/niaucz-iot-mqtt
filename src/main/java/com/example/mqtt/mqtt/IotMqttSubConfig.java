package com.example.mqtt.mqtt;

import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * 消息订阅配置
 */
@Configuration
public class IotMqttSubConfig {

    @Autowired
    private IotMqttConfig iotMqttConfig;

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Bean
    public MessageChannel iotMqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer mqttInbound() {
        //TODO clientID生成
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("Sub-" + UUID.randomUUID(), mqttClientFactory, "data/environment");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(iotMqttInputChannel());
        return adapter;
    }

    /**
     * 消息订阅
     */
    @Bean
    @ServiceActivator(inputChannel = "iotMqttInputChannel")
    public MessageHandler subHandler() {

        return message -> {
            try {
                String string = message.getPayload().toString();
                System.out.println("接收到de数据：" + string);
            } catch (MessagingException ex) {
            }
        };
    }
}
