package swp391.SPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import swp391.SPS.entities.Accessory;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;
import swp391.SPS.entities.Phone;
import swp391.SPS.entities.User;
import swp391.SPS.repositories.CartItemRepository;
import swp391.SPS.repositories.CartRepository;
import swp391.SPS.repositories.UserRepository;
import swp391.SPS.services.CartService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart getCart(String userName) {
        int quantity=0;
        double total=0;
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        for (CartItem item : ciList) {
            if (item.getPhone().getStatus()) {
                item.setTotal(item.getTotalPrice());
                quantity += item.getQuantity();
                total += item.getTotal();
            }
        }
        cart.setQuantity(quantity);
        cart.setTotal(total);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void clearCart(Cart cart) {
        cartItemRepository.deleteAll(cart.getItems());
        cart.setItems(new ArrayList<>());
        cart.setQuantity(0);
        cart.setTotal(0.0);
        cartRepository.save(cart);
    }


}
