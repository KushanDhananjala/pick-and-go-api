package edu.esoft.sdp.cw.pickandgoapi.service;

import java.util.List;

import edu.esoft.sdp.cw.pickandgoapi.dto.CenterDTO;

public interface CenterService {

  CenterDTO saveOrUpdate(CenterDTO centerDTO);

  List<CenterDTO> getAllActiveCenters();

  List<CenterDTO> getAllInActiveCenters();

  CenterDTO getCenterById(Long registrationId);

  CenterDTO deleteCenter(Long registrationId);
}
