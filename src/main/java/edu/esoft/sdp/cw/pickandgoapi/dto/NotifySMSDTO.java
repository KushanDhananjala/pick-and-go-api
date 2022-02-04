package edu.esoft.sdp.cw.pickandgoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifySMSDTO {

    private Long user_id;
    private String api_key;
    private String sender_id;
    private Long to;
    private String message;
}

