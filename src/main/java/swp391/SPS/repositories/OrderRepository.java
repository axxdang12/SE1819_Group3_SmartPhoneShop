package swp391.SPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.entities.Order;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM ordertb WHERE ordertb.user_id = :userId" , nativeQuery = true)
    List<Order> getOrderByUserId(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "select o.order_id, o.order_date, o.user_id, o.total_price, o.status from ordertb o join user u using (user_id) where u.user_name like %:name%", nativeQuery = true)
    List<Order> searchOrderByUserName(@Param("name") String name);


}
