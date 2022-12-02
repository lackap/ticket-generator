package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.database.*;
import com.forum.ticketgenerator.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EntrepriseModelService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Transactional
    public void addPoste(String nomEntreprise, String intitule, FamilleMetier familleMetier, Niveau niveau, TypeContrat typeContrat) {
        Poste poste = new Poste();
        poste.setIntitule(intitule);
        poste.setFamilleMetier(familleMetier);
        poste.setNiveau(niveau);
        poste.setTypeContrat(typeContrat);

        Entreprise entreprise = entrepriseRepository.findByNom(nomEntreprise);
        entreprise.getPostes().add(poste);
        entrepriseRepository.save(entreprise);

    }
}
