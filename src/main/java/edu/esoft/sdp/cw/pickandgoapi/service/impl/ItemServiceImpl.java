package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import org.springframework.stereotype.Service;

import edu.esoft.sdp.cw.pickandgoapi.dto.ItemDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Item;
import edu.esoft.sdp.cw.pickandgoapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  @Override
  public ItemDTO findItemById(final String itemId) {
    return null;
  }

  @Override
  public ItemDTO convertItemToItemDTO(final Item item) {
    return null;
  }
}
