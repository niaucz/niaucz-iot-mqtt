package com.example.mqtt;

import cn.hutool.json.JSONUtil;
import com.example.mqtt.entity.Environment;
import com.example.mqtt.entity.ModBusData;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.Date;

/**
 * @author ksd
 */
@Configuration
@IntegrationComponentScan
public class MqttSubscriberConfig {

    /**
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";

    @Autowired
    private EnvironmentMapper environmentMapper;
    @Value("${mqtt.username}")

    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.serverURIs}")
    private String hostUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.topic}")
    private String defaultTopic;

    /**
     * MQTT连接器选项
     */
    @Bean
    public MqttConnectOptions getReceiverMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户名
        if (!"".equals(username.trim())) {
            options.setUserName(username);
        }
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        // 设置连接的地址
        options.setServerURIs(new String[]{hostUrl});
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        return options;
    }

    /**
     * MQTT客户端
     */
    @Bean
    public MqttPahoClientFactory receiverMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getReceiverMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道（消费者）
     */
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }


    /**
     * MQTT消息订阅绑定（消费者）
     */
    @Bean
    public MessageProducer inbound() {
        // 可以同时消费（订阅）多个Topic
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId, receiverMqttClientFactory(),
                        defaultTopic, defaultTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * MQTT消息处理器（消费者）
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                String msg = message.getPayload().toString();
                System.out.println("\n--------------------START-------------------\n" +
                        "接收到订阅消息:\ntopic:" + topic + "\nmessage:" + msg +
                        "\n---------------------END--------------------");
                ModBusData modBusData = JSONUtil.toBean(msg, ModBusData.class);
                System.out.println("modBusData ----> " + modBusData);
                Integer[] data = modBusData.getData();
                System.out.println("----------------理论数据-----------------");
                System.out.println("co2 ---> " + MathFormula.co2(data[0], data[1]) + " ppm");
                System.out.println("pm25 ---> " + MathFormula.pm25(data[6], data[7]) + " ug/m³");
                System.out.println("humidity ---> " + MathFormula.humidity(data[10], data[11]) + " %RH");
                System.out.println("temperature ---> " + MathFormula.temperature(data[8], data[9]) + " ℃");
                Environment environment = new Environment();
                environment.setClientId(modBusData.getClientId());
                environment.setSlaveId(modBusData.getSlaveID());
                environment.setCo2(MathFormula.co2(data[0], data[1]));
                environment.setPm25(MathFormula.pm25(data[6], data[7]));
                environment.setHumidity(MathFormula.humidity(data[10], data[11]));
                environment.setTemperature(MathFormula.temperature(data[8], data[9]));
                environment.setDataTime(new Date());
                environment.setCreateTime(new Date());
                environmentMapper.insert(environment);
//                System.out.println("-----------------测试数据----------------");
//                for (int i = 0; i < data.length; i += 2) {
//                    System.out.println(i+"-"+(i+1) +"-"+ "co2 ---> " + MathFormula.co2(data[i], data[i + 1]));
//                    System.out.println(i+"-"+(i+1) +"-"+ "pm25 ---> " + MathFormula.pm25(data[i], data[i + 1]));
//                    System.out.println(i+"-"+(i+1) +"-"+ "humidity ---> " + MathFormula.humidity(data[i], data[i + 1]));
//                    System.out.println(i+"-"+(i+1) +"-"+ "temperature ---> " + MathFormula.temperature(data[i], data[i + 1]));
//                    System.out.println();
//                }
            }
        };
    }
}
