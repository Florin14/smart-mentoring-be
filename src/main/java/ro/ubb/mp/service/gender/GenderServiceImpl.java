package ro.ubb.mp.service.gender;

import ro.ubb.mp.controller.dto.request.GenderRequestDTO;
import ro.ubb.mp.dao.model.Gender;
import ro.ubb.mp.dao.repository.GenderRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Service
public class GenderServiceImpl implements GenderService {
    private final GenderRepository genderRepository;

    @Override
    public Optional<Gender> findById(Long id) {
        return genderRepository.findById(id);
    }

    @Override
    public List<Gender> getAll() {
        return genderRepository.findAll();
    }

    @Override
    public Gender saveGender(GenderRequestDTO genderDTO) {
        final Gender genderToBeSaved = Gender.builder()
                .name(genderDTO.getName())

                .build();
        return genderRepository.save(genderToBeSaved);

    }

    @Override
    public Gender updateGender(GenderRequestDTO genderDTO, Long id) {
        Gender gender = findById(id).orElseThrow(EntityNotFoundException::new);

        gender.setName(genderDTO.getName());
        gender.setGenderCode(genderDTO.getGenderCode());

        return genderRepository.save(gender);

    }

    @Override
    public void deleteGenderById(Long id) {
        genderRepository.deleteById(id);
    }
}
