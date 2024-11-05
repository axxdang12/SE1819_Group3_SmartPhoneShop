package swp391.SPS.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId AND phone_id = :phoneId", nativeQuery = true)
    void deletePhoneFromCart(@Param("cartId") int cartId, @Param("phoneId") int phoneId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.phone.phoneId = ?2")
    CartItem listCartItemByPAC(int cartId, int phoneId);

    @Query(value =  "select sum(total) from cart_item where cart_id = :cartId", nativeQuery = true)
    Double totalCart(@Param("cartId") int cartId);

//    @Query(value =  "select sum(quantity * total) from cart_item where phone_id = :phoneId and cart_id = :cartId", nativeQuery = true)
//    Double totalItem(@Param("phoneId") int phoneId, @Param("cartId") int cartId);
}
