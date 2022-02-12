package edu.esoft.sdp.cw.pickandgoapi.repository;

import edu.esoft.sdp.cw.pickandgoapi.entity.CenterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterUserRepository extends JpaRepository<CenterUser, Long> {

    CenterUser findCenterUserByUserId(Long userId);

}
