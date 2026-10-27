package com.freddiemac.disbursement.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "status", "message" })
@XmlRootElement(name = "DisbursementResponse", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class DisbursementResponse {

    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String message;

    public DisbursementResponse() {}

    public DisbursementResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
