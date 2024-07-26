package swp391.SPS.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import swp391.SPS.dtos.UserDto;
import swp391.SPS.entities.EmailDetails;
import swp391.SPS.entities.User;
import swp391.SPS.repositories.UserRepository;
import swp391.SPS.services.EmailService;
import swp391.SPS.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public static String EMAIL_PATTERN = "^(?=.{0,64}@(\\S))[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{1,})(\\S)$";

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(
            @Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model) {
        List<String> messageList = new ArrayList<>();
        List<FieldError> fieldErrors = result.getFieldErrors();

        try {
            if (result.hasErrors()) {
                for (FieldError fieldError : fieldErrors) {
                    messageList.add(fieldError.getDefaultMessage());
                }
                model.addAttribute("messageList", messageList);
                return "register";
            }
            String username = userDto.getUsername();
            User user = userService.findByUsername(username);
            User userByEmail = userService.findByEmail(userDto.getEmail());
            if (user != null || userByEmail != null) {
                model.addAttribute("userDto", userDto);
                System.out.println("user not null");
                model.addAttribute("usernameError", "Your username or email has been registered!");
                return "register";
            }
            if (Pattern.matches(EMAIL_PATTERN, userDto.getEmail()) == false) {
                model.addAttribute("passwordError", "Email invalid!");
                return "register";
            }
            if (userDto.getPassword().equals(userDto.getRepeatPassword())) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userService.save(userDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                EmailDetails emailDetails = EmailDetails.builder().recipient(userDto.getEmail()).subject("Account create success").msgBody("Your account have been create success! \nYour account is: " + userDto.getUsername() + "\nYour password is: " + userDto.getRepeatPassword() + "\nPlease do not share your password with anyone!").build();
                emailService.sendSimpleMail(emailDetails, userDto);
                model.addAttribute("userDto", userDto);
            } else {
                model.addAttribute("userDto", userDto);
                model.addAttribute("passwordError", "Your password not match! Check again!");
                System.out.println("password not same");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";
    }
}
