package ro.ubb.mp.service.gender;

import ro.ubb.mp.controller.dto.request.GenderRequestDTO;
import ro.ubb.mp.dao.model.Gender;

import java.util.List;
import java.util.Optional;

public interface GenderService {
    Optional<Gender> findById(Long id);

    List<Gender> getAll();

    Gender saveGender(GenderRequestDTO genderRequestDTO);

    Gender updateGender(GenderRequestDTO genderRequestDTO, Long id);

    void deleteGenderById(Long id);

}
