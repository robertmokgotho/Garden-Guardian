package com.agritech_innovator.gardenguardian.controller;

import com.agritech_innovator.gardenguardian.model.Contact;
import com.agritech_innovator.gardenguardian.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<String> submitContactForm(@RequestBody Contact contact) {
        contactService.processContactForm(contact);
        return ResponseEntity.ok("Message received successfully");
    }
}
