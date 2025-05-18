package com.sanjib.edureka.ms_customer_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig
{

    @Autowired
    EurekaDiscoveryClient discoveryClient;

    @Bean
    public WebClient authValidateWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl("http://auth-service/api/v1/validate")
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient authValidateWebClientEurekaDiscovered(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("auth-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for auth-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/validate", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient profileNotifyContractorsEurekaBalanced(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("profile-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for profile-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/get/contractors/all", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }
    
    @Bean
    @Scope(value = "prototype")
    WebClient authUseIdFromTokenWebClientEureka(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("auth-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for auth-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/loggedinuser", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }
    
    @Bean
    @Scope(value = "prototype")
    WebClient productFetchNameWebClientEureka(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("catalog-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for catalog-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/product", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }
    
    
    @Bean
    @Scope(value = "prototype")
    WebClient productFetchWebClientEureka(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("catalog-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for catalog-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/productbyid", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }

}
