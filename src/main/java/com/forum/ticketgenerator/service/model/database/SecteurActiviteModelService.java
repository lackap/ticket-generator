package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.repository.SecteurActiviteRepository;
import com.forum.ticketgenerator.service.model.IParametrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecteurActiviteModelService implements IParametrageService<SecteurActivite> {

    @Autowired
    private SecteurActiviteRepository secteurActiviteRepository;

    @Transactional
    @Override
    public List<SecteurActivite> searchParEvenement(Evenement evenement) {
        Iterable<SecteurActivite> secteursIterable = secteurActiviteRepository.findByEvenement(evenement);
        List<SecteurActivite> secteurs = new ArrayList<>();
        secteursIterable.forEach(secteur -> secteurs.add(secteur));
        return secteurs;
    }

    @Override
    public SecteurActivite enregistrer (String secteurValue, Evenement evenement) {
        SecteurActivite secteur = new SecteurActivite();
        secteur.setIntitule(secteurValue);
        secteur.setEvenement(evenement);
        return secteurActiviteRepository.save(secteur);
    }

    public SecteurActivite enregistrer (String secteurValue, String couleur, Evenement evenement) {
        SecteurActivite secteur = new SecteurActivite();
        secteur.setIntitule(secteurValue);
        secteur.setCouleur(couleur);
        secteur.setEvenement(evenement);
        return secteurActiviteRepository.save(secteur);
    }

    @Override
    public void delete (SecteurActivite item) {
        secteurActiviteRepository.delete(item);
    }
}
