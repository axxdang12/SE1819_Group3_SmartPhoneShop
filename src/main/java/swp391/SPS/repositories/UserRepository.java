package swp391.SPS.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.dtos.StatisticsUserOrder;
import swp391.SPS.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String userName);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);

    @Query("SELECT u FROM User u WHERE u.username LIKE %?1% OR (u.email LIKE %?1%) OR (u.userDetail.firstName LIKE %?1%) OR (u.userDetail.lastName LIKE %?1%) OR (u.userDetail.phoneNumber LIKE %?1%)")
    Page<User> findAllUser(String search, Pageable pageable);

//  @Modifying
  @Transactional
  @Query(value = "SELECT u.* FROM user u JOIN ordertb o USING (user_id) WHERE o.order_id = :orderId" , nativeQuery = true)
  User getUserByOrderId(@Param("orderId") int orderId);

    @Query("select new swp391.SPS.dtos.StatisticsUserOrder( u.username as userName, COUNT(o.orderId) as totalOrder) FROM Order o JOIN User u ON o.user.userId = u.userId   where o.status ='completed'  GROUP BY  u.userId ,u.username ORDER BY COUNT(o.orderId) DESC ")
    List<StatisticsUserOrder> ListTotalOrderOfUser();

    @Query("select new swp391.SPS.dtos.StatisticsUserOrder( u.username as userName, COUNT(o.orderId) as totalOrder) FROM Order o JOIN User u ON o.user.userId = u.userId where o.status ='completed' and o.orderDate >= :start and o.orderDate <= :end GROUP BY  u.userId ,u.username ORDER BY COUNT(o.orderId) DESC ")
    List<StatisticsUserOrder> ListTotalOrderOfUserByDate(@Param("start") LocalDate  start,@Param("end") LocalDate end);

}
//@Query("SELECT new co.vn.vse.common.dtos.AccountListDto(a.accountId, a.avatar, a.fullName, a.birthDate, a.phoneNumber, a.address, a.email) FROM Account a WHERE a.role.roleName = 'TEACHER'")
//Page<AccountListDto> findAllTeacher(Pageable pageable);
//List<StatisticsUserOrder> ListRevenueOfBrandBuDate(@Param("start") LocalDate start, @Param("end") LocalDate end);


