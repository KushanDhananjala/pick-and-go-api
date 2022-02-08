package edu.esoft.sdp.cw.pickandgoapi.repository;

import edu.esoft.sdp.cw.pickandgoapi.entity.PricePerWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricePerWeightRepository extends JpaRepository<PricePerWeight, Long> {
}
