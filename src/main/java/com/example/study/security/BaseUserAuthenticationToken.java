package com.example.study.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BaseUserAuthenticationToken implements Authentication {

    private final BaseUser baseUser;
    private boolean authenticated;
    private final Collection<? extends GrantedAuthority> authorities;

    public BaseUserAuthenticationToken(BaseUser baseUser, Collection<? extends GrantedAuthority> authorities) {
        this.baseUser = baseUser;
        this.authorities = authorities;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return baseUser;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return baseUser.getUserInfoId().toString();
    }
}
