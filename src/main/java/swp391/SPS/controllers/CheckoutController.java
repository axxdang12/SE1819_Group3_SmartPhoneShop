package swp391.SPS.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp391.SPS.dtos.ProfileDto;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.User;
import swp391.SPS.services.CartService;
import swp391.SPS.services.UserService;


@Controller
public class CheckoutController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                return "checkout";
            }
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        Cart cart= cartService.getCart(authentication.getName());
        model.addAttribute("listPByC", cart.getItems());
        model.addAttribute("cartTotal", cart.getTotal());
        model.addAttribute("user", user);
        model.addAttribute("profileDto", new ProfileDto(user.getUserDetail().getFirstName(), user.getUserDetail().getLastName(),
        user.getUserDetail().getPhoneNumber(), user.getEmail(), user.getUserDetail().getGender(), user.getUserDetail().getAddress()));
            return "checkout";
        }
}
