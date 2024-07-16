package swp391.SPS.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import swp391.SPS.dtos.ProfileDto;
import swp391.SPS.dtos.UpdatePassDto;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.User;
import swp391.SPS.services.CartService;
import swp391.SPS.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "profile";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("profileDto", new ProfileDto(user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(), user.getUserDetail().getPhoneNumber(), user.getEmail(), user.getUserDetail().getGender(), user.getUserDetail().getAddress()));
        model.addAttribute("updatePassDto", new UpdatePassDto());
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(Model model, @Valid @ModelAttribute("profileDto") ProfileDto profileDto, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "profile";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            model.addAttribute("user", userService.findByUsername(authentication.getName()));
            return "profile";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("updatePassDto", new UpdatePassDto());
        userService.saveProfile(profileDto, authentication.getName());
        return "profile";
    }

    @PostMapping("/profile/password")
    public String updatePass(Model model, @Valid @ModelAttribute("updatePassDto") UpdatePassDto updatePassDto, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        List<String> messageList = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "profile";
        }
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : fieldErrors) {
                messageList.add(fieldError.getDefaultMessage());
            }
            model.addAttribute("messageList", messageList);
            model.addAttribute("profileDto", new ProfileDto());
            model.addAttribute("isPassPage", true);
            return "profile";
        }
        if (updatePassDto.getNewPass().equalsIgnoreCase(updatePassDto.getConfirmPass())) {
            User user = userService.findByUsername(authentication.getName());
            user.setPassword(passwordEncoder.encode(updatePassDto.getNewPass()));
            userService.save(user);
            model.addAttribute("profileDto", new ProfileDto());
            model.addAttribute("notice", "Pass change successful");
            model.addAttribute("isPassPage", true);
            return "profile";
        }
        model.addAttribute("error", "Password not match");
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("profileDto", new ProfileDto());
        model.addAttribute("isPassPage", true);
        return "profile";
    }

    @GetMapping("/profileManager")
    public String profileManager(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "profileManager";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("profileDto", new ProfileDto(user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(), user.getUserDetail().getPhoneNumber(), user.getEmail(), user.getUserDetail().getGender(), user.getUserDetail().getAddress()));
        return "profileManager";
    }

    @PostMapping("/profileManager/update")
    public String updateProfileManager(Model model, @Valid @ModelAttribute("profileDto") ProfileDto profileDto, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "profileManager";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            model.addAttribute("user", userService.findByUsername(authentication.getName()));
            return "profileManager";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        userService.saveProfile(profileDto, authentication.getName());
        return "profileManager";
    }

    @PostMapping("/checkout/update")
    public String updateProfileCheckout(Model model, @Valid @ModelAttribute("profileDto") ProfileDto profileDto, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "redirect:/checkout";
        }
        if (bindingResult.hasErrors()) {
            Cart cart = cartService.getCart(authentication.getName());
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            model.addAttribute("listPByC", cart.getItems());
            model.addAttribute("cartTotal", cart.getTotal());
            model.addAttribute("user", userService.findByUsername(authentication.getName()));
            return "checkout";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        userService.saveProfileCheckout(profileDto, authentication.getName());
        return "redirect:/checkout";
    }
}
