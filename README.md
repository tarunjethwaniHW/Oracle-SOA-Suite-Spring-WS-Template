# SOA WebServiceTemplate Example

This project demonstrates **Spring-WS (WebServiceTemplate)** integration with Oracle SOA Suite,
replacing the traditional JAX-WS approach from `SOA_Suite_Final_Ex1-main`.

## Architecture Comparison

| Direction | Original (JAX-WS) | This Project (Spring-WS) |
|-----------|------------------|--------------------------|
| **Upstream (Client → SOA)** | `port.initiateLoan()` via `Service.getPort()` | `WebServiceTemplate.marshalSendAndReceive()` |
| **Downstream (SOA → Java)** | `@WebService` annotation | `@Endpoint` with `@PayloadRoot` |

## Key Difference: WebServiceTemplate vs @Endpoint

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  UPSTREAM: Java Client → Oracle SOA Suite                                    │
│  Uses: WebServiceTemplate (SOAP Client)                                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  spring-ws-client/                                                           │
│  └── LoanApprovalSoapClient.java                                             │
│      │                                                                       │
│      │  webServiceTemplate.marshalSendAndReceive(                            │
│      │      "http://soa-server/soa-infra/services/.../LoanApprovalProcess",  │
│      │      loanRequest                                                      │
│      │  );                                                                   │
│      │                                                                       │
│      ▼                                                                       │
│  Oracle SOA Suite (composite.xml → BPEL)                                     │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│  DOWNSTREAM: Oracle SOA Suite → Java Service                                 │
│  Uses: @Endpoint with @PayloadRoot (SOAP Server)                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Oracle SOA Suite (BPEL <invoke>)                                            │
│      │                                                                       │
│      │  HTTP POST to http://localhost:8082/ws/credit                         │
│      ▼                                                                       │
│  spring-ws-services/credit-service/                                          │
│  └── CreditEndpoint.java                                                     │
│      │                                                                       │
│      │  @Endpoint                                                            │
│      │  public class CreditEndpoint {                                        │
│      │      @PayloadRoot(namespace = "...", localPart = "CreditScoreRequest")│
│      │      @ResponsePayload                                                 │
│      │      public CreditScoreResponse processCreditCheck(                   │
│      │          @RequestPayload CreditScoreRequest request) {                │
│      │          // Business logic                                            │
│      │      }                                                                │
│      │  }                                                                    │
│      │                                                                       │
│      ▼                                                                       │
│  Returns SOAP Response to SOA Suite                                          │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Project Structure

```
SOA_WebServiceTemplate_Ex1/
├── README.md
│
├── loan-service-soa/                    # SOA Artifacts (unchanged from original)
│   ├── composite.xml                    # SOA Composite definition
│   ├── LoanApprovalProcess.bpel         # BPEL orchestration
│   ├── LoanApprovalProcess.wsdl         # Entry point WSDL
│   └── LoanWorkflow.xsd                 # Shared XSD types
│
├── soa-credit-composite/                # Credit service WSDL
│   └── CreditProcess.wsdl
├── soa-valuation-composite/             # Valuation service WSDL
│   └── ValuationProcess.wsdl
├── soa-underwriting-composite/          # Underwriting service WSDL
│   └── UnderwritingProcess.wsdl
├── soa-disbursement-composite/          # Disbursement service WSDL
│   └── DisbursementProcess.wsdl
├── soa-notification-composite/          # Notification service WSDL
│   └── NotificationProcess.wsdl
│
├── spring-ws-client/                    # UPSTREAM: WebServiceTemplate Client (port 8080)
│   ├── pom.xml
│   └── src/main/java/com/freddiemac/client/
│       ├── LoanApprovalClientApplication.java  # Main application
│       ├── config/
│       │   └── SoapClientConfig.java           # WebServiceTemplate configuration
│       ├── service/
│       │   └── LoanApprovalSoapClient.java     # Client using WebServiceTemplate
│       └── types/                              # JAXB Request/Response POJOs
│           ├── LoanApplicationRequest.java
│           └── LoanApplicationResponse.java
│
└── spring-ws-services/                  # DOWNSTREAM: @Endpoint Services
    ├── credit-service/                  # Port 8082
    │   ├── pom.xml
    │   └── src/main/java/com/freddiemac/credit/
    │       ├── CreditServiceApplication.java
    │       ├── config/WebServiceConfig.java
    │       ├── endpoint/CreditEndpoint.java     # @PayloadRoot("CreditScoreRequest")
    │       └── types/{CreditScoreRequest,CreditScoreResponse}.java
    │
    ├── valuation-service/               # Port 8083
    │   └── src/main/java/com/freddiemac/valuation/
    │       ├── ValuationServiceApplication.java
    │       ├── config/WebServiceConfig.java
    │       ├── endpoint/ValuationEndpoint.java  # @PayloadRoot("ValuationRequest")
    │       └── types/{ValuationRequest,ValuationResponse}.java
    │
    ├── underwriting-service/            # Port 8084
    │   └── src/main/java/com/freddiemac/underwriting/
    │       ├── UnderwritingServiceApplication.java
    │       ├── config/WebServiceConfig.java
    │       ├── endpoint/UnderwritingEndpoint.java  # @PayloadRoot("UnderwritingRequest")
    │       └── types/{UnderwritingRequest,UnderwritingResponse}.java
    │
    ├── disbursement-service/            # Port 8085
    │   └── src/main/java/com/freddiemac/disbursement/
    │       ├── DisbursementServiceApplication.java
    │       ├── config/WebServiceConfig.java
    │       ├── endpoint/DisbursementEndpoint.java  # @PayloadRoot("DisbursementRequest")
    │       └── types/{DisbursementRequest,DisbursementResponse}.java
    │
    └── notification-service/            # Port 8086
        └── src/main/java/com/freddiemac/notification/
            ├── NotificationServiceApplication.java
            ├── config/WebServiceConfig.java
            ├── endpoint/NotificationEndpoint.java  # @PayloadRoot("NotificationRequest")
            └── types/{NotificationRequest,NotificationResponse}.java
```

## Why This Matters for Parser/Linker

### JAX-WS Pattern (Linkable)
```java
// Callee: calleeClass="LoanApprovalProcessPort", calleeFunction="initiateLoan"
// Links to WSDL via: strip "Port" suffix → match className
LoanApprovalProcessPort port = service.getPort(LoanApprovalProcessPort.class);
port.initiateLoan(request);
```

### WebServiceTemplate Pattern (Not Directly Linkable)
```java
// Callee: calleeClass="WebServiceTemplate", calleeFunction="marshalSendAndReceive"
// Generic names - cannot link to specific WSDL operation
webServiceTemplate.marshalSendAndReceive(request);
```

### @Endpoint Pattern (Linkable via namespace + localPart)
```java
// Can link via @PayloadRoot namespace + localPart → WSDL namespace + element
@PayloadRoot(namespace = "http://xmlns.oracle.com/.../CreditProcess", 
             localPart = "CreditScoreRequest")
public CreditScoreResponse processCreditCheck(@RequestPayload CreditScoreRequest request)
```

## Running the Project

### 1. Start All Downstream Services
```bash
# Terminal 1 - Credit Service
cd spring-ws-services/credit-service
mvn spring-boot:run
# WSDL: http://localhost:8082/ws/CreditProcess.wsdl

# Terminal 2 - Valuation Service
cd spring-ws-services/valuation-service
mvn spring-boot:run
# WSDL: http://localhost:8083/ws/ValuationProcess.wsdl

# Terminal 3 - Underwriting Service
cd spring-ws-services/underwriting-service
mvn spring-boot:run
# WSDL: http://localhost:8084/ws/UnderwritingProcess.wsdl

# Terminal 4 - Disbursement Service
cd spring-ws-services/disbursement-service
mvn spring-boot:run
# WSDL: http://localhost:8085/ws/DisbursementProcess.wsdl

# Terminal 5 - Notification Service
cd spring-ws-services/notification-service
mvn spring-boot:run
# WSDL: http://localhost:8086/ws/NotificationProcess.wsdl
```

### 2. Run Client (Upstream)
```bash
cd spring-ws-client
mvn spring-boot:run
# Sends SOAP request to Oracle SOA Suite at http://localhost:8081/soap/LoanApprovalProcess
```

### Service Ports Summary
| Service | Port | WSDL URL |
|---------|------|----------|
| Client (Upstream) | 8080 | N/A (client only) |
| Credit Service | 8082 | http://localhost:8082/ws/CreditProcess.wsdl |
| Valuation Service | 8083 | http://localhost:8083/ws/ValuationProcess.wsdl |
| Underwriting Service | 8084 | http://localhost:8084/ws/UnderwritingProcess.wsdl |
| Disbursement Service | 8085 | http://localhost:8085/ws/DisbursementProcess.wsdl |
| Notification Service | 8086 | http://localhost:8086/ws/NotificationProcess.wsdl |

## WSDL Contract Validation

Both client and server use WSDL-based validation:

**Client (WebServiceTemplate):**
- `PayloadValidatingInterceptor` validates request/response against XSD schema
- Schema extracted from WSDL at startup

**Server (@Endpoint):**
- `PayloadValidatingInterceptor` validates incoming requests
- `Wsdl11Definition` bean exposes WSDL for consumers
