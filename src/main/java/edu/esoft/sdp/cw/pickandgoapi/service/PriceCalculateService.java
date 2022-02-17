package edu.esoft.sdp.cw.pickandgoapi.service;

import java.math.BigDecimal;

public interface PriceCalculateService {

    BigDecimal calculateDeliveryCharge(double distance, double weight, Long miscellaneousTypeId) throws Exception;

}
