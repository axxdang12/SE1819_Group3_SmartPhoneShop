package swp391.SPS.services;

//import swp391.SPS.entities.Accessory;
import org.aspectj.weaver.ast.Or;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.Phone;
import swp391.SPS.exceptions.FileNotFoundException;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> ListOrderByUserId(int id);
    Order getOrder(int oId) throws FileNotFoundException;
    void placeOrder(String userName);
    void cancelOrder(int orderId);
    List<Order> searchOrderByUserName(String name);
    void updateOrderStatus(int id, String status);
}
