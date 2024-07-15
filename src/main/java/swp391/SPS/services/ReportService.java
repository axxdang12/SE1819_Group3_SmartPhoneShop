package swp391.SPS.services;
//import swp391.SPS.entities.Accessory;
import swp391.SPS.entities.Cart;
import swp391.SPS.entities.Report;
import swp391.SPS.entities.User;

import java.util.List;
import swp391.SPS.dtos.ReportDto;
import swp391.SPS.exceptions.FileNotFoundException;

public interface ReportService {
    Report submitR(int orderId, String description, User user) throws FileNotFoundException;
    Report getReportByOrderId(int orderId) throws FileNotFoundException;
    void deleteReport(int report);
    Report getReport(int reportId);
    List<ReportDto> getAllReport();
    ReportDto convertToDTO(Report report);
    List<ReportDto> searchReportByUserName(String name);
    List<ReportDto> searchReportByOrderId(int orderId);
}
