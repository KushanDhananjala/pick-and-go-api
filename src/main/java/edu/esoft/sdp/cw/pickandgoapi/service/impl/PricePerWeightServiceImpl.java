package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerWeightDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.PricePerWeight;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.PricePerWeightRepository;
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
public class PricePerWeightServiceImpl implements PricePerWeightService {

    private final PricePerWeightRepository pricePerWeightRepository;

    @Override
    public PricePerWeightDTO save(PricePerWeightDTO pricePerWeightDTO) throws Exception {
        PricePerWeight pricePerWeight = convertPricePerWeightDtoToPricePerWeight(pricePerWeightDTO);

        Optional<PricePerWeight> optionalPricePerWeight = pricePerWeightRepository.findById(pricePerWeightDTO.getId());

        optionalPricePerWeight.ifPresent(value -> pricePerWeight.setId(value.getId()));

        return convertPricePerWeightToPricePerWeightDTO(pricePerWeightRepository.save(pricePerWeight));
    }

    @Override
    public PricePerWeightDTO findById(Long id) throws Exception {
        return convertPricePerWeightToPricePerWeightDTO(
                pricePerWeightRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Price per weight config not found for id: " + id)));
    }

    @Override
    public List<PricePerWeightDTO> findAll() throws Exception {
        return pricePerWeightRepository.findAll().stream()
                .map(this::convertPricePerWeightToPricePerWeightDTO)
                .collect(Collectors.toList());
    }

    private PricePerWeightDTO convertPricePerWeightToPricePerWeightDTO(PricePerWeight pricePerWeight) {
        PricePerWeightDTO pricePerWeightDTO = new PricePerWeightDTO();
        BeanUtils.copyProperties(pricePerWeight, pricePerWeightDTO);

        return pricePerWeightDTO;
    }

    private PricePerWeight convertPricePerWeightDtoToPricePerWeight(PricePerWeightDTO pricePerWeightDTO) {
        PricePerWeight pricePerWeight = new PricePerWeight();
        BeanUtils.copyProperties(pricePerWeightDTO, pricePerWeight);

        return pricePerWeight;
    }
}
