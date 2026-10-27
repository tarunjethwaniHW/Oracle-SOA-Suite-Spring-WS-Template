package com.freddiemac.underwriting.endpoint;

import com.freddiemac.underwriting.types.UnderwritingRequest;
import com.freddiemac.underwriting.types.UnderwritingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Spring-WS Endpoint for Underwriting Service.
 *
 * RECEIVES SOAP calls FROM Oracle SOA Suite for loan underwriting.
 * Calculates DTI (Debt-to-Income) and LTV (Loan-to-Value) ratios.
 *
 * WSDL Contract: loan-service-soa/UnderwritingProcess.wsdl
 * Operation: processUnderwriting
 */
@Endpoint
public class UnderwritingEndpoint {

    private static final Logger log = LoggerFactory.getLogger(UnderwritingEndpoint.class);
    private static final String NAMESPACE_URI = "http://xmlns.oracle.com/LoanOrchestration/types";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UnderwritingRequest")
    @ResponsePayload
    public UnderwritingResponse processUnderwriting(@RequestPayload UnderwritingRequest request) {
        log.info("[UnderwritingEndpoint] Received underwriting request from SOA Suite");
        log.info("[UnderwritingEndpoint] Credit Score: {}", request.getCreditScore());
        log.info("[UnderwritingEndpoint] Loan Amount: ${}", request.getLoanAmount());

        // Calculate DTI (assuming loan payment is ~0.6% of loan amount monthly)
        double monthlyPayment = request.getLoanAmount() * 0.006;
        double dti = (monthlyPayment / request.getMonthlyIncome()) * 100;
        dti = Math.round(dti * 100.0) / 100.0;

        // Calculate LTV
        double ltv = (request.getLoanAmount() / request.getPropertyValue()) * 100;
        ltv = Math.round(ltv * 100.0) / 100.0;

        // Determine decision
        String decision;
        String notes;
        if (request.getCreditScore() >= 700 && dti <= 43 && ltv <= 80) {
            decision = "AUTO_APPROVED";
            notes = "Excellent credit, acceptable DTI and LTV ratios";
        } else if (request.getCreditScore() >= 620 && dti <= 50 && ltv <= 95) {
            decision = "MANUAL_REVIEW";
            notes = "Requires manual underwriter review";
        } else {
            decision = "AUTO_REJECTED";
            notes = "Does not meet minimum underwriting criteria";
        }

        log.info("[UnderwritingEndpoint] Decision: {}, DTI: {}%, LTV: {}%", decision, dti, ltv);

        return new UnderwritingResponse(decision, notes, dti, ltv);
    }
}
