package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.ColorAvailable;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.repository.SecteurActiviteRepository;
import com.forum.ticketgenerator.service.ColorService;
import com.forum.ticketgenerator.service.model.IParametrageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecteurActiviteModelService implements IParametrageService<SecteurActivite> {

    @Autowired
    private ColorService colorService;

    @Autowired
    private SecteurActiviteRepository secteurActiviteRepository;

    @Transactional
    @Override
    public List<SecteurActivite> searchParEvenement(Evenement evenement) {
        Iterable<SecteurActivite> secteursIterable = secteurActiviteRepository.findByEvenement(evenement);
        List<SecteurActivite> secteurs = new ArrayList<>();
        secteursIterable.forEach(secteurs::add);
        return secteurs;
    }

    @Override
    @Transactional
    public SecteurActivite enregistrer (String secteurValue, Evenement evenement) {
        SecteurActivite secteur = new SecteurActivite();
        secteur.setIntitule(secteurValue);
        secteur.setEvenement(evenement);
        return secteurActiviteRepository.save(secteur);
    }

    public SecteurActivite enregistrer (String secteurValue, ColorAvailable couleur, Evenement evenement) throws ModelCreationException {
        if (evenement == null) {
            throw new ModelCreationException("Un évènement doit être rattaché a la création du secteur d'activité");
        }
        if (StringUtils.isEmpty(secteurValue)) {
            throw new ModelCreationException("Le " + evenement.getLabelSecteurActivite() + " doit être renseigné");
        }
        if (couleur == null) {
            throw new ModelCreationException("Une couleur doit être associée au " + evenement.getLabelSecteurActivite());
        }
        SecteurActivite secteurExisting = secteurActiviteRepository.findByEvenementAndIntitule(evenement, secteurValue);
        if (secteurExisting != null) {
            throw new ModelCreationException(evenement.getLabelSecteurActivite() + " " + secteurValue + " déjà existant pour l'évènement " + evenement.getIntitule());
        }

         secteurExisting = secteurActiviteRepository.findByEvenementAndCouleur(evenement, couleur.name());
        if (secteurExisting != null) {
            throw new ModelCreationException(evenement.getLabelSecteurActivite() + " avec la couleur " + couleur.name() + " déjà existant pour l'évènement " + evenement.getIntitule());
        }

        SecteurActivite secteur = new SecteurActivite();
        secteur.setIntitule(secteurValue);
        secteur.setCouleur(couleur.name());
        secteur.setEvenement(evenement);
        secteur.setCouleurLabel(colorService.getCouleurLabel(couleur.name()));
        return secteurActiviteRepository.save(secteur);
    }

    @Override
    public void delete (SecteurActivite item) {
        secteurActiviteRepository.delete(item);
    }
}
