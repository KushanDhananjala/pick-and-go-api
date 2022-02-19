package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.VehicleDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Vehicle;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.VehicleRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public VehicleDTO saveOrUpdate(VehicleDTO vehicleDTO) {
        final Vehicle vehicle = convertVehicleDtoToVehicle(vehicleDTO);
        return convertVehicleToVehicleDto(vehicleRepository.save(vehicle));

    }

    @Override
    public List<VehicleDTO> getAllByStatus(boolean availability) {
        return vehicleRepository.findAllByAvailability(availability).stream()
                .map(this::convertVehicleToVehicleDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO getVehicleByVehicleNo(String vehicleNo) {
        return convertVehicleToVehicleDto(
                vehicleRepository.findById(vehicleNo)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "vehicle not found for vehicleNo: " + vehicleNo)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public VehicleDTO updateAvailability(String vehicleNo, boolean availability) {
        Vehicle vehicle = vehicleRepository.findById(vehicleNo).orElseThrow(() ->
                new NotFoundException(
                        "Vehicle not found for vehicleNo: " + vehicleNo));
        vehicle.setAvailability(availability);
        return convertVehicleToVehicleDto(vehicleRepository.save(vehicle));
    }

    private Vehicle convertVehicleDtoToVehicle(final VehicleDTO vehicleDTO) {
        final Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDTO, vehicle);
        return vehicle;
    }

    private VehicleDTO convertVehicleToVehicleDto(final Vehicle vehicle) {
        final VehicleDTO vehicleDTO = new VehicleDTO();
        BeanUtils.copyProperties(vehicle, vehicleDTO);

        return vehicleDTO;
    }
}
