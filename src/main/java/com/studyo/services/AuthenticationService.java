package com.studyo.services;

import com.studyo.domain.UserDAO;
import com.studyo.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserInfo userInfo = userDAO.getUserInfo(username);
        UserDetails userDetails = new User(userInfo.getUsername(),
                userInfo.getPassword(), userInfo.getAuthorities());
        return userDetails;
    }
}