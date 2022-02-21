package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.PackageDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.MiscellaneousCharges;
import edu.esoft.sdp.cw.pickandgoapi.entity.Package;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.PackageRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PackageServiceImplTest {

    @InjectMocks
    PackageServiceImpl packageService;
    @Mock
    private PackageRepository packageRepository;

    @Mock
    private MiscellaneousCharges miscellaneousCharges;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() throws Exception {

        Package item = new Package();
        item.setId(1L);
        item.setMiscellaneousChargeId(miscellaneousCharges);
        item.setPrice(BigDecimal.TEN);
        PackageDTO packageDTO = new PackageDTO();
        BeanUtils.copyProperties(item, packageDTO);
        when(packageRepository.findById(any())).thenReturn(Optional.of(item));
        when(packageRepository.save(any())).thenReturn(item);

        PackageDTO save = packageService.save(packageDTO);
        Assertions.assertNotNull(save);
        Assertions.assertEquals(1L, save.getId());
    }

    @SneakyThrows
    @Test
    void findAll() {

        Package item = new Package();
        item.setId(1L);
        item.setMiscellaneousChargeId(miscellaneousCharges);
        item.setPrice(BigDecimal.TEN);

        when(packageRepository.findAll()).thenReturn(List.of(item));
        List<PackageDTO> all = packageService.findAll();

        Assertions.assertFalse(CollectionUtils.isEmpty(all));
    }

    @Test
    void findItemById() {
        Package item = new Package();
        item.setId(1L);
        item.setMiscellaneousChargeId(miscellaneousCharges);
        item.setPrice(BigDecimal.TEN);
        PackageDTO packageDTO = new PackageDTO();
        BeanUtils.copyProperties(item, packageDTO);
        when(packageRepository.findById(any())).thenReturn(Optional.of(item));

        PackageDTO res = packageService.findItemById(1L);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(1L, res.getId());
    }

    @Test
    void findItemById_exception() {

        when(packageRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> packageService.findItemById(1L));
    }

    @Test
    void convertPackageToPackageDTO() {
        Package item = new Package();
        item.setId(1L);
        item.setMiscellaneousChargeId(miscellaneousCharges);
        item.setPrice(BigDecimal.TEN);

        PackageDTO save = packageService.convertPackageToPackageDTO(item);
        Assertions.assertNotNull(save);
        Assertions.assertEquals(1L, save.getId());
    }
}
