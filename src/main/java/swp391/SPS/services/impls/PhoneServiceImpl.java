package swp391.SPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.entities.Brand;
//import swp391.SPS.entities.Category;
import swp391.SPS.entities.Phone;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.repositories.BrandRepository;
import swp391.SPS.repositories.PhoneRepository;
//import swp391.SPS.repositories.CategoryRepository;
//import swp391.SPS.repositories.StatisticRepository;
import swp391.SPS.services.BrandService;
import swp391.SPS.services.PhoneService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandService brandService;

//    @Autowired
//    private StatisticRepository statisticRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;


    @Override
    public List<Phone> findAllPhone() {
        return phoneRepository.findAll();
    }

    @Override
    public void addPhone(Phone phone) {

        phoneRepository.save(phone);
    }

    @Override
    public Phone getPhoneByID(int id) throws FileNotFoundException {
      if(phoneRepository.findById(id).isEmpty()){
          throw new FileNotFoundException("Not found!");
      }
       return phoneRepository.findById(id).get();
    }

    @Override
    public Phone getPhoneByIdForManager(int id) {
        if(phoneRepository.findById(id).isEmpty()){
            return null;
        }
        return phoneRepository.findById(id).get();
    }

    @Override
    public List<Phone> getPhoneByBrand(int id) throws FileNotFoundException {
        List<Phone> listPhone = findAllPhone();
        Brand brand = brandService.getBrand(id);
        if(brand ==null) return null;
        List<Phone> l = new ArrayList<>();
        for (int i = 0; i < listPhone.size(); i++) {
            if(listPhone.get(i).getBrand().equals(brand) && listPhone.get(i).getStatus()) l.add(listPhone.get(i));
        }
        return l;
    }

    @Override
    public void editPhone(Phone p) {
        Phone existingPhone = phoneRepository.getReferenceById(p.getPhoneId());
        if (existingPhone != null) {
            existingPhone.setProductName(p.getProductName());
            existingPhone.setPrice(p.getPrice());
            existingPhone.setCpu(p.getCpu());
            existingPhone.setRam(p.getRam());
            existingPhone.setMemory(p.getMemory());
            existingPhone.setDisplay(p.getDisplay());
            existingPhone.setCamera(p.getCamera());
            existingPhone.setOrigin(p.getOrigin());
            existingPhone.setSim(p.getSim());
            existingPhone.setReleaseDate(p.getReleaseDate());
            existingPhone.setStatus(p.getStatus());
            existingPhone.setBrand(p.getBrand());
            existingPhone.setPicture(p.getPicture());
            existingPhone.getPicture().setBack(p.getPicture().getBack());
            existingPhone.getPicture().setFront(p.getPicture().getFront());
            existingPhone.getPicture().setMain(p.getPicture().getMain());
            existingPhone.getPicture().setSite(p.getPicture().getSite());
            // Lưu đối tượng Phone đã được cập nhật vào cơ sở dữ liệu
            phoneRepository.save(existingPhone);

        }
    }
    @Override
    public void changeStatus(Phone p) {
        p.setStatus(!p.getStatus());
        phoneRepository.save(p);
    }

    @Override
    public List<Phone> searchPhone(String name) {

        return phoneRepository.SearchProduct(name);
    }

    @Override
    public Page<Phone> findPhonePage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1,5);
        return this.phoneRepository.findAll(pageable);

    }


    @Override
    public Page<Phone> searchPhone(String name, int pageNo) {
        List<Phone> list = phoneRepository.SearchProduct(name);
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list, pageable, phoneRepository.SearchProduct(name).size());
    }

    @Override
    public List<Phone> getbestsale() throws FileNotFoundException {
        List<Integer> li = phoneRepository.getBestSale();
        List<Phone> lp = new ArrayList<>();
        for(Integer i : li){
            lp.add(getPhoneByID(i));
        }
        return lp;
    }

    @Override
    public Page<Phone> viewphoneforshop(int pageno) {
        Pageable pageable = PageRequest.of(pageno - 1, 9);
        return phoneRepository.ViewProductforShop(pageable);
    }

    @Override
    public Page<Phone> searchPhoneforShop(String name, int pageNo) {
        List<Phone> list = phoneRepository.SearchProductforShop(name);
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list, pageable, phoneRepository.SearchProductforShop(name).size());
    }


    @Override
    public Page<Phone> getPhoneBrandByPahination(int id, int pageNo) throws FileNotFoundException {
        List<Phone> list = getPhoneByBrand(id);
        if(list == null) return null;
        Pageable pageable = PageRequest.of(pageNo -1,6);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list,pageable,getPhoneByBrand(id).size());
    }

    @Override
    public Page<Phone> searchPhoneByStatus(boolean status, int pageNo) {
        List<Phone> list = phoneRepository.searchPhoneByStatus(status);
        Pageable pageable = PageRequest.of(pageNo-1,5);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list,pageable, phoneRepository.searchPhoneByStatus(status).size());
    }

    @Override
    public Page<Phone> searchByPrice(double min, double max, int PageNo) {
        Pageable pageable = PageRequest.of(PageNo-1,6);
        return phoneRepository.findByPriceRangeAndStatus(min,max,pageable);
    }

    @Override
    public String GetTotalRevenue() {
        if(phoneRepository.TotalRevenue()!= null) return phoneRepository.TotalRevenue();
        return null;
    }

    @Override
    public String GetRevenueByDate(Date start, Date end) {
        if(phoneRepository.TotalRevenueByDate(start,end)!= null) return phoneRepository.TotalRevenueByDate(start,end);
        return null;
    }



    @Override
    public List<BrandRevenueDTO> GetBrandRevenue() {
       List<BrandRevenueDTO> list = brandRepository.ListRevenueOfBrand();
       if(list==null) return null;
       return list;
    }


}
