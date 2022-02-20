package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import edu.esoft.sdp.cw.pickandgoapi.dto.CenterDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Center;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.CenterRepository;

class CenterServiceImplTest {

  @InjectMocks private CenterServiceImpl centerService;
  @Mock private CenterRepository centerRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void save() {
    Center center = new Center();
    center.setName("Galle");
    center.setIsActive(1);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);
    CenterDTO centerDTO = new CenterDTO();
    BeanUtils.copyProperties(center, centerDTO);
    center.setRegistrationId(1L);
    when(centerRepository.save(any())).thenReturn(center);

    final CenterDTO response = centerService.saveOrUpdate(centerDTO);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getRegistrationId());
    Assertions.assertEquals("Galle", response.getName());
  }

  @Test
  void update() {
    Center center = new Center();
    center.setRegistrationId(1L);
    center.setName("Galle");
    center.setIsActive(1);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);
    CenterDTO centerDTO = new CenterDTO();
    BeanUtils.copyProperties(center, centerDTO);
    when(centerRepository.save(any())).thenReturn(center);
    Center savedCenter = new Center();
    savedCenter.setRegistrationId(1L);
    savedCenter.setName("galle");
    savedCenter.setIsActive(1);
    savedCenter.setLatitude(80.47F);
    savedCenter.setLongitude(6.087F);
    when(centerRepository.findById(any())).thenReturn(Optional.of(savedCenter));

    final CenterDTO response = centerService.saveOrUpdate(centerDTO);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getRegistrationId());
    Assertions.assertEquals("Galle", response.getName());
  }

  @Test
  void getAllActiveCenters() {
    Center center = new Center();
    center.setRegistrationId(1L);
    center.setName("Galle");
    center.setIsActive(1);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);

    Center center2 = new Center();
    center2.setRegistrationId(2L);
    center2.setName("Matara");
    center2.setIsActive(1);
    center2.setLatitude(90.47F);
    center2.setLongitude(7.087F);

    when(centerRepository.findAllByIsActive(1)).thenReturn(Arrays.asList(center, center2));

    List<CenterDTO> centerDTOS = centerService.getAllActiveCenters();

    Assertions.assertFalse(CollectionUtils.isEmpty(centerDTOS));
    Assertions.assertEquals(2, centerDTOS.size());
  }

  @Test
  void getAllInActiveCenters() {
    Center center = new Center();
    center.setRegistrationId(1L);
    center.setName("Galle");
    center.setIsActive(0);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);

    Center center2 = new Center();
    center2.setRegistrationId(2L);
    center2.setName("Matara");
    center2.setIsActive(0);
    center2.setLatitude(90.47F);
    center2.setLongitude(7.087F);

    when(centerRepository.findAllByIsActive(0)).thenReturn(Arrays.asList(center, center2));

    List<CenterDTO> centerDTOS = centerService.getAllInActiveCenters();

    Assertions.assertFalse(CollectionUtils.isEmpty(centerDTOS));
    Assertions.assertEquals(2, centerDTOS.size());
  }

  @Test
  void getCenterById() {

    Center center = new Center();
    center.setRegistrationId(1L);
    center.setName("Galle");
    center.setIsActive(1);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);

    when(centerRepository.findById(any())).thenReturn(Optional.of(center));
    final CenterDTO response = centerService.getCenterById(1L);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getRegistrationId());
    Assertions.assertEquals("Galle", response.getName());
  }

  @Test
  void getCenterById_exception() {

    when(centerRepository.findById(any())).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> centerService.getCenterById(1L));
  }

  @Test
  void deleteCenter() {

    Center center = new Center();
    center.setRegistrationId(1L);
    center.setName("Galle");
    center.setIsActive(0);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);

    when(centerRepository.findById(any())).thenReturn(Optional.of(center));
    final CenterDTO response = centerService.getCenterById(1L);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getRegistrationId());
    Assertions.assertEquals(0, response.getIsActive());
  }

  @Test
  void deleteCenter_exception() {

    when(centerRepository.findById(any())).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> centerService.getCenterById(1L));
  }

  @Test
  void getCenterByRadius() {

    Center center = new Center();
    center.setRegistrationId(1L);
    center.setName("Galle");
    center.setIsActive(0);
    center.setLatitude(80.47F);
    center.setLongitude(6.087F);

    when(centerRepository.getNearByCenters(78, 5, 100)).thenReturn(List.of(center));

    List<CenterDTO> centerDTOS = centerService.getCenterByRadius(78, 5, 100);

    Assertions.assertFalse(CollectionUtils.isEmpty(centerDTOS));
    Assertions.assertEquals(1, centerDTOS.size());
  }

  @Test
  void getCenterByRadius_exception() {

    when(centerRepository.getNearByCenters(78, 5, 100)).thenReturn(Collections.emptyList());

    Assertions.assertThrows(
        NotFoundException.class, () -> centerService.getCenterByRadius(78, 5, 100));
  }
}
