package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerDistanceDTO;

import java.util.List;

public interface PricePerDistanceService {

    PricePerDistanceDTO save(PricePerDistanceDTO pricePerDistanceDTO) throws Exception;

    PricePerDistanceDTO findById(Long id) throws Exception;

    List<PricePerDistanceDTO> findAll() throws Exception;

}
