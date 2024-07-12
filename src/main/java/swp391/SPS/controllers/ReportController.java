package swp391.SPS.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp391.SPS.entities.Report;
import swp391.SPS.entities.User;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.repositories.UserRepository;
import swp391.SPS.services.OrderItemService;
import swp391.SPS.services.OrderService;
import swp391.SPS.services.ReportService;
import swp391.SPS.services.UserService;



@Controller
public class ReportController {

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReportService reportService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/report/{id}")
    public String showReportForm(@PathVariable("id") int orderId, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "report";
        }
        Report existingReport = reportService.getReportByOrderId(orderId);
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("listItemByO", orderItemService.listOrderItemByOrderId(orderId));
        model.addAttribute("order", orderId);
        model.addAttribute("existingReport", existingReport);
        model.addAttribute("reportNormal", existingReport != null);
        return "report";
    }

    @PostMapping("/submit-report")
    public String submitReport(@RequestParam("orderId") int orderId,
                               @RequestParam("description") String description, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "report";
        }
        Report existingReport = reportService.getReportByOrderId(orderId);
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        reportService.submitR(orderId, description, user);
        model.addAttribute("listItemByO", orderItemService.listOrderItemByOrderId(orderId));
        model.addAttribute("existingReport", existingReport);
        model.addAttribute("reportNormal", existingReport != null);
        return "redirect:/respond";
    }

    @PostMapping("/delete-report")
    public String deleteReport(@RequestParam("reportId") int reportId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "report";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
//        Report report=reportService.getReport(reportId);
        reportService.deleteReport(reportId);
        model.addAttribute("orderListByUid",orderService.ListOrderByUserId(userService.getUserId(authentication.getName())));
        return "redirect:/userorder";
    }

    @GetMapping("/respond")
    public String respond(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "respond";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "respond";
    }
}
