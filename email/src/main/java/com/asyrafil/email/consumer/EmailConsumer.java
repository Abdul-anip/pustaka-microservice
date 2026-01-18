package com.asyrafil.email.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "emailQueue")
    public void receiveEmail(String message) {
        System.out.println("Menerima pesan dari RabbitMQ: " + message);

        String[] parts = message.split("\\|");
        if (parts.length == 3) {
            String to = parts[0];
            String subject = parts[1];
            String body = parts[2];

            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(to);
                mailMessage.setSubject(subject);
                mailMessage.setText(body);
                mailMessage.setFrom("asyrafilauliarasyid@gmail.com");

                mailSender.send(mailMessage);
                System.out.println("Email berhasil dikirim ke: " + to);
            } catch (Exception e) {
                System.err.println("Gagal mengirim email: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Format pesan tidak valid: " + message);
        }
    }
}
