package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import edu.esoft.sdp.cw.pickandgoapi.dto.MiscellaneousChargesDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.MiscellaneousCharges;
import edu.esoft.sdp.cw.pickandgoapi.enums.MiscellaneousTypes;
import edu.esoft.sdp.cw.pickandgoapi.repository.MiscellaneousChargesRepository;

class MiscellaneousChargesServiceImplTest {

  @InjectMocks private MiscellaneousChargesServiceImpl miscellaneousChargesService;
  @Mock private MiscellaneousChargesRepository repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAll() throws Exception {
    MiscellaneousCharges charges = new MiscellaneousCharges();
    charges.setId(1L);
    charges.setPrice(BigDecimal.valueOf(50.00));
    charges.setType(MiscellaneousTypes.ELECTRONIC);
    when(repository.findAll()).thenReturn(List.of(charges));
    List<MiscellaneousChargesDTO> all = miscellaneousChargesService.findAll();
    Assertions.assertFalse(CollectionUtils.isEmpty(all));
    Assertions.assertEquals(1,all.size());
  }
}
