package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.entity.MiscellaneousCharges;
import edu.esoft.sdp.cw.pickandgoapi.entity.PricePerDistance;
import edu.esoft.sdp.cw.pickandgoapi.entity.PricePerWeight;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.MiscellaneousChargesRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.PricePerDistanceRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.PricePerWeightRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.PriceCalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PriceCalculateServiceImpl implements PriceCalculateService {

    private final PricePerDistanceRepository pricePerDistanceRepository;
    private final PricePerWeightRepository pricePerWeightRepository;
    private final MiscellaneousChargesRepository miscellaneousChargesRepository;

    @Override
    public BigDecimal calculateDeliveryCharge(double distance, double weight, Long miscellaneousTypeId) throws Exception {

        PricePerDistance pricePerDistance = pricePerDistanceRepository.findById(1L).get();
        PricePerWeight pricePerWeight = pricePerWeightRepository.findById(1L).get();

        MiscellaneousCharges miscellaneousCharges = miscellaneousChargesRepository
                .findById(miscellaneousTypeId)
                .orElseThrow(
                        () ->
                                new NotFoundException("Miscellaneous Type not found for ID: " + miscellaneousTypeId));

        BigDecimal pricePerFirstKms = pricePerDistance.getFirstFiveKms();
        BigDecimal pricePerAdditionalKms = pricePerDistance.getForAdditionalKms();

        BigDecimal pricePerFirstKgs = pricePerWeight.getFirstFiveKgs();
        BigDecimal pricePerAdditionalKgs = pricePerWeight.getForAdditionalKgs();

        BigDecimal priceMiscellaneousType = miscellaneousCharges.getPrice();

        BigDecimal calculatedPrice = BigDecimal.ZERO;

        calculatedPrice = calculatedPrice.add(pricePerFirstKms);

        if (distance / 5 > 1) {
            double additionalDistance = distance - 5;
            BigDecimal additionalDistancePrice = BigDecimal.valueOf(additionalDistance).multiply(pricePerAdditionalKms);

            calculatedPrice = calculatedPrice.add(additionalDistancePrice);
        }

        calculatedPrice = calculatedPrice.add(pricePerFirstKgs);

        if (weight / 5 > 1) {
            double additionalWeight = weight - 5;
            BigDecimal additionalWeightPrice = BigDecimal.valueOf(additionalWeight).multiply(pricePerAdditionalKgs);

            calculatedPrice = calculatedPrice.add(additionalWeightPrice);
        }

        calculatedPrice = calculatedPrice.add(priceMiscellaneousType);


        return calculatedPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
