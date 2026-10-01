package com.companion.security.service;

import com.companion.jooq.generated.tables.pojos.Users;
import com.companion.security.CustomUserDetails;
import com.companion.security.FindUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private FindUserRepository findUserRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findUserRepository.find(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Преобразуем нашу сущность в объект, который понимает Spring Security
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .id(user.getId())
                .build();
    }
}