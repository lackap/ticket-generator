package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.repository.EvenementRepository;
import com.forum.ticketgenerator.service.model.IEvenementModelService;
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
    public Evenement enregistrer (String evenementValue, String labelSecteurActivite) {
        Evenement evenement = new Evenement();
        evenement.setIntitule(evenementValue);
        evenement.setLabelSecteurActivité(labelSecteurActivite);
        return evenementRepository.save(evenement);
    }

    @Override
    public void supprimer (Evenement evenement) {
        evenementRepository.delete(evenement);
    }
}
