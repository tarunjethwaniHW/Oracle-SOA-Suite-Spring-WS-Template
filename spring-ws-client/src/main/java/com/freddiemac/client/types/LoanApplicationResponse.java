package com.freddiemac.client.types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * JAXB-annotated response type for LoanApprovalProcess SOAP service.
 *
 * Namespace matches WSDL: http://xmlns.oracle.com/LoanOrchestration/types
 * Element name: LoanApplicationResponse
 *
 * Used by WebServiceTemplate.marshalSendAndReceive() to deserialize
 * the SOAP response from Oracle SOA Suite.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "loanId",
    "status",
    "decision",
    "decisionNotes"
})
@XmlRootElement(name = "LoanApplicationResponse", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class LoanApplicationResponse {

    @XmlElement(required = true)
    private long loanId;

    @XmlElement(required = true)
    private String status;

    @XmlElement(required = true)
    private String decision;

    @XmlElement(required = true)
    private String decisionNotes;

    public LoanApplicationResponse() {}

    public LoanApplicationResponse(long loanId, String status, String decision, String decisionNotes) {
        this.loanId = loanId;
        this.status = status;
        this.decision = decision;
        this.decisionNotes = decisionNotes;
    }

    public long getLoanId() { return loanId; }
    public void setLoanId(long loanId) { this.loanId = loanId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }

    public String getDecisionNotes() { return decisionNotes; }
    public void setDecisionNotes(String decisionNotes) { this.decisionNotes = decisionNotes; }

    @Override
    public String toString() {
        return "LoanApplicationResponse{" +
                "loanId=" + loanId +
                ", status='" + status + '\'' +
                ", decision='" + decision + '\'' +
                ", decisionNotes='" + decisionNotes + '\'' +
                '}';
    }
}
