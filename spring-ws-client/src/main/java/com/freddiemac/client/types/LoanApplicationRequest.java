package com.freddiemac.client.types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * JAXB-annotated request type for LoanApprovalProcess SOAP service.
 *
 * Namespace matches WSDL: http://xmlns.oracle.com/LoanOrchestration/types
 * Element name: LoanApplicationRequest
 *
 * Used by WebServiceTemplate.marshalSendAndReceive() to serialize
 * this object to XML when calling Oracle SOA Suite.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "applicantName",
    "ssn",
    "loanAmount",
    "monthlyIncome",
    "propertyAddress",
    "propertyValue"
})
@XmlRootElement(name = "LoanApplicationRequest", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class LoanApplicationRequest {

    @XmlElement(required = true)
    private String applicantName;

    @XmlElement(required = true)
    private String ssn;

    @XmlElement(required = true)
    private double loanAmount;

    @XmlElement(required = true)
    private double monthlyIncome;

    @XmlElement(required = true)
    private String propertyAddress;

    @XmlElement(required = true)
    private double propertyValue;

    public LoanApplicationRequest() {}

    public LoanApplicationRequest(String applicantName, String ssn, double loanAmount,
                                   double monthlyIncome, String propertyAddress, double propertyValue) {
        this.applicantName = applicantName;
        this.ssn = ssn;
        this.loanAmount = loanAmount;
        this.monthlyIncome = monthlyIncome;
        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
    }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }

    public double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(double monthlyIncome) { this.monthlyIncome = monthlyIncome; }

    public String getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    public double getPropertyValue() { return propertyValue; }
    public void setPropertyValue(double propertyValue) { this.propertyValue = propertyValue; }

    @Override
    public String toString() {
        return "LoanApplicationRequest{" +
                "applicantName='" + applicantName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", loanAmount=" + loanAmount +
                ", monthlyIncome=" + monthlyIncome +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", propertyValue=" + propertyValue +
                '}';
    }
}
