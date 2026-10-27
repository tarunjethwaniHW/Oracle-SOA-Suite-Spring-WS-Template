package com.freddiemac.disbursement.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "loanId", "loanAmount" })
@XmlRootElement(name = "DisbursementRequest", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class DisbursementRequest {

    @XmlElement(required = true)
    private long loanId;
    @XmlElement(required = true)
    private double loanAmount;

    public DisbursementRequest() {}

    public long getLoanId() { return loanId; }
    public void setLoanId(long loanId) { this.loanId = loanId; }
    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
}
