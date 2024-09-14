package ro.ubb.mp.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.ubb.mp.controller.dto.mapper.InterestAreaMapper;
import ro.ubb.mp.controller.dto.request.InterestAreaRequestDTO;
import ro.ubb.mp.controller.dto.response.InterestAreaResponseDTO;
import ro.ubb.mp.controller.dto.response.ResponseWrapperDTO;
import ro.ubb.mp.dao.model.InterestArea;
import ro.ubb.mp.service.interestArea.InterestAreaService;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interest-areas")
@RequiredArgsConstructor
@Getter
public class InterestAreaController {

    private final InterestAreaService interestAreaService;
    private final InterestAreaMapper interestAreaMapper;

    @GetMapping()
    public ResponseEntity<ResponseWrapperDTO<List<InterestAreaResponseDTO>>> getAll() {
        final List<InterestArea> interestAreas = getInterestAreaService().getAll();

        List<InterestAreaResponseDTO> dtos = new ArrayList<>();
        if(!interestAreas.isEmpty()) {
            dtos = interestAreas.stream()
                    .map(interestArea -> getInterestAreaMapper().toDto(interestArea))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(
                ResponseWrapperDTO.<List<InterestAreaResponseDTO>>builder()
                        .data(dtos)
                        .build()
        );

    }

    @PostMapping()
    public ResponseEntity<InterestAreaResponseDTO> addInterestArea(@RequestBody InterestAreaRequestDTO interestAreaRequestDTO) {
        URI uri = URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/addInterestArea").toUriString()));

        return ResponseEntity.created(uri).body(getInterestAreaMapper().toDto(getInterestAreaService().saveInterestArea(interestAreaRequestDTO)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<InterestAreaResponseDTO> updateInterestArea(@RequestBody InterestAreaRequestDTO interestAreaRequestDTO,
                                                                      @PathVariable Long id) {
        return ResponseEntity.ok().body(getInterestAreaMapper().toDto(getInterestAreaService().
                updateInterestArea(interestAreaRequestDTO, id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteInterestArea(@PathVariable Long id) {

        final InterestArea interestArea = getInterestAreaService().findById(id).orElseThrow(EntityNotFoundException::new);
        interestAreaService.deleteInterestAreaById(interestArea.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
