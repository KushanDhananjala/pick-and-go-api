package edu.esoft.sdp.cw.pickandgoapi.repository;

import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
