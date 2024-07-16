package swp391.SPS.services;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import swp391.SPS.dtos.UserDto;
import swp391.SPS.entities.EmailDetails;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendSimpleMail(EmailDetails details, UserDto userDto) throws MessagingException, UnsupportedEncodingException;

    void sendEmail(String targetEnail, String link) throws MessagingException, UnsupportedEncodingException;
}
