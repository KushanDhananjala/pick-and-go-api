package edu.esoft.sdp.cw.pickandgoapi.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AssignRiderDTO {

  @NotEmpty private String riderUserName;
}
