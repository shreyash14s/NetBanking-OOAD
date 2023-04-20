package com.netbanking.backend.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.repository.UserRecordRepository;


@Service
public class NBUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRecordRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(username, user.getUserPassword(),
                true, true, true, true,
                // null);
                getAuthorities());
    }

    private static List<GrantedAuthority> getAuthorities() {
        // List<GrantedAuthority> authorities = new ArrayList<>();
        // for (String role : roles) {
        //     authorities.add(new SimpleGrantedAuthority(role));
        // }
        // return authorities;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
    
}
