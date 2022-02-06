package edu.esoft.sdp.cw.pickandgoapi.enums;

public enum ActiveStatus {

    IsActive(1),
    IsInActive(0);

    public final int status;

    ActiveStatus(int status) {
        this.status = status;
    }

}
