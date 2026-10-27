package com.freddiemac.underwriting.config;

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

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "UnderwritingProcess")
    public DefaultWsdl11Definition underwritingProcessWsdl(XsdSchema underwritingSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("UnderwritingProcessPort");
        definition.setServiceName("UnderwritingProcess");
        definition.setLocationUri("/ws/underwriting");
        definition.setTargetNamespace("http://xmlns.oracle.com/LoanOrchestration");
        definition.setSchema(underwritingSchema);
        return definition;
    }

    @Bean
    public XsdSchema underwritingSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/LoanWorkflow.xsd"));
    }
}
