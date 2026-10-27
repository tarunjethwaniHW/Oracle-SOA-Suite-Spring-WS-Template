package com.freddiemac.valuation.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "appraisedValue", "status" })
@XmlRootElement(name = "ValuationResponse", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class ValuationResponse {

    @XmlElement(required = true)
    private double appraisedValue;

    @XmlElement(required = true)
    private String status;

    public ValuationResponse() {}

    public ValuationResponse(double appraisedValue, String status) {
        this.appraisedValue = appraisedValue;
        this.status = status;
    }

    public double getAppraisedValue() { return appraisedValue; }
    public void setAppraisedValue(double appraisedValue) { this.appraisedValue = appraisedValue; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
