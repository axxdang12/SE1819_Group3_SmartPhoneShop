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
    @Query(value = "SELECT CONCAT(ud.first_name, ' ', ud.last_name) AS username,  r.report_id, r.description, r.status, r.order_id, r.user_id FROM report r JOIN user u USING(user_id) JOIN userdtl ud USING(user_detail_id) WHERE CONCAT(ud.first_name, ' ', ud.last_name) LIKE %:name%", nativeQuery = true)
    List<Report> searchReportByUserName(@Param("name") String name);

    @Transactional
    @Query(value = "SELECT * FROM sps.report WHERE report.order_id LIKE %:oID%", nativeQuery = true)
    List<Report> searchReportByOrderId(@Param("oID") int orderId);
}
