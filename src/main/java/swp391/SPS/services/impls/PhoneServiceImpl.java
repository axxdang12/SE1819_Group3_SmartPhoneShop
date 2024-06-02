package swp391.SPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.SPS.entities.Phone;
import swp391.SPS.repositories.PhoneRepository;
import swp391.SPS.services.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public void addPhone(Phone phone) {
        phoneRepository.save(phone);
    }
}
