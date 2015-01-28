/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.appmanager.integration.services.jaxrs.coffeesample;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.ext.RuntimeDelegate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CoffeeConfig {
    @Bean( destroyMethod = "shutdown" )
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    public org.apache.cxf.endpoint.Server jaxRsServer() {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
        factory.setServiceBeans( Arrays.< Object >asList(starbucksOutletService()) );
        factory.setAddress( "/" + factory.getAddress() );
		 List<Object> providers = new ArrayList<Object>();
        providers.add(jsonProvider());
        providers.add(new OrderReader());
        factory.setProviders(providers);
        //factory.setProviders( Arrays.< Object >asList( jsonProvider() ) );
        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public StarbucksOutletService starbucksOutletService() {
        return new StarbucksOutletServiceImpl();
    }



    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }
}
