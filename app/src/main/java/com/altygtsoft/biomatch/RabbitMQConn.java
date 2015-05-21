package com.altygtsoft.biomatch;

/*
 * Created by altug on 20.05.2015.
 */
import android.util.Log;
import android.widget.Toast;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConn {

    private final static String QUEUE_NAME = "id";

    public void rabbitMQSend(String message)
    {
        try
        {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("188.166.67.19");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.d("ERROR", e.getMessage());

        }
        catch (TimeoutException e) {
            e.printStackTrace();
            Log.d("EROOR", e.getMessage());
        }
    }

    public void rabbitMQReceive() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("188.166.67.19");
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            if (connection != null) {
                channel = connection.createChannel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (channel != null) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        try {
            if (channel != null) {
                channel.basicConsume(QUEUE_NAME, true, consumer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            QueueingConsumer.Delivery delivery = null;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String message = null;
            if (delivery != null) {
                message = new String(delivery.getBody());
            }
            System.out.println(" [x] Received '" + message + "'");

        }
    }


}
