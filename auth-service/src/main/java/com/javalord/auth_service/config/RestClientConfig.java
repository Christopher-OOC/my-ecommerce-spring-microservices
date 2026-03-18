package com.javalord.auth_service.config;

import com.javalord.auth_service.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${service.user.url}")
    private String baseUrl;

    @Value("${app.internal.key}")
    private  String internalKey;

    @Bean
    public UserServiceClient userServiceClient() {
        RestClient restClient = RestClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader("Internal-Api-Key",  internalKey)
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory serviceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return serviceProxyFactory.createClient(UserServiceClient.class);
    }



}
