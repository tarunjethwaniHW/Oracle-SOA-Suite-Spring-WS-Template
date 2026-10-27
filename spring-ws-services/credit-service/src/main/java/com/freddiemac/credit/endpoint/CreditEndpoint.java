package com.freddiemac.credit.endpoint;

import com.freddiemac.credit.types.CreditScoreRequest;
import com.freddiemac.credit.types.CreditScoreResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Spring-WS Endpoint for Credit Service.
 *
 * RECEIVES SOAP calls FROM Oracle SOA Suite (downstream pattern).
 *
 * COMPARISON WITH JAX-WS @WebService:
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  JAX-WS @WebService Pattern                                             │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │  @WebService(                                                           │
 * │      serviceName = "CreditProcess",      ← For parser detection         │
 * │      portName = "CreditProcessPort",                                    │
 * │      targetNamespace = "..."                                            │
 * │  )                                                                      │
 * │  public class CreditProcessImpl {                                       │
 * │      @WebMethod                                                         │
 * │      public CreditScoreResponse processCreditCheck(CreditScoreRequest)  │
 * │  }                                                                      │
 * │                                                                         │
 * │  Parser extracts: serviceName="CreditProcess" from @WebService          │
 * │  Stores in: inheritsFrom field for linker matching                      │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  Spring-WS @Endpoint Pattern (This class)                               │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │  @Endpoint                               ← Marks as SOAP endpoint       │
 * │  public class CreditEndpoint {                                          │
 * │      @PayloadRoot(                       ← Routes by XML element        │
 * │          localPart = "CreditScoreRequest",  ← Request element name      │
 * │          namespace = "http://..."           ← Namespace                 │
 * │      )                                                                  │
 * │      @ResponsePayload                    ← Return value is SOAP body    │
 * │      public CreditScoreResponse processCreditCheck(                     │
 * │          @RequestPayload CreditScoreRequest request                     │
 * │      )                                                                  │
 * │  }                                                                      │
 * │                                                                         │
 * │  Parser would extract: localPart="CreditScoreRequest" from @PayloadRoot │
 * │  Linker would match: localPart vs WSDL message element name             │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * NOTE: The parser currently does NOT detect @Endpoint/@PayloadRoot pattern.
 *       This is a gap that would need to be implemented for full Spring-WS support.
 *
 * WSDL Contract: loan-service-soa/CreditProcess.wsdl
 * Operation: processCreditCheck
 */
@Endpoint
public class CreditEndpoint {

    private static final Logger log = LoggerFactory.getLogger(CreditEndpoint.class);

    private static final String NAMESPACE_URI = "http://xmlns.oracle.com/LoanOrchestration/types";

    /**
     * Process credit check request from SOA Suite.
     *
     * The @PayloadRoot annotation routes SOAP requests based on:
     * - localPart: The XML element name in the SOAP body (CreditScoreRequest)
     * - namespace: The XML namespace of that element
     *
     * When SOA Suite calls this service, it sends:
     * <soap:Body>
     *   <CreditScoreRequest xmlns="http://xmlns.oracle.com/LoanOrchestration/types">
     *     <ssn>123-45-6789</ssn>
     *   </CreditScoreRequest>
     * </soap:Body>
     *
     * Spring-WS matches the request to this method via @PayloadRoot.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreditScoreRequest")
    @ResponsePayload
    public CreditScoreResponse processCreditCheck(@RequestPayload CreditScoreRequest request) {
        log.info("[CreditEndpoint] Received credit check request from SOA Suite");
        log.info("[CreditEndpoint] SSN: {}", maskSsn(request.getSsn()));

        // Simulate credit score lookup
        int creditScore = calculateCreditScore(request.getSsn());
        String creditStatus = determineCreditStatus(creditScore);

        log.info("[CreditEndpoint] Credit Score: {}, Status: {}", creditScore, creditStatus);

        return new CreditScoreResponse(creditScore, creditStatus);
    }

    private int calculateCreditScore(String ssn) {
        // Simulated credit score based on SSN pattern
        // In real system, this would call credit bureau APIs
        if (ssn != null && ssn.startsWith("999")) {
            return 450; // Poor credit for testing
        } else if (ssn != null && ssn.startsWith("888")) {
            return 620; // Fair credit
        } else {
            return 750; // Good credit (default)
        }
    }

    private String determineCreditStatus(int score) {
        if (score >= 700) {
            return "EXCELLENT";
        } else if (score >= 650) {
            return "GOOD";
        } else if (score >= 600) {
            return "FAIR";
        } else {
            return "POOR";
        }
    }

    private String maskSsn(String ssn) {
        if (ssn == null || ssn.length() < 4) {
            return "***";
        }
        return "***-**-" + ssn.substring(ssn.length() - 4);
    }
}
