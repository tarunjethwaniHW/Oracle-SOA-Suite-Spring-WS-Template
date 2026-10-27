package com.freddiemac.credit.types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * JAXB-annotated response type for CreditProcess SOAP service.
 *
 * Namespace matches WSDL: http://xmlns.oracle.com/LoanOrchestration/types
 * Element name: CreditScoreResponse
 *
 * This is the response sent back to Oracle SOA Suite
 * from the processCreditCheck operation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "creditScore", "creditStatus" })
@XmlRootElement(name = "CreditScoreResponse", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class CreditScoreResponse {

    @XmlElement(required = true)
    private int creditScore;

    @XmlElement(required = true)
    private String creditStatus;

    public CreditScoreResponse() {}

    public CreditScoreResponse(int creditScore, String creditStatus) {
        this.creditScore = creditScore;
        this.creditStatus = creditStatus;
    }

    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }

    public String getCreditStatus() { return creditStatus; }
    public void setCreditStatus(String creditStatus) { this.creditStatus = creditStatus; }

    @Override
    public String toString() {
        return "CreditScoreResponse{creditScore=" + creditScore + ", creditStatus='" + creditStatus + "'}";
    }
}
