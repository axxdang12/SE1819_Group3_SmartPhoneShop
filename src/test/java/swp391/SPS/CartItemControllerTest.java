package swp391.SPS;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import swp391.SPS.controllers.CartItemController;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;
import swp391.SPS.services.CartItemService;
import swp391.SPS.services.CartService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartItemControllerTest {

	@InjectMocks
	private CartItemController cartItemController;

	@Mock
	private CartItemService cartItemService;

	@Mock
	private CartService cartService;

	@Mock
	private Model model;

	@Test
	public void testAddPhoneToCart() {
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("testUser");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Cart mockCart = mock(Cart.class);
		List<CartItem> mockCartItems = new ArrayList<>();
		when(cartService.getCart("testUser")).thenReturn(mockCart);
		when(mockCart.getItems()).thenReturn(mockCartItems);

		doAnswer(invocation -> {
			mockCartItems.add(new CartItem());
			return null;
		}).when(cartItemService).addPhoneToCart("testUser", 1);

		cartItemController.addPhoneToCart(1, model);

		// Verify that the appropriate methods were called
		verify(cartService, times(1)).getCart("testUser");
		verify(cartItemService, times(1)).addPhoneToCart("testUser", 1);

		// Assert the CartItem list has one item
		assertEquals(1, mockCartItems.size());
	}

	@Test
	public void testDeletePhoneWhenNotLoggedIn() {
		// Setup mock Authentication
		SecurityContextHolder.getContext().setAuthentication(null);

		// Call the method
		String result = cartItemController.deletePhone(1, model, null);

		// Verify that model attributes are set correctly
		verify(model).addAttribute("isLogin", false);
		assertEquals("cart", result);
	}

	@Test
	public void testAddPhoneToCartWhenNotLoggedIn() {
		// Setup mock Authentication
		SecurityContextHolder.getContext().setAuthentication(null);

		// Call the method
		String result = cartItemController.addPhoneToCart(1, model);

		// Verify that model attributes are set correctly
		verify(model).addAttribute("isLogin", false);
		assertEquals("cart", result);
	}

	@Test
	public void testUpdateQuantityWhenNotLoggedIn() {
		// Setup mock Authentication
		SecurityContextHolder.getContext().setAuthentication(null);

		// Call the method
		String result = cartItemController.updateQuantity(1, 5, model);

		// Verify that model attributes are set correctly
		verify(model).addAttribute("isLogin", false);
		assertEquals("cart", result);
	}
}
