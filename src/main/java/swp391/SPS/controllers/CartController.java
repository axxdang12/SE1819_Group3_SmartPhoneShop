package swp391.SPS.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;
import swp391.SPS.repositories.CartItemRepository;
import swp391.SPS.services.CartItemService;
import swp391.SPS.services.CartService;
import swp391.SPS.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartItemRepository cartItemRepository;

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

    @PostMapping("/cart/delete-phone/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Lấy giỏ hàng của người dùng
        Cart cart = cartService.getCart(authentication.getName());

        // Xóa sản phẩm khỏi giỏ hàng
        cartItemService.removePhoneFromCart(authentication.getName(), cart.getCartId(), id);

        // Cập nhật lại tổng số tiền và danh sách sản phẩm trong giỏ hàng

        List<CartItem> listPByC = cart.getItems();

        // Tạo đối tượng trả về dưới dạng JSON
        Map<String, Object> response = new HashMap<>();
        response.put("cartTotal", cartItemRepository.totalCart(cart.getCartId()));


        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/update-quantity/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProductQuantity(@PathVariable int id, @RequestBody Map<String, Integer> payload) {
        try {
            // Kiểm tra và lấy số lượng từ payload
            if (payload == null || !payload.containsKey("quantity")) {
                return ResponseEntity.badRequest().body("Invalid request payload");
            }

            int quantity = payload.get("quantity");

            if (quantity < 1) {
                return ResponseEntity.badRequest().body("Quantity must be greater than 0");
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Lấy giỏ hàng của người dùng
            Cart cart = cartService.getCart(username);

            // Cập nhật số lượng sản phẩm trong giỏ hàng
            cartItemService.updatePhoneQuantity(username, cart.getCartId(), id, quantity);

            // Tính toán lại tổng số tiền giỏ hàng và từng sản phẩm
            double cartTotal = cartItemRepository.totalCart(cart.getCartId());


            Map<String, Object> response = new HashMap<>();
            response.put("cartTotal", cartTotal);


            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi chi tiết
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating quantity: " + e.getMessage());
        }
    }


}
