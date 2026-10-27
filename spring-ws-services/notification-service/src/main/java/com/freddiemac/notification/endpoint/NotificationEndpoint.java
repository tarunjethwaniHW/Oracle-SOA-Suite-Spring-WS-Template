package com.freddiemac.notification.endpoint;

import com.freddiemac.notification.types.NotificationRequest;
import com.freddiemac.notification.types.NotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Spring-WS Endpoint for Notification Service.
 *
 * RECEIVES SOAP calls FROM Oracle SOA Suite to send loan decision notifications.
 * Called at the end of the loan workflow regardless of approval/rejection.
 *
 * WSDL Contract: loan-service-soa/NotificationProcess.wsdl
 * Operation: processNotification
 */
@Endpoint
public class NotificationEndpoint {

    private static final Logger log = LoggerFactory.getLogger(NotificationEndpoint.class);
    private static final String NAMESPACE_URI = "http://xmlns.oracle.com/LoanOrchestration/types";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "NotificationRequest")
    @ResponsePayload
    public NotificationResponse processNotification(@RequestPayload NotificationRequest request) {
        log.info("[NotificationEndpoint] Received notification request from SOA Suite");
        log.info("[NotificationEndpoint] Loan ID: {}", request.getLoanId());
        log.info("[NotificationEndpoint] Applicant: {}", request.getApplicantName());
        log.info("[NotificationEndpoint] Decision: {}", request.getDecision());

        // Simulate notification sending (email, SMS, etc.)
        String status = "SENT";
        String message = String.format(
            "Notification sent to %s regarding loan %d. Decision: %s",
            request.getApplicantName(),
            request.getLoanId(),
            request.getDecision()
        );

        log.info("[NotificationEndpoint] Notification sent successfully");

        return new NotificationResponse(status, message);
    }
}
