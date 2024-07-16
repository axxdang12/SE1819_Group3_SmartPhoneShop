package swp391.SPS.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swp391.SPS.entities.EmailDetails;
import swp391.SPS.services.EmailService;

import java.io.UnsupportedEncodingException;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public void sendMail(@RequestBody EmailDetails details) throws MessagingException, UnsupportedEncodingException {
//        emailService.sendSimpleMail(details);
    }
}
