package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.CenterDTO;

import java.util.List;

public interface CenterService {

  CenterDTO saveOrUpdate(CenterDTO centerDTO) throws Exception;

  List<CenterDTO> getAllActiveCenters() throws Exception;

  List<CenterDTO> getAllInActiveCenters() throws Exception;

  CenterDTO getCenterById(Long registrationId) throws Exception;

  CenterDTO deleteCenter(Long registrationId) throws Exception;
}
