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
//import swp391.SPS.services.AccessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.Phone;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.services.CartItemService;
import swp391.SPS.services.CartService;
import swp391.SPS.services.PhoneService;
import swp391.SPS.services.UserService;

@Controller
public class SingleProductController {
    @Autowired
    PhoneService phoneService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    @GetMapping("/single-product")
    public String SingleProduct(@RequestParam("id") String idPhone, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String role = userService.findByUsername(authentication.getName()).getRoles().get(0).getRoleName();
            model.addAttribute("userRole", role);
            if ("ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role)) {
                throw new AccessDeniedException("You do not have permission to access this page");
            }
        }
        if(idPhone.isEmpty() || idPhone.equals("")) {
            throw new FileNotFoundException("Not Found");
        }
        int id = Integer.parseInt(idPhone);
        Phone p = phoneService.getPhoneByID(id);
        if(p!=null){
            model.addAttribute("product",p);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                model.addAttribute("check", false);
                return "single-product";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            model.addAttribute("check", false);
            return "single-product";
        }
        model.addAttribute("check", true);
        return "single-product";


    }


    @PostMapping("/cart-single/phone/{id}")
    public String addPhoneQuantityToCart(@PathVariable("id") String id, Model model, @RequestParam("quantity") int quantity) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(id.isEmpty() || id==null)   throw new FileNotFoundException("Not Found");

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "cart";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        cartItemService.addPhoneSingleToCart(authentication.getName(),Integer.parseInt(id), quantity);
        Cart cart= cartService.getCart(authentication.getName());
        model.addAttribute("listPByC", cart.getItems());
        model.addAttribute("cartTotal", cart.getTotal());
        return "redirect:/shop";
    }
}
