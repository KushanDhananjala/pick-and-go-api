package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerDistanceDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerWeightDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.PricePerDistance;
import edu.esoft.sdp.cw.pickandgoapi.entity.PricePerWeight;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.PricePerDistanceRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.PricePerDistanceService;
import edu.esoft.sdp.cw.pickandgoapi.service.PricePerWeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PricePerDistanceServiceImpl implements PricePerDistanceService {

    private final PricePerDistanceRepository pricePerDistanceRepository;

    @Override
    public PricePerDistanceDTO save(PricePerDistanceDTO pricePerWeightDTO) throws Exception {
        PricePerDistance pricePerDistance = convertPricePerDistanceDtoToPricePerDistance(pricePerWeightDTO);

        Optional<PricePerDistance> optionalPricePerDistance = pricePerDistanceRepository.findById(pricePerWeightDTO.getId());

        optionalPricePerDistance.ifPresent(value -> pricePerDistance.setId(value.getId()));

        return convertPricePerDistanceToPricePerDistanceDTO(pricePerDistanceRepository.save(pricePerDistance));
    }

    @Override
    public PricePerDistanceDTO findById(Long id) throws Exception {
        return convertPricePerDistanceToPricePerDistanceDTO(
                pricePerDistanceRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Price per distance config not found for id: " + id)));
    }

    @Override
    public List<PricePerDistanceDTO> findAll() throws Exception {
        return pricePerDistanceRepository.findAll().stream()
                .map(this::convertPricePerDistanceToPricePerDistanceDTO)
                .collect(Collectors.toList());
    }

    private PricePerDistanceDTO convertPricePerDistanceToPricePerDistanceDTO(PricePerDistance pricePerDistance) {
        PricePerDistanceDTO pricePerDistanceDTO = new PricePerDistanceDTO();
        BeanUtils.copyProperties(pricePerDistance, pricePerDistanceDTO);

        return pricePerDistanceDTO;
    }

    private PricePerDistance convertPricePerDistanceDtoToPricePerDistance(PricePerDistanceDTO pricePerDistanceDTO) {
        PricePerDistance pricePerDistance = new PricePerDistance();
        BeanUtils.copyProperties(pricePerDistanceDTO, pricePerDistance);

        return pricePerDistance;
    }
}
