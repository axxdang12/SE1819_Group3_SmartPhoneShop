package swp391.SPS.controllers;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import swp391.SPS.entities.Phone;

@Controller
@CrossOrigin
public class MainController {
    @GetMapping("/page/login")
    @CrossOrigin
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    @CrossOrigin
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "index";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "index";
    }

    @RequestMapping(value = "/admin-dashboard", method = RequestMethod.GET)
    public String adminDashBoard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "redirect:/login";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "admin-dashboard";
    }

    @RequestMapping(value = "/manager-dashboard", method = RequestMethod.GET)
    public String managerDashBoard() {
        return "manager-dashboard";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "login";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());

        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "checkout";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "checkout";
    }

    @GetMapping("/single-product")
    public String singleProduct() {
        return "/";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/about")
    public String about(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "about";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "about";
    }

    @GetMapping("/user_detail")
    public String user_detail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "user_detail";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "user_detail";
    }

    @GetMapping("/userorder")
    public String userOrder(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "userorder";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "userorder";
    }

    @GetMapping("/detail")
    public String detail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "detail";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "detail";
    }
}
