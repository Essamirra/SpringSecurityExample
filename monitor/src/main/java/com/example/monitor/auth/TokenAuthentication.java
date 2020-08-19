package com.example.monitor.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenAuthentication implements Authentication {
    private boolean isAuthenticated = false;
    private Object token;
    List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

    public TokenAuthentication(String token) {
        this.token = token;
        if(token.contains("Admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_Admin"));
        } else if (token.contains("User")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_User"));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.isAuthenticated  = b;
    }

    @Override
    public String getName() {
        return "TokenAuthentication";
    }
}
