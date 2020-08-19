package com.example.monitor.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class AuthenticationProcessingFilter  extends AbstractAuthenticationProcessingFilter {
    private final static Logger LOGGER = Logger.getLogger(AuthenticationProcessingFilter.class);
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public AuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super("/**");
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(this::redirectToOriginalUrl);
        setAuthenticationFailureHandler(this::redirectToAccessDeniedPage);
    }


    private void redirectToAccessDeniedPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendRedirect("/denied.html");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String token = extractTokenFromHeaderAndCookie(httpServletRequest);
        String code = extractCode(httpServletRequest);
        if (token == null && code == null) {
            return SecurityContextHolder.getContext().getAuthentication();
        } else if (token == null) {
            token = requestToken(code);
        }
        TokenAuthentication authentication = new TokenAuthentication(token);
        return getAuthenticationManager().authenticate(authentication);
    }

    private String extractTokenFromHeaderAndCookie(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("Authentication");
    }

    private String requestToken(String code) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/token");
        StringEntity body = new StringEntity("{ \"code\":\"" + code + "\"}");
        post.setEntity(body);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = client.execute(post);
        JsonNode node = OBJECT_MAPPER.readTree(response.getEntity().getContent());
        String token = node.get("token").textValue();
        client.close();
        return token;
    }

    private String extractCode(HttpServletRequest httpServletRequest) {
        try {
            List<NameValuePair> queryParams = URLEncodedUtils.parse(new URI(httpServletRequest.getRequestURL().append("?").append(httpServletRequest.getQueryString()).toString()),
                    StandardCharsets.UTF_8);
            Optional<NameValuePair> codeParam = queryParams.stream().filter(v -> "code".equals(v.getName())).findFirst();
            if(codeParam.isPresent()) {
                return codeParam.get().getValue();
            }
        } catch (URISyntaxException e) {
            LOGGER.error("Can not extract code param from url");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
        getSuccessHandler().onAuthenticationSuccess(request,response,  authResult);

 }

    private void redirectToOriginalUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpServletRequest matchingRequest = requestCache.getMatchingRequest(request, response);
    }
}
