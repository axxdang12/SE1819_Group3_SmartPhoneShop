package swp391.SPS.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import swp391.SPS.services.AccessService;
import swp391.SPS.entities.Phone;
import swp391.SPS.services.BrandService;
//import swp391.SPS.services.CategoryService;
import swp391.SPS.services.PhoneService;

import java.util.List;

@Controller
public class ShopController {

    @Autowired
    BrandService brandService;
    @Autowired
    PhoneService phoneService;

    @GetMapping("/shop")
    public String shop(Model model,@RequestParam(name = "keyword", required = false) String name,
                                    @RequestParam(name = "pageNo", defaultValue = "1") int page,
                                    @RequestParam (name = "minPrice", required = false) String minPrice,
                                    @RequestParam (name="maxPrice", required = false) String maxPrice) {
        model.addAttribute("listBrand", brandService.findAllBrand());
            Page<Phone> list = phoneService.viewphoneforshop(page);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);

            }
            if(name !=null && !name.isEmpty()){
                list = phoneService.searchPhoneforShop(name,page);
                model.addAttribute("keyword", name);
            }
            else if (minPrice != null && maxPrice != null ) {
                Double max = Double.parseDouble(maxPrice);
                Double min = Double.parseDouble(minPrice);
                list = phoneService.searchByPrice(min,max,page);
                int TotalPage = list.getTotalPages();
                if(list.isEmpty()) {
                    TotalPage =1;
                }
                model.addAttribute("listPhone", list);
                model.addAttribute("totalPage", TotalPage);
                model.addAttribute("currentPage", page);
                model.addAttribute("isLogin", true);
                model.addAttribute("username", authentication.getName());
                model.addAttribute("minPrice", min);
                model.addAttribute("maxPrice", max);
                return "shop";
            }
            model.addAttribute("listPhone", list);
            model.addAttribute("totalPage", list.getTotalPages());
            model.addAttribute("currentPage", page);
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());

        return "shop";

        }

//@GetMapping("/shop/price")
//public String searchPrice( @RequestParam (name = "minPrice") double minPrice,
//                           @RequestParam (name="maxPrice") double maxPrice,
//                           @RequestParam(name = "pageNo",defaultValue = "1") int pageno,
//                           Model model){
//    model.addAttribute("listBrand", brandService.findAllBrand());
//    Page<Phone> list = phoneService.searchByPrice(minPrice,maxPrice,pageno);
//    model.addAttribute("listPhone", list);
//    model.addAttribute("totalPage", list.getTotalPages());
//    model.addAttribute("currentPage", pageno);
//    model.addAttribute("minPrice",minPrice);
//    model.addAttribute("maxPrice",maxPrice);
//
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//        model.addAttribute("isLogin", false);
//        return "shop";
//    }
//    model.addAttribute("isLogin", true);
//    model.addAttribute("username", authentication.getName());
//    return "shop";
//
//}

    @GetMapping("/shop/brand/{idBrand}")
    public String ProductByBrand(@PathVariable("idBrand") int id, Model model,@RequestParam(name = "pageNo", defaultValue = "1") int page){
        model.addAttribute("listBrand", brandService.findAllBrand());
        Page<Phone> list = phoneService.getPhoneBrandByPahination(id,page);
        model.addAttribute("listPhone", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "shop";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "shop";
    }

//    }


}
