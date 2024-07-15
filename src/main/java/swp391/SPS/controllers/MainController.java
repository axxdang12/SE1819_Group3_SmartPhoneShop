package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp391.SPS.dtos.PageDto;
import swp391.SPS.entities.Phone;
import swp391.SPS.exceptions.NoDataInListException;
import swp391.SPS.exceptions.OutOfPageException;
import swp391.SPS.services.PhoneService;
import swp391.SPS.services.RoleService;
import swp391.SPS.services.UserDetailService;
import swp391.SPS.services.UserService;

@Controller
@CrossOrigin
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PhoneService phoneService;

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
            model.addAttribute("listPhone", phoneService.getbestsale());
            model.addAttribute("isLogin", false);
            model.addAttribute("listPhone", phoneService.getbestsale());
            return "index";
        }
        String role = userService.findByUsername(authentication.getName()).getRoles().get(0).getRoleName();
        if (role.equalsIgnoreCase("ADMIN")) {
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            return "redirect:/admin-dashboard";
        } else if (role == "MANAGER") {
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            return "redirect:/manager-dashboard";
        } else {
            model.addAttribute("listPhone", phoneService.getbestsale());
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            return "index";
        }
    }



    @RequestMapping(value = "/manager-dashboard", method = RequestMethod.GET)
    public String managerDashBoard() {
        return "manager-dashboard";
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
