package swp391.SPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;
import swp391.SPS.entities.Phone;
import swp391.SPS.entities.User;
import swp391.SPS.repositories.CartItemRepository;
import swp391.SPS.repositories.CartRepository;
import swp391.SPS.repositories.PhoneRepository;
import swp391.SPS.repositories.UserRepository;
import swp391.SPS.services.CartItemService;

import java.util.List;


@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void removePhoneFromCart(String userName,int cartId, int phoneId) {
        int quantity=0;
        double total=0;
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        cartItemRepository.deletePhoneFromCart(cartId, phoneId);
        cartRepository.save(cart);
    }

    @Override
    public void addPhoneToCart(String userName, int phoneId) {
        int quantity=0;
        double total = 0;
        Phone upd = phoneRepository.findById(phoneId).orElse(null);
        if (upd == null||!upd.getStatus()) {
            return;
        }
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        boolean found = false;
        for (CartItem item : ciList) {
            if (item.getPhone().getPhoneId() == upd.getPhoneId()) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            ciList.add(CartItem.builder().phone(upd).cart(cart).quantity(1).build());
        }
        cartRepository.save(cart);
    }

    @Override
    public void updatePhoneQuantity(String userName,int cartId, int phoneId, int quantity) {
        int quantityCart=0;
        double total=0;
        CartItem cartItem=cartItemRepository.listCartItemByPAC(cartId,phoneId);
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        if (cartItem!=null&&cartItem.getPhone().getStatus()){
            cartItem.setQuantity(quantity);
            cartItem.setTotal(cartItem.getTotalPrice());
            cartRepository.save(cart);
        }
    }

    @Override
    public void addPhoneSingleToCart(String userName, int phoneId, int quantity) {
            Phone phone = phoneRepository.findById(phoneId).orElse(null);
            if (phone == null) {
                return; // Handle case where phone is not found
            }

            User user = userRepository.findByUsername(userName);
            Cart cart = user.getCart();
            List<CartItem> cartItems = cart.getItems();

            // Check if the phone already exists in the cart
            boolean phoneExistsInCart = false;
            for (CartItem item : cartItems) {
                if (item.getPhone().getPhoneId() == phone.getPhoneId()) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartItemRepository.save(item); // Update existing cart item
                    phoneExistsInCart = true;
                    break;
                }
            }

            // If the phone is not already in the cart, add it as a new cart item
            if (!phoneExistsInCart) {
                CartItem newItem = CartItem.builder()
                        .phone(phone)
                        .cart(cart)
                        .quantity(quantity)
                        .total(quantity*phone.getPrice())
                        .build();
                cartItems.add(newItem);
                cartItemRepository.save(newItem);
            }
            int totalQuantity = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
            double totalPrice = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getPhone().getPrice()).sum();
            cart.setQuantity(totalQuantity);
            cart.setTotal(totalPrice);
            cartRepository.save(cart); // Save updated cart
        }
}
