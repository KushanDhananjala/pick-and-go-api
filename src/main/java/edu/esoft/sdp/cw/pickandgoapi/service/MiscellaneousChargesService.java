package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.MiscellaneousChargesDTO;

import java.util.List;

public interface MiscellaneousChargesService {

    List<MiscellaneousChargesDTO> findAll() throws Exception;

}
