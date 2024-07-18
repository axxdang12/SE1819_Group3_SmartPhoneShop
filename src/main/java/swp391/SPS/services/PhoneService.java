package swp391.SPS.services;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import swp391.SPS.dtos.BrandRevenueDTO;
import swp391.SPS.dtos.PageDto;
import swp391.SPS.entities.Phone;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.exceptions.NoDataInListException;
import swp391.SPS.exceptions.OutOfPageException;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PhoneService {
    List<Phone> findAllPhone();
    void addPhone(Phone phone);
    Phone getPhoneByID(int id) throws FileNotFoundException;
    Phone getPhoneByIdForManager(int id);
    List<Phone> getPhoneByBrand(int id) throws FileNotFoundException;
    void editPhone(Phone p);
    void changeStatus(Phone p);
    List<Phone> searchPhone(String name);
    Page<Phone> findPhonePage(int pageNo);
    Page<Phone> searchPhone(String name, int pageNo);
    List<Phone> getbestsale() throws FileNotFoundException;
    Page<Phone> viewphoneforshop(int pageno);
    Page<Phone> searchPhoneforShop(String name,int pageNo);
    Page<Phone> getPhoneBrandByPahination(int id,int pageNo) throws FileNotFoundException;
    Page<Phone> searchPhoneByStatus(boolean status,int pageNo);
    Page<Phone> searchByPrice(double min, double max,int PageNo);
    String GetTotalRevenue();
//    List<BrandRevenueDTO> GetBrandRevenue();
}
