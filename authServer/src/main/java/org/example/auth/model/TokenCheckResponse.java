package org.example.auth.model;

public class TokenCheckResponse {
    private boolean valid;

    public TokenCheckResponse(boolean valid) {
        this.valid = valid;
    }

    public TokenCheckResponse() {
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
