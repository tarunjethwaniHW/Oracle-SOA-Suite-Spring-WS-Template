package com.freddiemac.client.service;

import com.freddiemac.client.types.LoanApplicationRequest;
import com.freddiemac.client.types.LoanApplicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

/**
 * Spring-WS SOAP Client for Oracle SOA Suite LoanApprovalProcess.
 *
 * COMPARISON WITH JAX-WS:
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  JAX-WS Pattern (Original SOA_Suite_Final_Ex1-main)                     │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │  Service service = Service.create(wsdlURL, serviceName);                │
 * │  LoanApprovalProcessPort port = service.getPort(...);                   │
 * │  LoanApplicationResponse response = port.initiateLoan(request);         │
 * │                                          ^^^^^^^^^^^^                   │
 * │                                          Operation name in code!        │
 * │                                                                         │
 * │  Parser Output:                                                         │
 * │  - calleeClass: "LoanApprovalProcessPort"                               │
 * │  - calleeFunction: "initiateLoan"                                       │
 * │  - Can link to WSDL via Port suffix stripping                           │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  WebServiceTemplate Pattern (This project)                              │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │  LoanApplicationResponse response = (LoanApplicationResponse)           │
 * │      webServiceTemplate.marshalSendAndReceive(request);                 │
 * │                         ^^^^^^^^^^^^^^^^^^^^^^                          │
 * │                         Generic method name!                            │
 * │                                                                         │
 * │  Parser Output:                                                         │
 * │  - calleeClass: "WebServiceTemplate"                                    │
 * │  - calleeFunction: "marshalSendAndReceive"                              │
 * │  - Cannot directly link to WSDL (generic names)                         │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * WSDL Contract: loan-service-soa/LoanApprovalProcess.wsdl
 * Operation: initiateLoan
 * Endpoint: http://localhost:8081/soap/LoanApprovalProcess
 */
@Service
public class LoanApprovalSoapClient {

    private static final Logger log = LoggerFactory.getLogger(LoanApprovalSoapClient.class);

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public LoanApprovalSoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    /**
     * Calls the SOA Suite LoanApprovalProcess.initiateLoan operation.
     *
     * This triggers the BPEL orchestration which calls:
     * - CreditProcess.processCreditCheck
     * - ValuationProcess.appraise
     * - UnderwritingProcess.processUnderwriting
     * - DisbursementProcess.processDisbursement (if approved)
     * - NotificationProcess.processNotification
     *
     * NOTE: The operation name "initiateLoan" is NOT in the code!
     * SOA Suite determines the operation from the XML element name (LoanApplicationRequest).
     *
     * @param request The loan application request
     * @return The loan application response from SOA Suite
     */
    public LoanApplicationResponse initiateLoan(LoanApplicationRequest request) {
        log.info("[LoanApprovalSoapClient] Calling SOA Suite via WebServiceTemplate");
        log.info("[LoanApprovalSoapClient] Applicant: {}", request.getApplicantName());
        log.info("[LoanApprovalSoapClient] Loan Amount: ${}", request.getLoanAmount());

        try {
            // WebServiceTemplate SOAP call
            // - Marshalls LoanApplicationRequest to XML
            // - Validates XML against WSDL schema (via PayloadValidatingInterceptor)
            // - Sends SOAP request to endpoint
            // - Receives SOAP response
            // - Validates response XML against WSDL schema
            // - Unmarshalls XML to LoanApplicationResponse
            LoanApplicationResponse response = (LoanApplicationResponse)
                webServiceTemplate.marshalSendAndReceive(request);

            log.info("[LoanApprovalSoapClient] Response received from SOA Suite");
            log.info("[LoanApprovalSoapClient] Loan ID: {}", response.getLoanId());
            log.info("[LoanApprovalSoapClient] Decision: {}", response.getDecision());

            return response;

        } catch (SoapFaultClientException e) {
            log.error("[LoanApprovalSoapClient] SOAP Fault: {}", e.getFaultStringOrReason());
            throw e;
        } catch (Exception e) {
            log.error("[LoanApprovalSoapClient] Error calling SOA Suite: {}", e.getMessage());
            throw new RuntimeException("Loan approval request failed", e);
        }
    }

    /**
     * Alternative method with explicit endpoint URI.
     * Useful when calling different environments (dev, test, prod).
     */
    public LoanApplicationResponse initiateLoan(String endpointUri, LoanApplicationRequest request) {
        log.info("[LoanApprovalSoapClient] Calling custom endpoint: {}", endpointUri);

        try {
            // marshalSendAndReceive with explicit URI
            LoanApplicationResponse response = (LoanApplicationResponse)
                webServiceTemplate.marshalSendAndReceive(endpointUri, request);

            return response;

        } catch (SoapFaultClientException e) {
            log.error("[LoanApprovalSoapClient] SOAP Fault: {}", e.getFaultStringOrReason());
            throw e;
        }
    }
}
