package com.example.monitor.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class TokenProvider implements AuthenticationProvider {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER  = Logger.getLogger(TokenProvider.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if(token == null) {
            return null;
        }
        boolean isValid = isValid(token);
        if(isValid) {
            authentication.setAuthenticated(isValid(token));
            return authentication;
        } else {
            throw  new BadCredentialsException("Token is nov valid");
        }
    }



    private boolean isValid(String token) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/token/check");
        StringEntity body = null;
        try {
            body = new StringEntity("{ \"token\":\"" + token + "\"}");
            post.setEntity(body);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(post);
            JsonNode node = OBJECT_MAPPER.readTree(response.getEntity().getContent());
            return node.get("valid").asBoolean();
        } catch (IOException e) {
            LOGGER.error("Error occurred during token retrieval");
            return false;
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == TokenAuthentication.class;
    }
}
