package swp391.SPS.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp391.SPS.dtos.ChangePassDto;
import swp391.SPS.dtos.UserDto;
import swp391.SPS.entities.User;
import swp391.SPS.exceptions.UserNotFoundException;
import swp391.SPS.services.EmailService;
import swp391.SPS.services.UserService;
import swp391.SPS.utils.GeneratePassword;
import swp391.SPS.utils.Utility;

import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws UserNotFoundException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;
        emailService.sendEmail(email, resetPasswordLink);
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        return "forgot-password-form";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "reset-password-form";
        }

        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@Valid @ModelAttribute("changePassDto") ChangePassDto changePassDto, @RequestParam(value = "token") String token, BindingResult result, Model model) {

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "reset-password-form";
        } else if (changePassDto.getPassword().equals(changePassDto.getRepeatPassword())) {
            userService.updatePassword(user, changePassDto.getPassword());
            model.addAttribute("message", "You have successfully changed your password.");
            return "redirect:page/login";
        } else {
            model.addAttribute("message", "Password not the same.");
        }

        return "reset-password-form";
    }
}
