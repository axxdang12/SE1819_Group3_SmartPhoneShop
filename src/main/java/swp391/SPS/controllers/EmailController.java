package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swp391.SPS.entities.EmailDetails;
import swp391.SPS.services.EmailService;

@RestController
public class EmailController {
  @Autowired private EmailService emailService;

  @PostMapping("/sendMail")
  public String sendMail(@RequestBody EmailDetails details) {
    String status = emailService.sendSimpleMail(details);

    return status;
  }
}
