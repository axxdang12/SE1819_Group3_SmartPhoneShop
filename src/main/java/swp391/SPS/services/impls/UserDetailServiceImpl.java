package swp391.SPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.SPS.entities.UserDetail;
import swp391.SPS.repositories.UserDetailRepository;
import swp391.SPS.services.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired private UserDetailRepository userDetailRepository;
    @Override
    public UserDetail getUserDetailByUserId(int userId) {
        return userDetailRepository.getUserDetailByUserId(userId);
    }
}
