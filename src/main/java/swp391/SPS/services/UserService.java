package swp391.SPS.services;

import org.springframework.http.ResponseEntity;
import swp391.SPS.dtos.*;
import swp391.SPS.entities.Phone;
import swp391.SPS.entities.User;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.exceptions.NoDataInListException;
import swp391.SPS.exceptions.OutOfPageException;
import swp391.SPS.exceptions.UserNotFoundException;

import java.util.Date;
import java.util.List;

public interface UserService {
    User save(UserDto userDto);

    User save(UserAddDto userAddDto);

    User save(User user);

    User saveProfile(ProfileDto profileDto, String userName);

    User saveProfileCheckout(ProfileDto profileDto, String userName);

    User findByUsername(String username);

    int getUserId(String userName);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    public User getByResetPasswordToken(String token);

    public void updatePassword(User user, String newPassword);

    User findByEmail(String email);

    User findUserByOrderId(int orderId);

    PageDto getListUserFirstLoad(int page, int size, String search) throws NoDataInListException, OutOfPageException, FileNotFoundException;

    User saveUserRole(int userId, String roleName) throws UserNotFoundException;

    void saveUserActive(int userId, String status) throws UserNotFoundException;

    List<StatisticsUserOrder> TotalOderOfUser();

    List<StatisticsUserOrder> TotalOrderOfUserByDate(Date start,Date end);

    User findUserById(int userId);
}
