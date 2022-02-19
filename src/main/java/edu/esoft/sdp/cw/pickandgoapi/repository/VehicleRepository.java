package edu.esoft.sdp.cw.pickandgoapi.repository;

import edu.esoft.sdp.cw.pickandgoapi.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findAllByAvailability(boolean availability);
}
