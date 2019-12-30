package com.semantyca.j2ee.mq;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MsgReceiver implements MessageListener {

    public MsgReceiver() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, "mq://127.0.0.1:7676");

            QueueConnection connection = connectionFactory.createQueueConnection("admin", "admin");
            connection.start();

            Session session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("FirstDest");
            MessageConsumer consumer = session.createConsumer(queue);

            consumer.setMessageListener(this);

            System.out.println("received");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Message msg) {
        if (msg instanceof TextMessage) {
            System.out.println(msg);
        }

    }

    public static void main(String[] args) {
        new MsgReceiver();

    }
}
