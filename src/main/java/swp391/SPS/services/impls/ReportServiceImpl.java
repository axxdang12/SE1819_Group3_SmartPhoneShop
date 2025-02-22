package swp391.SPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.SPS.dtos.ReportDto;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.Report;
import swp391.SPS.entities.User;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.repositories.*;
import swp391.SPS.services.OrderService;
import swp391.SPS.services.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private OrderService orderService;

    @Override
    public Report submitR(int orderId, String description, User user) throws FileNotFoundException {
        Order order=orderService.getOrder(orderId);
        Report report=new Report();
        report.setOrder(order);
        report.setUser(user);
        report.setDescription(description);
        report.setStatus("Pending");
        return reportRepository.save(report);
    }

    @Override
    public Report getReportByOrderId(int orderId) throws FileNotFoundException {
        return reportRepository.getReportFromOrder(orderId);
    }

    @Override
    public void deleteReport(int report) {
        reportRepository.deleteR(report);
    }

    @Override
    public Report getReport(int reportId) {
        return reportRepository.getReferenceById(reportId);
    }

    @Override
    public List<ReportDto> getAllReport() {
        return reportRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto convertToDTO(Report report) {
        ReportDto dto = new ReportDto();
        dto.setReportId(report.getReportId());
        dto.setUserId(report.getUser().getUserId());
        dto.setOrderId(report.getOrder().getOrderId());
        dto.setDescription(report.getDescription());
        dto.setStatus(report.getStatus());
        dto.setUserName(report.getUser().getUsername());
        dto.setOrderDate(report.getOrder().getOrderDate());
        dto.setEmail(report.getUser().getEmail());
        return dto;
    }

    @Override
    public List<ReportDto> searchReportByUserName(String name) {
        List<Report> reports = reportRepository.searchReportByUserName(name);
        return reports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReportDto> findReportsBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Report> reports = reportRepository.findReportsBetweenDates(startDate, endDate);
        return reports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
