package swp391.SPS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.OrderItem;
import swp391.SPS.entities.User;

import java.util.List;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

    @Modifying
    @Transactional
    @Query(value = "select * from order_item where order_id= :order_id" , nativeQuery = true)
    List<OrderItem> getOrderItemByOrderId(@Param("order_id") int order_id);
}
