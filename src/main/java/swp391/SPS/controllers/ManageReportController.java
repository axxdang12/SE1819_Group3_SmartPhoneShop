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
import java.util.List;

@Controller
public class ManageReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/manageReport")
    public String viewReportList(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "orderId", required = false) Integer orderId,
                                 Model model, RedirectAttributes redirectAttributes) {
        List<ReportDto> reportList;
        if (name != null && !name.isEmpty()) {
            reportList = reportService.searchReportByUserName(name);
            if (reportList.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No reports found for user: " + name);
            }
        } else if (orderId != null) {
            reportList = reportService.searchReportByOrderId(orderId);
            if (reportList.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No reports found for order ID: " + orderId);
            }
        } else {
            reportList = reportService.getAllReport();
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
