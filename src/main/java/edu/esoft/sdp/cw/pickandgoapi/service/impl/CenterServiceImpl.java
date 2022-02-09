package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.CenterDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Center;
import edu.esoft.sdp.cw.pickandgoapi.enums.ActiveStatus;
import edu.esoft.sdp.cw.pickandgoapi.repository.CenterRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

  private final CenterRepository centerRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CenterDTO saveOrUpdate(final CenterDTO centerDTO) throws Exception {

    if (centerDTO.getRegistrationId() != null) {
      final Optional<Center> optionalCenter =
          centerRepository.findById(centerDTO.getRegistrationId());

      if (optionalCenter.isPresent()) {
        final Center center = convertCenterDtoToCenter(centerDTO);
        center.setRegistrationId(optionalCenter.get().getRegistrationId());

        return convertCenterToCenterDto(centerRepository.save(center));
      }
    }

    final Center center = convertCenterDtoToCenter(centerDTO);

    final Center savedCenter = centerRepository.save(center);

    return convertCenterToCenterDto(savedCenter);
  }

  @Override
  public List<CenterDTO> getAllActiveCenters() throws Exception {

    return centerRepository.findAllByIsActive(ActiveStatus.IsActive.status).stream()
        .map(this::convertCenterToCenterDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<CenterDTO> getAllInActiveCenters() throws Exception {
    return centerRepository.findAllByIsActive(ActiveStatus.IsInActive.status).stream()
        .map(this::convertCenterToCenterDto)
        .collect(Collectors.toList());
  }

  @Override
  public CenterDTO getCenterById(final Long registrationId) throws Exception {

    return convertCenterToCenterDto(
        centerRepository
            .findById(registrationId)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Center not found for registrationId: " + registrationId)));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CenterDTO deleteCenter(final Long registrationId) throws Exception {
    final Optional<Center> optionalCenter = centerRepository.findById(registrationId);
    if (!optionalCenter.isPresent()) {
      throw new RuntimeException("Center not found for registrationId: " + registrationId);
    }

    final Center center = optionalCenter.get();
    center.setIsActive(ActiveStatus.IsInActive.status);

    return convertCenterToCenterDto(centerRepository.save(center));
  }

  private CenterDTO convertCenterToCenterDto(final Center center) {
    final CenterDTO centerDTO = new CenterDTO();
    BeanUtils.copyProperties(center, centerDTO);

    return centerDTO;
  }

  private Center convertCenterDtoToCenter(final CenterDTO centerDTO) {
    final Center center = new Center();
    BeanUtils.copyProperties(centerDTO, center);
    return center;
  }
}
