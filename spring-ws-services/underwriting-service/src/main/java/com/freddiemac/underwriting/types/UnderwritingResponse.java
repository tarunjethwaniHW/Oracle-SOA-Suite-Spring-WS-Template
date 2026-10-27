package com.freddiemac.underwriting.types;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "decision", "notes", "dti", "ltv" })
@XmlRootElement(name = "UnderwritingResponse", namespace = "http://xmlns.oracle.com/LoanOrchestration/types")
public class UnderwritingResponse {

    @XmlElement(required = true)
    private String decision;
    @XmlElement(required = true)
    private String notes;
    @XmlElement(required = true)
    private double dti;
    @XmlElement(required = true)
    private double ltv;

    public UnderwritingResponse() {}

    public UnderwritingResponse(String decision, String notes, double dti, double ltv) {
        this.decision = decision;
        this.notes = notes;
        this.dti = dti;
        this.ltv = ltv;
    }

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public double getDti() { return dti; }
    public void setDti(double dti) { this.dti = dti; }
    public double getLtv() { return ltv; }
    public void setLtv(double ltv) { this.ltv = ltv; }
}
