package com.ruddi.logiweb.service.impl;

import com.rabbitmq.client.Channel;
import com.ruddi.logiweb.service.api.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * mq service for working with mq
 * @author Ivan Rud
 */
@Service
@Slf4j
public class MQServiceImpl implements MQService {
    Channel channel;

    @Autowired
    public MQServiceImpl(
            @Qualifier("mqChannel") Channel channel) {
        this.channel = channel;
    }

    /**
     * sending message to mq
     * @param message message to send
     */
    @Override
    public void send(String message) {
        log.info("send(message) method was called");
        try {
            channel.basicPublish("", "logiweb", null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
