package com.asyrafil.peminjaman.email;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmail(String to, String subject, String body) {
        String message = to + "|" + subject + "|" + body;
        rabbitTemplate.convertAndSend("emailQueue", message);

        System.out.println("Pesan email dikirim ke RabbitMQ: " + message);
    }
}
