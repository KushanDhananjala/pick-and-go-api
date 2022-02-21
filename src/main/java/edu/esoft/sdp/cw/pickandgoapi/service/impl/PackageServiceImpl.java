package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.PackageDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.MiscellaneousCharges;
import edu.esoft.sdp.cw.pickandgoapi.entity.Package;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.MiscellaneousChargesRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.PackageRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final MiscellaneousChargesRepository miscellaneousChargesRepository;

    @Override
    public PackageDTO save(PackageDTO packageDTO) throws Exception {
        Package aPackage = convertPackageDTOToPackage(packageDTO);

        Optional<Package> optionalPackage = packageRepository.findById(packageDTO.getId());

        optionalPackage.ifPresent(value -> aPackage.setId(value.getId()));

        MiscellaneousCharges miscellaneousCharges = miscellaneousChargesRepository.findById(packageDTO.getMiscellaneousTypeId()).get();

        aPackage.setMiscellaneousChargeId(miscellaneousCharges);

        return convertPackageToPackageDTO(packageRepository.save(aPackage));
    }

    @Override
    public List<PackageDTO> findAll() throws Exception {
        return packageRepository.findAll().stream()
                .map(this::convertPackageToPackageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PackageDTO findItemById(Long packageId) {
        return convertPackageToPackageDTO(
                packageRepository
                        .findById(packageId)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Package not found for ID: " + packageId)));
    }

    @Override
    public PackageDTO convertPackageToPackageDTO(Package aPackage) {
        PackageDTO packageDTO = new PackageDTO();
        BeanUtils.copyProperties(aPackage, packageDTO);

        return packageDTO;
    }

    private Package convertPackageDTOToPackage(PackageDTO packageDTO) {
        Package aPackage = new Package();
        BeanUtils.copyProperties(packageDTO, aPackage);

        return aPackage;
    }
}
