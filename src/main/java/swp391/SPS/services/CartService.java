package swp391.SPS.services;
//import swp391.SPS.entities.Accessory;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.Phone;

import java.util.List;

public interface CartService {
    Cart getCart(String userName);
    void clearCart(Cart cart);
}
