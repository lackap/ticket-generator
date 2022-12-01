package com.forum.ticketgenerator.security;

import com.forum.ticketgenerator.model.database.TicketUser;
import com.forum.ticketgenerator.repository.TicketUserRepository;
import com.forum.ticketgenerator.service.PdfGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUserDetailService.class);

    @Autowired
    private TicketUserRepository ticketUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.info("Triing to log in user " + username);
        TicketUser user = ticketUserRepository.findByUsername(username);
        if (user == null) {
            LOGGER.info("User " + username + "has not been found");
            throw new UsernameNotFoundException(username);
        }
        return new ApplicationUser(user);
    }
}
