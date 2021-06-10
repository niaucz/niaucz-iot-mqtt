package com.example.mqtt.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * @author ksd
 */
@Configuration
@IntegrationComponentScan
public class IotMqttConfig {

//    @Value("${mqtt.client.id}")
//    public String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.host}")
    private String host;

    @Value("${mqtt.topic}")
    public String defaultTopic;

    @Value("${mqtt.auto-reconnect}")
    private Boolean autoReconnect;

    @Value("${mqtt.timeout}")
    private Integer timeout;

    @Value("${mqtt.keepalive}")
    private Integer keepAlive;

    /**
     * MQTT连接器选项
     */
    @Bean
    public MqttConnectOptions getReceiverMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户名
        options.setUserName(username);
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        // 设置连接的地址
        options.setServerURIs(new String[]{host});
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(timeout);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(keepAlive);
        options.setCleanSession(false);
        options.setAutomaticReconnect(autoReconnect);
        return options;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getReceiverMqttConnectOptions());
        return factory;
    }
}
