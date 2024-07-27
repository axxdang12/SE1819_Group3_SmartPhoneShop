package swp391.SPS.services.impls;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import swp391.SPS.dtos.UserDto;
import swp391.SPS.entities.EmailDetails;
import swp391.SPS.services.EmailService;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EmailDetails details, UserDto userDto) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("LKHT@gmail.com", "LKHT Support");
        helper.setTo(details.getRecipient());

        String subject = details.getSubject();

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Your account is:\"" + userDto.getUsername() + "\"</p>"
                + "<p>Your password is:\"" + userDto.getRepeatPassword() + "\"</p>"
                + "<br>"
                + "<p>Please don't share this to anyone else! ";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @Override
    public void sendEmail(String targetEnail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("LKHT@gmail.com", "LKHT Support");
        helper.setTo(targetEnail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>Your account have been create success!</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
