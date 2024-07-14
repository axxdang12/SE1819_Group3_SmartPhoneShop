package swp391.SPS.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import swp391.SPS.dtos.PageDto;
import swp391.SPS.dtos.RequestSaveUserRoleDto;
import swp391.SPS.dtos.UserAddDto;
import swp391.SPS.entities.EmailDetails;
import swp391.SPS.entities.User;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.exceptions.NoDataInListException;
import swp391.SPS.exceptions.OutOfPageException;
import swp391.SPS.repositories.RoleRepository;
import swp391.SPS.services.RoleService;
import swp391.SPS.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = {"/admin-dashboard"}, method = RequestMethod.GET)
    public String adminDashBoard(Model model,
                                 @RequestParam("page") Optional<Integer> page) throws NoDataInListException, OutOfPageException, FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "redirect:/login";
        }
        int currentPage = page.orElse(1);
        PageDto pageDto = userService.getListUserFirstLoad(currentPage - 1, 5, "");
        List<Integer> pageNumbers = IntStream.rangeClosed(1, pageDto.getTotalPage())
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("loginRole", authentication.getPrincipal());
        model.addAttribute("listFirstLoad", pageDto.getResultList());
        model.addAttribute("listRole", roleService.findAll());
        return "admin-dashboard";
    }

    @RequestMapping("/admin-dashboard/add-account")
    public String addAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "redirect:/login";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "AddAccount";
    }

    @PostMapping("/admin-dashboard/add-account")
    public String add(@Valid @ModelAttribute(value = "userAddDto") UserAddDto userAddDto, Model model, BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> messageList = new ArrayList<>();
        List<FieldError> fieldErrors = result.getFieldErrors();

        try {

            if (result.hasErrors()) {
                for (FieldError fieldError : fieldErrors) {
                    messageList.add(fieldError.getDefaultMessage());
                }
                model.addAttribute("messageList", messageList);
                return "AddAccount";
            }
            if (userAddDto.getPassword().equals(userAddDto.getRepeatPassword())) {
                userAddDto.setPassword(passwordEncoder.encode(userAddDto.getPassword()));
                userService.save(userAddDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("userAddDto", userAddDto);
            } else {
                model.addAttribute("userAddDto", userAddDto);
                model.addAttribute("passwordError", "Your password not match! Check again!");
                System.out.println("password not same");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "AddAccount";
    }
}
