package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.ItemDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Item;

public interface ItemService {

  ItemDTO findItemById(String itemId);

  ItemDTO convertItemToItemDTO(Item item);
}
