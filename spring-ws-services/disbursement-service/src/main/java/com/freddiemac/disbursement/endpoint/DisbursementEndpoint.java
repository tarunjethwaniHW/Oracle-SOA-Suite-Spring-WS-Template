package com.freddiemac.disbursement.endpoint;

import com.freddiemac.disbursement.types.DisbursementRequest;
import com.freddiemac.disbursement.types.DisbursementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Spring-WS Endpoint for Disbursement Service.
 *
 * RECEIVES SOAP calls FROM Oracle SOA Suite to process loan disbursement.
 * Called only when loan is approved.
 *
 * WSDL Contract: loan-service-soa/DisbursementProcess.wsdl
 * Operation: processDisbursement
 */
@Endpoint
public class DisbursementEndpoint {

    private static final Logger log = LoggerFactory.getLogger(DisbursementEndpoint.class);
    private static final String NAMESPACE_URI = "http://xmlns.oracle.com/LoanOrchestration/types";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DisbursementRequest")
    @ResponsePayload
    public DisbursementResponse processDisbursement(@RequestPayload DisbursementRequest request) {
        log.info("[DisbursementEndpoint] Received disbursement request from SOA Suite");
        log.info("[DisbursementEndpoint] Loan ID: {}", request.getLoanId());
        log.info("[DisbursementEndpoint] Amount: ${}", request.getLoanAmount());

        // Simulate disbursement processing
        String status = "DISBURSED";
        String message = String.format("Loan %d disbursed successfully. Amount: $%.2f transferred to escrow.",
            request.getLoanId(), request.getLoanAmount());

        log.info("[DisbursementEndpoint] Status: {}", status);

        return new DisbursementResponse(status, message);
    }
}
