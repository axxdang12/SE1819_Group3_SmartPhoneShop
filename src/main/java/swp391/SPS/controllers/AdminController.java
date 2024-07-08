package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp391.SPS.dtos.PageDto;
import swp391.SPS.dtos.RequestSaveUserRoleDto;
import swp391.SPS.exceptions.NoDataInListException;
import swp391.SPS.exceptions.OutOfPageException;
import swp391.SPS.repositories.RoleRepository;
import swp391.SPS.services.RoleService;
import swp391.SPS.services.UserService;

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

    @RequestMapping(value ={"/admin-dashboard"}, method = RequestMethod.GET)
    public String adminDashBoard(Model model,
                                 @RequestParam("page") Optional<Integer> page) throws NoDataInListException, OutOfPageException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "redirect:/login";
        }
        int currentPage = page.orElse(1);
        PageDto pageDto = userService.getListUserFirstLoad(currentPage -1, 2, "");
        List<Integer> pageNumbers = IntStream.rangeClosed(1, pageDto.getTotalPage())
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("listFirstLoad", pageDto.getResultList());
        model.addAttribute("listRole", roleService.findAll());
        return "admin-dashboard";
    }
}
