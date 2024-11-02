package ro.ubb.mp.service.interestArea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.ubb.mp.controller.dto.request.InterestAreaRequestDTO;
import ro.ubb.mp.dao.model.InterestArea;
import ro.ubb.mp.dao.repository.InterestAreaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service("interestAreaService")
@RequiredArgsConstructor
@Getter
public class InterestAreaServiceImpl implements InterestAreaService {
    private final InterestAreaRepository interestAreaRepository;


    @Override
    public List<InterestArea> getAll() {
        return interestAreaRepository.findAll();
    }
    @Override
    public Optional<InterestArea> findById(Long id) {
        return getInterestAreaRepository().findById(id);
    }

    @Override
    public InterestArea saveInterestArea(InterestAreaRequestDTO interestAreaRequestDTO) {


        final InterestArea interestAreaToBeSaved = InterestArea.builder()
                .name(interestAreaRequestDTO.getName())
                .build();

        return interestAreaRepository.save(interestAreaToBeSaved);
    }

    @Override
    public InterestArea updateInterestArea(InterestAreaRequestDTO interestAreaRequestDTO, Long id) {
        InterestArea assignment = findById(id).orElseThrow(EntityNotFoundException::new);

        assignment.setName(interestAreaRequestDTO.getName());
        return interestAreaRepository.save(assignment);
    }

    @Override
    public void deleteInterestAreaById(Long id) {
        interestAreaRepository.deleteById(id);
    }
}
