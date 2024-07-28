package swp391.SPS.services;

import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;
import swp391.SPS.entities.Phone;

import java.util.Collection;
import java.util.List;

public interface CartItemService {
    void removePhoneFromCart(String userName,int cartId, int phoneId);
    void addPhoneToCart(String userName, int phoneId);
    void updatePhoneQuantity(String userName,int cartId, int phoneId, int quantity);
    void addPhoneSingleToCart(String userName, int phoneId, int quantity);
//    void addCartItem(Cart cart, CartItem cartItem);
}
