package com.freddiemac.client;

import com.freddiemac.client.service.LoanApprovalSoapClient;
import com.freddiemac.client.types.LoanApplicationRequest;
import com.freddiemac.client.types.LoanApplicationResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot Application demonstrating WebServiceTemplate SOAP client.
 *
 * This replaces the JAX-WS client from SOA_Suite_Final_Ex1-main.
 *
 * COMPARISON:
 *
 * Original (JAX-WS):
 *   LoanApprovalClient client = new LoanApprovalClient();
 *   LoanApplicationResponse response = client.initiateLoan(request);
 *   // Uses: port.initiateLoan(request) internally
 *
 * This project (WebServiceTemplate):
 *   LoanApprovalSoapClient client = ...
 *   LoanApplicationResponse response = client.initiateLoan(request);
 *   // Uses: webServiceTemplate.marshalSendAndReceive(request) internally
 */
@SpringBootApplication
public class LoanApprovalClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApprovalClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(LoanApprovalSoapClient soapClient) {
        return args -> {
            System.out.println("=========================================================================");
            System.out.println("     LOAN APPROVAL CLIENT - Spring-WS WebServiceTemplate");
            System.out.println("=========================================================================");
            System.out.println("Pattern: WebServiceTemplate.marshalSendAndReceive()");
            System.out.println("Target: http://localhost:8081/soap/LoanApprovalProcess");
            System.out.println("WSDL: loan-service-soa/LoanApprovalProcess.wsdl");
            System.out.println("=========================================================================");

            try {
                // Test Case 1: Standard loan application (should be AUTO_APPROVED)
                System.out.println("\n--- TEST CASE 1: Standard Application (Expected: AUTO_APPROVED) ---");
                LoanApplicationRequest request1 = new LoanApplicationRequest(
                    "John Doe",           // applicantName
                    "123-45-6789",        // ssn
                    250000.0,             // loanAmount
                    12000.0,              // monthlyIncome
                    "123 Main St, VA",    // propertyAddress
                    400000.0              // propertyValue
                );

                LoanApplicationResponse response1 = soapClient.initiateLoan(request1);
                printResponse(response1);

                // Test Case 2: Low credit score (should be AUTO_REJECTED)
                System.out.println("\n--- TEST CASE 2: Low Credit Score (Expected: AUTO_REJECTED) ---");
                LoanApplicationRequest request2 = new LoanApplicationRequest(
                    "Jane Smith",
                    "999-12-3456",        // SSN starting with 999 triggers low credit
                    250000.0,
                    12000.0,
                    "456 Oak Ave, MD",
                    400000.0
                );

                LoanApplicationResponse response2 = soapClient.initiateLoan(request2);
                printResponse(response2);

            } catch (Exception e) {
                System.err.println("[ERROR] " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\n=========================================================================");
        };
    }

    private static void printResponse(LoanApplicationResponse response) {
        System.out.println("[RESPONSE] Loan ID: " + response.getLoanId());
        System.out.println("[RESPONSE] Status: " + response.getStatus());
        System.out.println("[RESPONSE] Decision: " + response.getDecision());
        System.out.println("[RESPONSE] Notes: " + response.getDecisionNotes());
    }
}
