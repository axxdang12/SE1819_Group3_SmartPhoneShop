package swp391.SPS.services;
import org.springframework.data.domain.Page;
import swp391.SPS.entities.Brand;

import java.util.List;

public interface BrandService {
    Brand getBrand(int b);
    List<Brand> findAllBrand();
    void addBrand (Brand b);
    void editBrand(Brand b);

}
