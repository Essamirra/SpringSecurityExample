package org.example.auth.model;

public class TokenCheckRequest {
    private String token;

    public TokenCheckRequest(String token) {
        this.token = token;
    }

    public TokenCheckRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
