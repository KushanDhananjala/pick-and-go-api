package edu.esoft.sdp.cw.pickandgoapi.entity;

import edu.esoft.sdp.cw.pickandgoapi.enums.VehicleType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
public class Vehicle extends Auditable<Long> implements Serializable {
    @Id
    private String vehicleNo;
    private BigDecimal maxCapacity;
    @Enumerated
    private VehicleType vehicleType;
    private boolean availability;
}
