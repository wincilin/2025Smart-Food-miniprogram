package com.smartfood.backend.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartfood.backend.entity.User;

public class LoginUser implements UserDetails{
    private final User user;

    public LoginUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 暂无权限
    }

    @Override
    public String getPassword() {
        return ""; // 微信无需密码
    }

    @Override
    public String getUsername() {
        return user.getOpenid(); // 用 openid 作为用户名
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
