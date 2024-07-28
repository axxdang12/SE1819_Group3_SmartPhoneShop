package swp391.SPS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp391.SPS.entities.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
    @Query("SELECT u FROM UserDetail u JOIN User user ON user.userDetail.userDetailId = u.userDetailId WHERE user.userId = ?1")
    UserDetail getUserDetailByUserId(int userId);
}
