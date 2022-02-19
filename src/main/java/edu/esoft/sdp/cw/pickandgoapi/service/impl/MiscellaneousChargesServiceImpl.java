package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.MiscellaneousChargesDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.MiscellaneousCharges;
import edu.esoft.sdp.cw.pickandgoapi.repository.MiscellaneousChargesRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.MiscellaneousChargesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MiscellaneousChargesServiceImpl implements MiscellaneousChargesService {


    private final MiscellaneousChargesRepository miscellaneousChargesRepository;

    @Override
    public List<MiscellaneousChargesDTO> findAll() throws Exception {
        return miscellaneousChargesRepository.findAll().stream()
                .map(this::convertMiscellaneousChargesToMiscellaneousChargesDTO)
                .collect(Collectors.toList());
    }

    private MiscellaneousChargesDTO convertMiscellaneousChargesToMiscellaneousChargesDTO(MiscellaneousCharges miscellaneousCharges) {
        MiscellaneousChargesDTO miscellaneousChargesDTO = new MiscellaneousChargesDTO();
        BeanUtils.copyProperties(miscellaneousCharges, miscellaneousChargesDTO);

        return miscellaneousChargesDTO;
    }
}
