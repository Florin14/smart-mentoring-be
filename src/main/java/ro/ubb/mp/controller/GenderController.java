package ro.ubb.mp.controller;

import ro.ubb.mp.controller.dto.mapper.GenderMapper;
import ro.ubb.mp.controller.dto.request.GenderRequestDTO;
import ro.ubb.mp.controller.dto.response.gender.GenderResponseDTO;
import ro.ubb.mp.dao.model.Gender;
import ro.ubb.mp.service.gender.GenderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gender")
@Getter
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderService;
    private final GenderMapper genderMapper;

    @GetMapping()
    public ResponseEntity<List<GenderResponseDTO>> getGenders() {
        List<Gender> genders = genderService.getAll();
        List<GenderResponseDTO> genderResponseDTOS = genders.stream()
                .map(gender -> getGenderMapper().toDTO(gender)).collect(Collectors.toList());

        return ResponseEntity.ok().body(genderResponseDTOS);
    }

    @PostMapping()
    public ResponseEntity<GenderResponseDTO> addGender(@RequestBody GenderRequestDTO genderRequestDTO) {
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addGender").toUriString()));
        return ResponseEntity.created(uri).body(getGenderMapper().toDTO(getGenderService().saveGender(genderRequestDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderResponseDTO> updateGender(@RequestBody GenderRequestDTO genderRequestDTO,
                                                              @PathVariable Long id) {
        return ResponseEntity.ok().body(getGenderMapper().toDTO(getGenderService().
                updateGender(genderRequestDTO, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteGender(@PathVariable Long id) {

        final Gender gender = getGenderService().findById(id).
                orElseThrow(EntityNotFoundException::new);
        genderService.deleteGenderById(gender.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
