package swp391.SPS.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import swp391.SPS.dtos.ProfileDto;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.User;
import swp391.SPS.services.CartService;
import swp391.SPS.services.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;

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
        userService.saveProfile(profileDto, authentication.getName());
        return "profile";
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
