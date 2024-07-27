package swp391.SPS.services;
import org.springframework.data.domain.Page;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.entities.Brand;
import swp391.SPS.exceptions.FileNotFoundException;

import java.util.Date;
import java.util.List;

public interface BrandService {
    Brand getBrand(int b) throws FileNotFoundException;

    List<Brand> findAllBrand();
    void addBrand (Brand b);
    void editBrand(Brand b);
    List<BrandRevenueDTO> GetBrandRevenueByDate(Date start, Date end);
    List<BrandRevenueDTO> GetBrandRevenue();
}
