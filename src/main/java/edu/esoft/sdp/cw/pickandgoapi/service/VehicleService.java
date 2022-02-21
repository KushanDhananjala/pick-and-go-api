package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    VehicleDTO saveOrUpdate(VehicleDTO vehicleDTO);

    List<VehicleDTO> getAllByStatus(boolean availability);

    VehicleDTO getVehicleByVehicleNo(String vehicleNo);

    VehicleDTO updateAvailability(String vehicleNo, boolean availability);
}
