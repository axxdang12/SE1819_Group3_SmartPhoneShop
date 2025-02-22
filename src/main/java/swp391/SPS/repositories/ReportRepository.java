package swp391.SPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.entities.Brand;
import swp391.SPS.entities.Report;
import swp391.SPS.entities.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

//    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM report WHERE order_id= :orderId", nativeQuery = true)
    Report getReportFromOrder(@Param("orderId") int cartId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM report WHERE report_id = :reportId", nativeQuery = true)
    void deleteR(@Param("reportId") int reportId);

    @Transactional
    @Query(value = "select r.report_id, r.order_id, r.user_id, r.description, r.status from report r join user u using (user_id) where u.user_name like %:name%", nativeQuery = true)
    List<Report> searchReportByUserName(@Param("name") String name);

    @Transactional
    @Query(value = "SELECT r.report_id, r.description, r.status, u.user_id, u.user_name, o.order_id, o.order_date " +
            "FROM report r " +
            "JOIN user u ON r.user_id = u.user_id " +
            "JOIN ordertb o ON r.order_id = o.order_id " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Report> findReportsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
