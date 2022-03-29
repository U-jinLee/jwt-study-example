package com.jwt.example.jwtexample.auth;

import com.jwt.example.jwtexample.entity.User;
import com.jwt.example.jwtexample.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * */
@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("login event by loadUserByUsername===================================");
        User userEnrtity = userRepository.findByUsername(username);
        log.info("response userEntity {}", userEnrtity.toString());
        return new PrincipalDetails(userEnrtity);
    }

}
