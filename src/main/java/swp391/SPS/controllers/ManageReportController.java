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
import swp391.SPS.services.ReportService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/manageReport")
    public String viewReportList(Model model) {
        List<ReportDto> reportList = reportService.getAllReport();
        model.addAttribute("reportList", reportList);
        return "manageReport";
    }

    @PostMapping("/searchReport")
    public String searchReport(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "orderId", required = false) Integer orderId,
                               Model model) {
        List<ReportDto> reportList = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            reportList = reportService.searchReportByUserName(name);
            if (reportList.isEmpty()) {
                model.addAttribute("error", "No reports found for user: " + name);
            }
        }

        if (orderId != null) {
            reportList = reportService.searchReportByOrderId(orderId);
            if (reportList.isEmpty()) {
                model.addAttribute("error", "No reports found for order ID: " + orderId);
            }
        }

        // Ensure reportList is empty if no search parameters are provided or no results found
        if ((name == null || name.isEmpty()) && orderId == null) {
            reportList = reportService.getAllReport(); // Only fetch all reports if no search parameters are provided
        }

        model.addAttribute("reportList", reportList);
        return "manageReport";
    }


    @GetMapping("/report/report-detail/{reportId}")
    public String viewReportDetails(@PathVariable("reportId") int reportId, Model model) {
        Report report = reportService.getReport(reportId);
        model.addAttribute("report", report);
        return "report-detail";
    }
}
