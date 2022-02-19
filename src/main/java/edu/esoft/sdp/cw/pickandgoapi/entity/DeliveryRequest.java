package edu.esoft.sdp.cw.pickandgoapi.entity;

import com.google.common.base.Objects;
import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(
    name = "delivery_requests",
    indexes = @Index(name = "internalId", columnList = "internalId", unique = true))
public class DeliveryRequest extends Auditable<Long> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String internalId;
  private String displayId;

  @ManyToOne
  @JoinColumn(name = "customer", referencedColumnName = "userName")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "itemId", referencedColumnName = "id")
  private Package aPackage;

  @ManyToOne
  @JoinColumn(name = "rider", referencedColumnName = "userName")
  private User rider;

  private BigDecimal deliveryFee;
  private String fromAddress;
  private String toAddress;
  private double distance;
  @DateTimeFormat private Instant requestedTime;
  @DateTimeFormat private Instant acceptedTime;
  @Enumerated private DeliveryRequestStatus status;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final DeliveryRequest that = (DeliveryRequest) o;
    return Double.compare(that.distance, distance) == 0
            && Objects.equal(id, that.id)
            && Objects.equal(internalId, that.internalId)
            && Objects.equal(displayId, that.displayId)
            && Objects.equal(customer, that.customer)
            && Objects.equal(user, that.user)
            && Objects.equal(aPackage, that.aPackage)
            && Objects.equal(deliveryFee, that.deliveryFee)
            && Objects.equal(fromAddress, that.fromAddress)
            && Objects.equal(toAddress, that.toAddress)
            && Objects.equal(requestedTime, that.requestedTime)
            && Objects.equal(acceptedTime, that.acceptedTime)
            && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
            super.hashCode(),
            id,
            internalId,
            displayId,
            customer,
            user,
            aPackage,
            deliveryFee,
            fromAddress,
            toAddress,
            distance,
            requestedTime,
            acceptedTime,
            status);
  }
}
