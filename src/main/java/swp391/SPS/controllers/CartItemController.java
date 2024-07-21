    package swp391.SPS.controllers;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.AnonymousAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import swp391.SPS.entities.Cart;
    import swp391.SPS.services.CartItemService;
    import swp391.SPS.services.CartService;
    import swp391.SPS.services.PhoneService;
    import swp391.SPS.services.UserService;


    @Controller
    public class CartItemController {

        @Autowired
        PhoneService phoneService;
        @Autowired
        CartItemService cartItemService;
        @Autowired
        CartService cartService;
        @Autowired
        UserService userService;

            @GetMapping("/cart/delete-phone/{id}")
        public String deletePhone(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                return "cart";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            Cart cart= cartService.getCart(authentication.getName());
            cartItemService.removePhoneFromCart(authentication.getName(), cart.getCartId(),id);
            model.addAttribute("cartTotal", cart.getTotal());
            model.addAttribute("listPByC", cart.getItems());
            return "redirect:/cart";
        }

            @GetMapping("/cart/phone/{id}")
        public String addPhoneToCart(@PathVariable("id") int id, Model model){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                return "cart";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            cartItemService.addPhoneToCart(authentication.getName(),id);
            Cart cart= cartService.getCart(authentication.getName());
            model.addAttribute("listPByC", cart.getItems());
            model.addAttribute("cartTotal", cart.getTotal());
                return "redirect:/shop";
        }

        @PostMapping("/cart/update-quantity/{id}")
        public String updateQuantity(@PathVariable("id") int id, @RequestParam("quantity") int quantity, Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                return "cart";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            Cart cart= cartService.getCart(authentication.getName());
            cartItemService.updatePhoneQuantity(authentication.getName(), cart.getCartId(), id, quantity);
            model.addAttribute("listPByC", cart.getItems());
            model.addAttribute("cartTotal", cart.getTotal());
            return "redirect:/cart";
        }
    }
