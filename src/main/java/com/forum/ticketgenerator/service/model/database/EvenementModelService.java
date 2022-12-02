package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.repository.EvenementRepository;
import com.forum.ticketgenerator.repository.NiveauRepository;
import com.forum.ticketgenerator.service.model.IEvenementModelService;
import com.forum.ticketgenerator.service.model.INiveauModelService;
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
    public void enregistrer (String evenementValue) {
        Evenement evenement = new Evenement();
        evenement.setIntitule(evenementValue);
        evenementRepository.save(evenement);
    }
}
