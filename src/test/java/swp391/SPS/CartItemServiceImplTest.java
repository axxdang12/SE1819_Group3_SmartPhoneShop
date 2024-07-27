package swp391.SPS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;
import swp391.SPS.entities.Phone;
import swp391.SPS.entities.User;
import swp391.SPS.repositories.CartItemRepository;
import swp391.SPS.repositories.CartRepository;
import swp391.SPS.repositories.PhoneRepository;
import swp391.SPS.repositories.UserRepository;
import swp391.SPS.services.impls.CartItemServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceImplTest {

    @InjectMocks
    private CartItemServiceImpl cartItemServiceImpl;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private UserRepository userRepository;

    private User mockUser;
    private Cart mockCart;
    private List<CartItem> mockCartItems;

    @BeforeEach
    public void setup() {
        mockUser = mock(User.class);
        mockCart = mock(Cart.class);
        mockCartItems = new ArrayList<>();
        lenient().when(mockUser.getCart()).thenReturn(mockCart);
        lenient().when(mockCart.getItems()).thenReturn(mockCartItems);
    }

    @Test
    public void testRemovePhoneFromCart() {
        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);
        doNothing().when(cartItemRepository).deletePhoneFromCart(1, 1);
        cartItemServiceImpl.removePhoneFromCart("testUser", 1, 1);
        verify(cartItemRepository, times(1)).deletePhoneFromCart(1, 1);
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testAddPhoneToCart() {
        Phone mockPhone = mock(Phone.class);
        when(mockPhone.getStatus()).thenReturn(true);
        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);
        when(phoneRepository.findById(1)).thenReturn(Optional.of(mockPhone));
        cartItemServiceImpl.addPhoneToCart("testUser", 1);
        assertEquals(1, mockCartItems.size());
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testUpdatePhoneQuantity() {
        CartItem mockCartItem = mock(CartItem.class);
        Phone mockPhone = mock(Phone.class);
        when(mockPhone.getStatus()).thenReturn(true);
        when(mockCartItem.getPhone()).thenReturn(mockPhone);
        when(cartItemRepository.listCartItemByPAC(1, 1)).thenReturn(mockCartItem);
        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);
        cartItemServiceImpl.updatePhoneQuantity("testUser", 1, 1, 5);
        verify(mockCartItem, times(1)).setQuantity(5);
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testAddPhoneSingleToCart() {
        Phone mockPhone = mock(Phone.class);
        when(mockPhone.getPrice()).thenReturn(100.0);
        when(phoneRepository.findById(1)).thenReturn(Optional.of(mockPhone));
        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);

        cartItemServiceImpl.addPhoneSingleToCart("testUser", 1, 2);

        assertEquals(1, mockCartItems.size());
        verify(cartRepository, times(1)).save(mockCart);
    }
}
