package swp391.SPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.entities.Brand;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.repositories.BrandRepository;
import swp391.SPS.services.BrandService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand getBrand(int b) throws FileNotFoundException {
        if(brandRepository.findById(b).isEmpty()) return null;
        return brandRepository.findById(b).get();
    }

    @Override
    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public void addBrand(Brand b) {
        brandRepository.save(b);
    }

    @Override
    public void editBrand(Brand b) {
      Brand existingBrand = brandRepository.getReferenceById(b.getBrandId());
      existingBrand.setBrandName(b.getBrandName());
      brandRepository.save(existingBrand);
    }

    @Override
    public List<BrandRevenueDTO> GetBrandRevenueByDate(Date start, Date end) {
        LocalDate startDate = convertToLocalDate(start);
        LocalDate endDate = convertToLocalDate(end);

        List<BrandRevenueDTO> results = brandRepository.ListRevenueOfBrandBuDate(startDate, endDate);
        return results != null ? results : null;
    }

    @Override
    public List<BrandRevenueDTO> GetBrandRevenue() {

            List<BrandRevenueDTO> list = brandRepository.ListRevenueOfBrand();
            if(list==null) return null;
            return list;

    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
