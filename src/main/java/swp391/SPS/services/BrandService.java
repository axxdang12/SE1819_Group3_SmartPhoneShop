package swp391.SPS.services;
import swp391.SPS.entities.Brand;

import java.util.List;

public interface BrandService {
    Brand getBrand(int b);
    List<Brand> findAllBrand();
}
