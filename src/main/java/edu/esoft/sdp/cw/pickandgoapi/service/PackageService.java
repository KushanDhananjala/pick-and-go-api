package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.PackageDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Package;

import java.util.List;

public interface PackageService {

    PackageDTO save(PackageDTO packageDTO) throws Exception;

    List<PackageDTO> findAll() throws Exception;

    PackageDTO findItemById(Long packageId);

    PackageDTO convertPackageToPackageDTO(Package aPackage);
}
