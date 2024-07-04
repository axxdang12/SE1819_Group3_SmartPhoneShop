package swp391.SPS.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp391.SPS.entities.Order;
import swp391.SPS.entities.Phone;


import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM phone WHERE product_name LIKE %:name%", nativeQuery = true)
    List<Phone> SearchProduct(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM phone WHERE product_name LIKE %:name% AND phone.status = true", nativeQuery = true)
    List<Phone> SearchProductforShop(@Param("name") String name);



    @Query(value = "select * from phone where phone.status = true", nativeQuery = true)
    Page<Phone> ViewProductforShop(Pageable pageable);


    @Query(value = "SELECT * FROM phone WHERE price >= :minPrice AND price <= :maxPrice AND status = true", nativeQuery = true)
    Page<Phone> findByPriceRangeAndStatus(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "SELECT phone.phone_id\n" +
            "FROM phone join order_item on phone.phone_id = order_item.phone_id\n" +
            "join ordertb on order_item.order_id = ordertb.order_id\n" +
            "where ordertb.status = 'Completed' \n" +
            "group by phone.phone_id \n" +
            "order by sum(order_item.quantity) desc LIMIT 3;\n", nativeQuery = true)
    List<Integer> getBestSale();

    @Modifying
    @Transactional
    @Query(value = "select * from phone where phone.status = :status" , nativeQuery = true)
    List<Phone> searchPhoneByStatus(@Param("status") boolean status);



}
