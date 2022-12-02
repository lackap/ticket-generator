package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.exception.UserCreationException;
import com.forum.ticketgenerator.model.database.TicketUser;
import com.forum.ticketgenerator.repository.TicketUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TicketUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TicketUserRepository ticketUserRepository;

    public void createUser(String name, String password, String role, String displayedName, byte[] logo) throws UserCreationException {
        if (name == null) {
            throw new UserCreationException("L'identifiant doit être renseigné.");
        }
        if (password == null) {
            throw new UserCreationException("Le mot de passe doit être renseigné.");
        }
        if (role == null) {
            throw new UserCreationException("Le role doit être renseigné.");
        }
        TicketUser user = ticketUserRepository.findByUsername(name);
        if (user != null) {
            throw new UserCreationException("L'utilisateur existe déjà");
        }

        TicketUser ticketUser = new TicketUser();
        ticketUser.setUsername(name);
        ticketUser.setPassword(passwordEncoder.encode(password));
        ticketUser.setRole(role);
        ticketUser.setLogo(logo);
        ticketUser.setDisplayName(displayedName);
        ticketUserRepository.save(ticketUser);
    }

}
