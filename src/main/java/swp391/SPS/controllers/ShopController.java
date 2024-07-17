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
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.services.BrandService;
//import swp391.SPS.services.CategoryService;
import swp391.SPS.services.PhoneService;

import java.util.List;
import java.util.Objects;

@Controller
public class ShopController {

    @Autowired
    BrandService brandService;
    @Autowired
    PhoneService phoneService;

    @GetMapping("/shop")
    public String shop(Model model,@RequestParam(name = "keyword", required = false) String name,
                                    @RequestParam(name = "pageNo", defaultValue = "1") String pageNo,
                                    @RequestParam (name = "minPrice", required = false) String minPrice,
                                    @RequestParam (name="maxPrice", required = false) String maxPrice) throws FileNotFoundException {
        if(pageNo.equals("") || pageNo.isEmpty() || pageNo == null){
            model.addAttribute("check", true);
            return "shop";
        }
        model.addAttribute("listBrand", brandService.findAllBrand());

            int page = Integer.parseInt(pageNo);
            if(page <=0 ){
                model.addAttribute("check", true);
                return "shop";
            }
            Page<Phone> list = phoneService.viewphoneforshop(page);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);

            }else{
                model.addAttribute("isLogin", true);

                model.addAttribute("username", authentication.getName());
            }
            if(name !=null && !name.isEmpty()){
                list = phoneService.searchPhoneforShop(name,page);
                model.addAttribute("keyword", name);
                int TotalPage = list.getTotalPages();

                if(page > TotalPage || list.getContent().isEmpty() || list.getContent()==null){
                    model.addAttribute("check", true);
                }else{
                    model.addAttribute("check", false);
                }
            }
            else if (minPrice != null && maxPrice != null ) {
                if(minPrice.isEmpty() || maxPrice.isEmpty()){
                    model.addAttribute("check", true);
                    return "shop";
                }

                Double max = Double.parseDouble(maxPrice);
                Double min = Double.parseDouble(minPrice);
                list = phoneService.searchByPrice(min,max,page);
                int TotalPage = list.getTotalPages();

                if(page > TotalPage || list.getContent().isEmpty() || list.getContent()==null){
                    model.addAttribute("check", true);
                }else{
                    model.addAttribute("check", false);
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
        int TotalPage = list.getTotalPages();

        if(page > TotalPage || list.getContent().isEmpty() || list.getContent()==null){
            model.addAttribute("check", true);
        }else{
            model.addAttribute("check", false);
        }
            model.addAttribute("listPhone", list);
            model.addAttribute("totalPage", list.getTotalPages());
            model.addAttribute("currentPage", page);


        return "shop";

        }
@GetMapping("/shop?minPrice=&maxPrice=")
public String exceptionPrice() throws FileNotFoundException {
        throw  new FileNotFoundException("Not Found");
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


    @GetMapping("/shop/brand")
    public String ProductByBrand(@RequestParam("id") String idBrand, Model model,@RequestParam(name = "pageNo", defaultValue = "1") int page) throws FileNotFoundException {
        if(idBrand.isEmpty() || idBrand.equals("")){
            throw new FileNotFoundException("Not Found");
        }
        int id = Integer.parseInt(idBrand);
        model.addAttribute("listBrand", brandService.findAllBrand());
        int TotalPage = 1;
        Page<Phone> list = phoneService.getPhoneBrandByPahination(id,page);

        if(list!=null) TotalPage = list.getTotalPages();

        if(page > TotalPage || list == null){
            model.addAttribute("check", true);
        }else{
            model.addAttribute("check", false);
        }
        model.addAttribute("listPhone", list);

        model.addAttribute("totalPage", TotalPage);
        model.addAttribute("currentPage", page);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);

        }else{
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
        }


        return "shop";
    }

//    }


}
