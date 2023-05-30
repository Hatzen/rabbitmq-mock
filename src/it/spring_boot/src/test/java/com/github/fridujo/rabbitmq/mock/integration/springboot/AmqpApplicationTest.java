package com.github.fridujo.rabbitmq.mock.integration.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AmqpApplicationTestConfiguration.class)
public class AmqpApplicationTest {

    @Autowired
    private Sender sender;
    @Autowired
    private Receiver receiver;

    @Test
    public void message_is_received_when_sent_by_sender() throws InterruptedException {
        sender.send(1);
        List<String> receivedMessages = new ArrayList<>();
        waitAndReceive(receivedMessages);
        assertThat(receivedMessages).containsExactly("Hello 1 from RabbitMQ!");

        sender.send(2);
        waitAndReceive(receivedMessages);
        assertThat(receivedMessages).containsExactly("Hello 2 from RabbitMQ!");
    }

    private void waitAndReceive(List<String> receivedMessages)  throws InterruptedException {
        while (receivedMessages.isEmpty()) {
            receivedMessages.addAll(receiver.getMessages());
            TimeUnit.MILLISECONDS.sleep(100L);
        }
    }
}

