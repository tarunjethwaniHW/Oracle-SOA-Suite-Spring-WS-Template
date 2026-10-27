package com.freddiemac.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application for Credit Service.
 *
 * This is a Spring-WS endpoint service that RECEIVES SOAP calls
 * from Oracle SOA Suite (downstream pattern).
 *
 * PATTERN COMPARISON:
 *
 * JAX-WS @WebService (Original):
 *   - Deployed to application server (Tomcat, WebLogic)
 *   - Uses Metro/CXF runtime
 *   - @WebService annotation on implementation class
 *   - Parser detects: @WebService(serviceName="...")
 *
 * Spring-WS @Endpoint (This project):
 *   - Embedded in Spring Boot application
 *   - Uses Spring-WS runtime
 *   - @Endpoint annotation on handler class
 *   - Parser would detect: @PayloadRoot(localPart="...")
 *
 * WSDL: http://localhost:8082/ws/CreditProcess.wsdl
 * Endpoint: http://localhost:8082/ws/credit
 */
@SpringBootApplication
public class CreditServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditServiceApplication.class, args);
    }
}
