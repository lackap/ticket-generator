package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.repository.EvenementRepository;
import com.forum.ticketgenerator.service.model.IEvenementModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvenementModelService implements IEvenementModelService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Transactional
    @Override
    public List<Evenement> searchAllEvenement() {
        Iterable<Evenement> evenementsIterable = evenementRepository.findAll();
        List<Evenement> evenements = new ArrayList<>();
        evenementsIterable.forEach(evenement -> evenements.add(evenement));
        return evenements;
    }

    @Override
    public Evenement enregistrer (String evenementValue, String labelSecteurActivite, byte[] affiche) throws ModelCreationException {
        if (StringUtils.isEmpty(evenementValue)) {
            throw new ModelCreationException("L'évènement doit être renseigné");
        }
        if (StringUtils.isEmpty(labelSecteurActivite)) {
            labelSecteurActivite = "Secteur d'activité";
        }
        Evenement evenementExisting = evenementRepository.findByIntitule(evenementValue);
        if (evenementExisting != null) {
            throw new ModelCreationException("Evenement " + evenementValue + " déjà existant");
        }
        Evenement evenement = new Evenement();
        evenement.setIntitule(evenementValue);
        evenement.setLabelSecteurActivité(labelSecteurActivite);
        evenement.setAffiche(affiche);
        return evenementRepository.save(evenement);
    }

    @Override
    public Evenement mettreAJourParametres (Evenement evenement, String labelSecteurActivite, boolean displaySecteur,
                                                  boolean displayNiveau, boolean displayContrat, byte[] affiche) throws ModelCreationException {
        evenement.setLabelSecteurActivité(labelSecteurActivite);
        evenement.setDisplaySecteur(displaySecteur);
        evenement.setDisplayNiveau(displayNiveau);
        evenement.setDisplayTypeContrat(displayContrat);
        if (affiche != null) {
            evenement.setAffiche(affiche);
        }
        return evenementRepository.save(evenement);
    }

    @Override
    public void supprimer (Evenement evenement) {
        evenementRepository.delete(evenement);
    }
}
