package swp391.SPS.controllers;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp391.SPS.dtos.ReportDto;
import swp391.SPS.entities.Report;
import swp391.SPS.services.OrderItemService;
import swp391.SPS.services.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/manageReport")
    public String viewReportList(Model model) {
        List<ReportDto> reportList = reportService.getAllReport();
        model.addAttribute("reportList", reportList);
        return "manageReport";
    }

    @GetMapping("/report/report-detail/{reportId}")
    public String viewReportDetails(@PathVariable("reportId") int reportId, Model model) {
        Report report = reportService.getReport(reportId);
        model.addAttribute("report", report);
        model.addAttribute("listItemByO", orderItemService.listOrderItemByOrderId(report.getOrder().getOrderId()));
        return "report-detail";
    }

    @PostMapping("/searchReport")
    public String searchReport(@RequestParam(value = "name", required = false) String name,
                               Model model) {
        List<ReportDto> reportList = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            reportList = reportService.searchReportByUserName(name);
            if (reportList.isEmpty()) {
                model.addAttribute("error", "No reports found for user: " + name);
            }
        }
        // Ensure reportList is empty if no search parameters are provided or no results found
        if (name == null || name.isEmpty()) {
            reportList = reportService.getAllReport(); // Only fetch all reports if no search parameters are provided
        }

        model.addAttribute("reportList", reportList);
        return "manageReport";
    }


//    @GetMapping("/report/report-detail/{reportId}")
//    public String viewReportDetails(@PathVariable("reportId") int reportId, Model model) {
//        Report report = reportService.getReport(reportId);
//        model.addAttribute("report", report);
//        return "report-detail";
//    }

    @PostMapping("/searchReportsByDate")
    public String searchReportsByDate(@RequestParam("startDate") String startDate,
                                      @RequestParam("endDate") String endDate, Model model) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<ReportDto> reportList = reportService.findReportsBetweenDates(start, end);
        if (reportList.isEmpty()) {
            model.addAttribute("error", "No reports found between the specified dates.");
        }
        model.addAttribute("reportList", reportList);
        return "manageReport";
    }
}
