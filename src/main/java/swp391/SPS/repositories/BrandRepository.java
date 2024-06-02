package swp391.SPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp391.SPS.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
