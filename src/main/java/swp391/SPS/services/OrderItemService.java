package swp391.SPS.services;

//import swp391.SPS.entities.Accessory;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> listOrderItemByOrderId(int oId);
}
