package com.freddiemac.underwriting.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "creditScore", "loanAmount", "monthlyIncome", "propertyValue" })
@XmlRootElement(name = "UnderwritingRequest", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class UnderwritingRequest {

    @XmlElement(required = true)
    private int creditScore;
    @XmlElement(required = true)
    private double loanAmount;
    @XmlElement(required = true)
    private double monthlyIncome;
    @XmlElement(required = true)
    private double propertyValue;

    public UnderwritingRequest() {}

    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
    public double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public double getPropertyValue() { return propertyValue; }
    public void setPropertyValue(double propertyValue) { this.propertyValue = propertyValue; }
}
