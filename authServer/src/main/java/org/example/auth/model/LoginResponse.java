package org.example.auth.model;

public class LoginResponse {
    private String code;

    public LoginResponse(String code) {
        this.code = code;
    }

    public LoginResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
