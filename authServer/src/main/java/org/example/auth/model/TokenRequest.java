package org.example.auth.model;

public class TokenRequest {
    private String code;

    public TokenRequest(String code) {
        this.code = code;
    }

    public TokenRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
