package swp391.SPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.entities.Brand;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("select new swp391.SPS.dtos.BrandRevenueDTO( b.brandName as brandName, sum(oi.total) as total ) from Brand b join Phone p on b.brandId = p.brand.brandId join OrderItem oi on oi.phone.phoneId = p.phoneId join Order o on o.orderId = oi.order.orderId where o.status = 'Completed'  group by b.brandId, b.brandName")
    List<BrandRevenueDTO> ListRevenueOfBrand();

    @Query("select new swp391.SPS.dtos.BrandRevenueDTO( b.brandName as brandName, sum(oi.total) as total ) from Brand b join Phone p on b.brandId = p.brand.brandId join OrderItem oi on oi.phone.phoneId = p.phoneId join Order o on o.orderId = oi.order.orderId where o.status = 'Completed' and o.orderDate >= :start and o.orderDate <= :end group by b.brandId, b.brandName")
    List<BrandRevenueDTO> ListRevenueOfBrandBuDate(@Param("start") LocalDate  start,@Param("end") LocalDate end);

//    @Query("SELECT new co.vn.vse.common.dtos.AccountListDto(a.accountId, a.avatar, a.fullName, a.birthDate, a.phoneNumber, a.address, a.email) FROM Account a WHERE a.role.roleName = 'TEACHER'")
//    Page<AccountListDto> findAllTeacher(Pageable pageable);


//    @Query(value = "select new swp391.SPS.dtos.BrandRevenueDTO( brand.brand_name as brandName, sum(order_item.total) as total ) from brand join phone on brand.brand_id = phone.brand_id join order_item on order_item.phone_id = phone.phone_id join ordertb on ordertb.order_id = order_item.order_id where ordertb.status = 'Completed'  group by brand.brand_id, brand.brand_name\n", nativeQuery = true)
//    List<BrandRevenueDTO> l();
}