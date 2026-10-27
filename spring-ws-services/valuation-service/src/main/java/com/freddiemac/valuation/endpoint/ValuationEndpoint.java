package com.freddiemac.valuation.endpoint;

import com.freddiemac.valuation.types.ValuationRequest;
import com.freddiemac.valuation.types.ValuationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Spring-WS Endpoint for Valuation Service.
 *
 * RECEIVES SOAP calls FROM Oracle SOA Suite for property appraisal.
 * Replaces JAX-WS @WebService pattern with @Endpoint + @PayloadRoot.
 *
 * WSDL Contract: loan-service-soa/ValuationProcess.wsdl
 * Operation: appraise
 */
@Endpoint
public class ValuationEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ValuationEndpoint.class);
    private static final String NAMESPACE_URI = "http://xmlns.oracle.com/LoanOrchestration/types";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValuationRequest")
    @ResponsePayload
    public ValuationResponse appraise(@RequestPayload ValuationRequest request) {
        log.info("[ValuationEndpoint] Received appraisal request from SOA Suite");
        log.info("[ValuationEndpoint] Property: {}", request.getPropertyAddress());
        log.info("[ValuationEndpoint] Estimated Value: ${}", request.getEstimatedValue());

        // Simulate property appraisal (typically +/- 10% of estimated value)
        double variance = (Math.random() - 0.5) * 0.2; // -10% to +10%
        double appraisedValue = request.getEstimatedValue() * (1 + variance);
        appraisedValue = Math.round(appraisedValue * 100.0) / 100.0;

        String status = appraisedValue >= request.getEstimatedValue() * 0.9 ? "APPROVED" : "BELOW_ESTIMATE";

        log.info("[ValuationEndpoint] Appraised Value: ${}, Status: {}", appraisedValue, status);

        return new ValuationResponse(appraisedValue, status);
    }
}
