package swp391.SPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.SPS.entities.Picture;
import swp391.SPS.repositories.PictureRepository;
import swp391.SPS.services.PictureService;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public Picture getPictureById(int p) {
        return pictureRepository.getReferenceById(p);
    }

    @Override
    public void addPicture(Picture p) {
        pictureRepository.save(p);
    }

    @Override
    public void editPicture(Picture p) {

    }


    @Override
    public void deletePicture(Picture p) {
        pictureRepository.deleteById(p.getPictureId());
    }


}
