package swp391.SPS.services;
//import swp391.SPS.entities.Accessory;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.Report;
import swp391.SPS.entities.User;
import java.util.List;
import swp391.SPS.dtos.ReportDto;

public interface ReportService {
    Report submitR(int orderId, String description, User user);
    Report getReportByOrderId(int orderId);
    void deleteReport(int report);
    Report getReport(int reportId);
    List<ReportDto> getAllReport();
    ReportDto convertToDTO(Report report);
    List<ReportDto> searchReportByUserId(int userId);
    List<ReportDto> searchReportByOrderId(int orderId);
}
