package swp391.SPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.entities.Brand;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "select brand.brand_name as brandName, sum(order_item.total) as total \n" +
            "            from brand join phone on brand.brand_id = phone.brand_id \n" +
            "            join order_item on order_item.phone_id = phone.phone_id \n" +
            "            join ordertb on ordertb.order_id = order_item.order_id \n" +
            "            where ordertb.status = 'Completed' \n" +
            "            group by brand.brand_id, brand.brand_name", nativeQuery = true)
    List<BrandRevenueDTO> ListRevenueOfBrand();
}
