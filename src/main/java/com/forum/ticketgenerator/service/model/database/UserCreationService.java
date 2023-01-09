package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.constants.Roles;
import com.forum.ticketgenerator.exception.UserCreationException;
import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Formation;
import com.forum.ticketgenerator.model.database.TicketUser;
import com.forum.ticketgenerator.repository.EntrepriseRepository;
import com.forum.ticketgenerator.repository.FormationRepository;
import com.forum.ticketgenerator.repository.TicketUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCreationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TicketUserRepository ticketUserRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private FormationRepository formationRepository;

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
        if (StringUtils.isEmpty(displayedName)) {
            displayedName = name;
        }
        ticketUser.setDisplayName(displayedName);
        ticketUserRepository.save(ticketUser);

        if (Roles.ENTREPRISE.name().equals(role)) {
            Entreprise entreprise = new Entreprise();
            entreprise.setNom(displayedName);
            entrepriseRepository.save(entreprise);
        }
        if (Roles.FORMATION.name().equals(role)) {
            Formation formation = new Formation();
            if (StringUtils.isEmpty(displayedName)) {
                displayedName = name;
            }
            formation.setNomCentre(displayedName);
            formationRepository.save(formation);
        }
    }

}
