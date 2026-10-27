package com.freddiemac.credit.types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * JAXB-annotated request type for CreditProcess SOAP service.
 *
 * Namespace matches WSDL: http://xmlns.oracle.com/LoanOrchestration/types
 * Element name: CreditScoreRequest
 *
 * This is the request that Oracle SOA Suite sends to this endpoint
 * when calling the processCreditCheck operation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "ssn" })
@XmlRootElement(name = "CreditScoreRequest", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class CreditScoreRequest {

    @XmlElement(required = true)
    private String ssn;

    public CreditScoreRequest() {}

    public CreditScoreRequest(String ssn) {
        this.ssn = ssn;
    }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    @Override
    public String toString() {
        return "CreditScoreRequest{ssn='" + ssn + "'}";
    }
}
