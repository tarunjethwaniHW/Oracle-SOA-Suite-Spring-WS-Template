package com.freddiemac.credit.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Spring-WS Endpoint Configuration for Credit Service.
 *
 * COMPARISON WITH JAX-WS:
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  JAX-WS Pattern (Original SOA_Suite_Final_Ex1-main)                     │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │  @WebService(                                                           │
 * │      serviceName = "CreditProcess",                                     │
 * │      portName = "CreditProcessPort",                                    │
 * │      targetNamespace = "http://xmlns.oracle.com/LoanOrchestration"      │
 * │  )                                                                      │
 * │  public class CreditProcessImpl {                                       │
 * │      public CreditScoreResponse processCreditCheck(CreditScoreRequest)  │
 * │  }                                                                      │
 * │                                                                         │
 * │  Parser detects: @WebService annotation with serviceName                │
 * │  Linker matches: inheritsFrom vs WSDL service name                      │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  Spring-WS Pattern (This project)                                       │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │  @Endpoint                                                              │
 * │  public class CreditEndpoint {                                          │
 * │      @PayloadRoot(                                                      │
 * │          localPart = "CreditScoreRequest",                              │
 * │          namespace = "http://xmlns.oracle.com/LoanOrchestration/types"  │
 * │      )                                                                  │
 * │      @ResponsePayload                                                   │
 * │      public CreditScoreResponse processCreditCheck(@RequestPayload ...) │
 * │  }                                                                      │
 * │                                                                         │
 * │  Parser would detect: @PayloadRoot annotation with localPart+namespace  │
 * │  Linker would match: localPart vs WSDL element name                     │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * WSDL Reference: loan-service-soa/CreditProcess.wsdl
 * Endpoint URL: http://localhost:8082/ws/credit
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * MessageDispatcherServlet routes SOAP requests to @Endpoint classes.
     * Maps all requests under /ws/* to the Spring-WS dispatcher.
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Exposes WSDL at: http://localhost:8082/ws/CreditProcess.wsdl
     *
     * This allows clients (SOA Suite) to discover the service contract.
     * The WSDL is auto-generated from XSD schema + configuration.
     */
    @Bean(name = "CreditProcess")
    public DefaultWsdl11Definition creditProcessWsdl(XsdSchema creditSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CreditProcessPort");
        definition.setServiceName("CreditProcess");
        definition.setLocationUri("/ws/credit");
        definition.setTargetNamespace("http://xmlns.oracle.com/LoanOrchestration");
        definition.setSchema(creditSchema);
        return definition;
    }

    /**
     * Load XSD schema for request/response validation.
     */
    @Bean
    public XsdSchema creditSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/LoanWorkflow.xsd"));
    }
}
