package com.agritech_innovator.gardenguardian.service;

import com.agritech_innovator.gardenguardian.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private JavaMailSender mailSender;

    public void processContactForm(Contact contact) {
        // Log the form submission
        System.out.println("Received contact form submission:");
        System.out.println("Name: " + contact.getName());
        System.out.println("Email: " + contact.getEmail());
        System.out.println("Message: " + contact.getMessage());

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("your-email@gmail.com"); // Replace with your email
        message.setSubject("New Contact Form Submission");
        message.setText(
                "New message from: " + contact.getName() + "\n" +
                        "Email: " + contact.getEmail() + "\n" +
                        "Message: " + contact.getMessage()
        );
        message.setFrom("no-reply@yourdomain.com"); // Optional
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            // Optionally, rethrow or handle differently
        }
    }
}
