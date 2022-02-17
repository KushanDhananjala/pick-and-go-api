package edu.esoft.sdp.cw.pickandgoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esoft.sdp.cw.pickandgoapi.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {}
