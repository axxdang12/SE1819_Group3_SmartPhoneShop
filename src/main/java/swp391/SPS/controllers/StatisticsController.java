package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.services.PhoneService;

@Controller
public class StatisticsController {
    @Autowired
    PhoneService phoneService;

//    @GetMapping("/statistics")
//    public String LoadStatistic(Model model) throws FileNotFoundException {
//        model.addAttribute("listBestSalePhone",phoneService.getbestsale());
//        if(phoneService.GetTotalRevenue() == null)  model.addAttribute("revenue","Unknow");
//        else model.addAttribute("revenue", phoneService.GetTotalRevenue() );
//        return"Statistics";
//    }

}
