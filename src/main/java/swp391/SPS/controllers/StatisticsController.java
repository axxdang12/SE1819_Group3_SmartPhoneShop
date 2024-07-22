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
import swp391.SPS.services.PhoneService;

import java.util.Date;

@Controller
public class StatisticsController {
    @Autowired
    PhoneService phoneService;

    @GetMapping("/statistics")
    public String LoadStatistic(Model model) throws FileNotFoundException {
        model.addAttribute("listBestSalePhone",phoneService.getbestsale());
        if(phoneService.GetTotalRevenue() == null)  model.addAttribute("revenue","0");
        else model.addAttribute("revenue", phoneService.GetTotalRevenue() );
        model.addAttribute("listBrand",phoneService.GetBrandRevenue());
        return"Statistics";
    }
    @PostMapping("/staticsDate")
    public String SearchStaByDate(@RequestParam("start_date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                  @RequestParam("end_date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                  Model model){

        if(phoneService.GetRevenueByDate(start,end) == null)  model.addAttribute("revenue","0");
        else model.addAttribute("revenue", phoneService.GetRevenueByDate(start,end));
        return "Statistics";
    }

}
