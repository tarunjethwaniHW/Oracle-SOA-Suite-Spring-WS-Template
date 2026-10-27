package com.freddiemac.valuation.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "propertyAddress", "estimatedValue" })
@XmlRootElement(name = "ValuationRequest", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class ValuationRequest {

    @XmlElement(required = true)
    private String propertyAddress;

    @XmlElement(required = true)
    private double estimatedValue;

    public ValuationRequest() {}

    public ValuationRequest(String propertyAddress, double estimatedValue) {
        this.propertyAddress = propertyAddress;
        this.estimatedValue = estimatedValue;
    }

    public String getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    public double getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(double estimatedValue) { this.estimatedValue = estimatedValue; }
}
