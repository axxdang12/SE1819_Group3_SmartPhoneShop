package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp391.SPS.entities.Order;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.services.OrderItemService;
import swp391.SPS.services.OrderService;
import swp391.SPS.services.PhoneService;
import swp391.SPS.services.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ManagerController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private PhoneService phoneService;

    @GetMapping("/manager")
    public String viewOrderList(Model model) throws FileNotFoundException {
        model.addAttribute("orderList", orderService.getAllOrder());
//        model.addAttribute("listBestSalePhone",phoneService.getbestsale());
//        model.addAttribute("BrandRevenue",phoneService.GetBrandRevenue());
        if(phoneService.GetTotalRevenue() == null)  model.addAttribute("revenue","Unknow");
        else model.addAttribute("revenue", phoneService.GetTotalRevenue() );
        return "manager";
    }

    @PostMapping("/searchorder")
    public String searchOrderById(@RequestParam("name") String name, Model model, RedirectAttributes redirectAttributes){
        List<Order> orders = orderService.searchOrderByUserName(name);
        if (orders.isEmpty()) {
            model.addAttribute("error", "No orders found for user: " + name);
        } else {
            model.addAttribute("listOrderByUser", orders);
        }
        return "manager";
    }
    @GetMapping("/order-detail-manager/{id}")
    public String detailOrderManager(@PathVariable("id") int id, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "order-detail";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("listItemByO", orderItemService.listOrderItemByOrderId(id));
        model.addAttribute("orderByOrderId",orderService.getOrder(id));
        model.addAttribute("userByOrderId", userService.findUserByOrderId(id));
            return "order-detail";
    }

    @GetMapping("/order-detail")
    public String orderDetail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "order-detail";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "order-detail";
    }
    @GetMapping("/approve/{id}")
    public String approveOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(id, "Approved");
            redirectAttributes.addFlashAttribute("message", "Order approved successfully");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/manager";
    }

    @GetMapping("/reject/{id}")
    public String rejectOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(id, "Rejected");
            redirectAttributes.addFlashAttribute("message", "Order rejected successfully");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/manager";
    }

    @GetMapping("/complete/{id}")
    public String completeOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, "Completed");
        redirectAttributes.addFlashAttribute("message", "Order completed successfully");
        return "redirect:/manager";
    }

    @GetMapping("/refund/{id}")
    public String refundOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, "Refunded");
        redirectAttributes.addFlashAttribute("message", "Order refunded successfully");
        return "redirect:/manager";
    }

    @GetMapping("/filterOrdersByDate")
    public String filterOrdersByDate(@RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate,
                                     Model model) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Order> orders = orderService.findOrdersBetweenDates(start, end);
        model.addAttribute("orderList", orders);
        return "manager";
    }
}
