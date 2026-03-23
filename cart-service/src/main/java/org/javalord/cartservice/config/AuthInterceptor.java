package org.javalord.cartservice.config;

import org.javalord.cartservice.util.AuthUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class AuthInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = AuthUtil.getToken();

        request.getHeaders().add("Authorization", "Bearer " + token);

        return execution.execute(request, body);
    }
}
