package swp391.SPS.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.OrderItem;
import swp391.SPS.services.OrderItemService;
import swp391.SPS.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class APIstatistics {

    @Autowired
    private  OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrder();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
