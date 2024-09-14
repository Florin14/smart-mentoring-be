package ro.ubb.mp.service.interestArea;

import ro.ubb.mp.controller.dto.request.InterestAreaRequestDTO;
import ro.ubb.mp.dao.model.Assignment;
import ro.ubb.mp.dao.model.InterestArea;

import java.util.List;
import java.util.Optional;

public interface InterestAreaService {
    Optional<InterestArea> findById(Long id);
    List<InterestArea> getAll();
    InterestArea saveInterestArea(InterestAreaRequestDTO interestAreaRequestDTO);

    InterestArea updateInterestArea(InterestAreaRequestDTO interestAreaRequestDTO, Long id);


    void deleteInterestAreaById(Long id);

}
