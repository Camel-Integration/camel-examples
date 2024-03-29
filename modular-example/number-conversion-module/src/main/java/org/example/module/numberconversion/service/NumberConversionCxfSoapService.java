package org.example.module.numberconversion.service;

import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;

@Component
public class NumberConversionCxfSoapService {
    @Bean
    public CxfEndpoint convertNumber() {
        CxfEndpoint cxfEndpoint = new CxfEndpoint();
        cxfEndpoint.setDataFormat(DataFormat.PAYLOAD);
        cxfEndpoint.setAddress("https://www.dataaccess.com/webservicesserver/numberconversion.wso");
        cxfEndpoint.setWsdlURL("https://www.dataaccess.com/webservicesserver/numberconversion.wso?WSDL");
        cxfEndpoint.setPortNameAsQName(new QName("http://www.dataaccess.com/webservicesserver/", "NumberConversionSoap"));
        return cxfEndpoint;
    }
}
