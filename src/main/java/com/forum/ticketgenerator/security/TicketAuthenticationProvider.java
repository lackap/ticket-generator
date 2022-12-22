package com.forum.ticketgenerator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TicketAuthenticationProvider implements AuthenticationProvider {

        @Autowired
        private DatabaseUserDetailService userService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public Authentication authenticate(Authentication auth) throws AuthenticationException {
            String username = auth.getName();
            String password = auth.getCredentials().toString();

            UserDetails user = userService.loadUserByUsername(username);
            if (user == null) {
                throw new BadCredentialsException("invalid_username_or_pass");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("invalid_username_or_pass");
            }

            return new UsernamePasswordAuthenticationToken(user,
                    password, user.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> auth) {
            return auth.equals(UsernamePasswordAuthenticationToken.class);
        }
}
