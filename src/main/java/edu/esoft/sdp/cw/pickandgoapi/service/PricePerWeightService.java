package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerWeightDTO;

import java.util.List;

public interface PricePerWeightService {

    PricePerWeightDTO save(PricePerWeightDTO pricePerWeightDTO) throws Exception;

    PricePerWeightDTO findById(Long id) throws Exception;

    List<PricePerWeightDTO> findAll() throws Exception;

}
