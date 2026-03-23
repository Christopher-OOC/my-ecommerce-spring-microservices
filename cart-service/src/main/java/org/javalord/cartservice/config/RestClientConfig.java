package org.javalord.cartservice.config;

import org.javalord.cartservice.client.ProductRestClientService;
import org.javalord.cartservice.client.UserRestClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${service.product.url}")
    private String productServiceUrl;

    @Value("${service.user.url}")
    private String userServiceUrl;

    @Bean
    public ProductRestClientService productRestClientService() {
        RestClient restClient = RestClient
                .builder()
                .baseUrl(productServiceUrl)
                .requestInterceptor(new AuthInterceptor())
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return factory.createClient(ProductRestClientService.class);
    }

    @Bean
    public UserRestClientService userRestClientService() {
        RestClient restClient = RestClient
                .builder()
                .baseUrl(userServiceUrl)
                .requestInterceptor(new AuthInterceptor())
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return factory.createClient(UserRestClientService.class);
    }
}
