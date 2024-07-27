package swp391.SPS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp391.SPS.entities.Phone;
import swp391.SPS.entities.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

}
