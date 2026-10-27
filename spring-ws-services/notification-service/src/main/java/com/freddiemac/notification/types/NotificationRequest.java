package com.freddiemac.notification.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "loanId", "applicantName", "decision" })
@XmlRootElement(name = "NotificationRequest", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class NotificationRequest {

    @XmlElement(required = true)
    private long loanId;
    @XmlElement(required = true)
    private String applicantName;
    @XmlElement(required = true)
    private String decision;

    public NotificationRequest() {}

    public long getLoanId() { return loanId; }
    public void setLoanId(long loanId) { this.loanId = loanId; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
}
