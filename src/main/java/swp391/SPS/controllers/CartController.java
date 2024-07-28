package swp391.SPS.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp391.SPS.entities.Cart;
import swp391.SPS.services.CartService;
import swp391.SPS.services.UserService;


@Controller
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    @GetMapping("/cart")
    public String shop(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String role = userService.findByUsername(authentication.getName()).getRoles().get(0).getRoleName();
            if ("ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role)) {
                throw new AccessDeniedException("You do not have permission to access this page");
            }
        }
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                return "cart";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            Cart cart= cartService.getCart(authentication.getName());
            model.addAttribute("listPByC", cart.getItems());
            model.addAttribute("cartTotal", cart.getTotal());
        return "cart";
        }
}
