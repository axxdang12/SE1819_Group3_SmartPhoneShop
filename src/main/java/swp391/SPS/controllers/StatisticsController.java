package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.services.BrandService;
import swp391.SPS.services.PhoneService;
import swp391.SPS.services.UserService;

import java.util.Date;

@Controller
public class StatisticsController {
    @Autowired
    PhoneService phoneService;

    @Autowired
    BrandService brandService;

    @Autowired
    UserService userService;

    @GetMapping("/statistics")
    public String LoadStatistic(Model model) throws FileNotFoundException {
        model.addAttribute("listBestSalePhone",phoneService.getbestsale());
        if(phoneService.GetTotalRevenue() == null)  model.addAttribute("revenue","0");
        else model.addAttribute("revenue", phoneService.GetTotalRevenue() );
        model.addAttribute("listBrand",brandService.GetBrandRevenue());
        model.addAttribute("listUser", userService.TotalOderOfUser());
        model.addAttribute("listPhone",phoneService.BestSalePhone());
        return"Statistics";
    }
    @PostMapping("/staticsDate")
    public String SearchStaByDate(@RequestParam("start_date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                  @RequestParam("end_date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                  Model model){

        if(phoneService.GetRevenueByDate(start,end) == null)  model.addAttribute("revenue","0");
        else model.addAttribute("revenue", phoneService.GetRevenueByDate(start,end));
        if(brandService.GetBrandRevenueByDate(start, end) != null) model.addAttribute("listBrand",brandService.GetBrandRevenueByDate(start, end));
        model.addAttribute("listUser",userService.TotalOrderOfUserByDate(start, end));
        model.addAttribute("listPhone",phoneService.BestSalePhoneByDate(start,end));
        return "Statistics";
    }

}
