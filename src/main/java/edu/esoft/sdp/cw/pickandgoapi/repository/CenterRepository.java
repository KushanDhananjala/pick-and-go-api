package edu.esoft.sdp.cw.pickandgoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.esoft.sdp.cw.pickandgoapi.entity.Center;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

  List<Center> findAllByIsActive(int isActive);

  @Query(
      value =
          "SELECT * ,"
              + "(6371 * acos(cos(radians(?1)) * cos(radians(c.latitude)) * cos(radians(c.longitude) - radians(?2)) + sin(radians(?1)) * sin(radians(c.latitude )))) AS radius "
              + "FROM centers c "
              + "WHERE is_active = '1' "
              + "HAVING radius < ?3 ORDER BY radius LIMIT 0, 20",
      nativeQuery = true)
  List<Center> getNearByCenters(double lat, double lng, double radius);
}
