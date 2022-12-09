package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.repository.NiveauRepository;
import com.forum.ticketgenerator.service.model.IParametrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class NiveauModelService implements IParametrageService<Niveau> {

    @Autowired
    private NiveauRepository niveauRepository;

    @Transactional
    @Override
    public List<Niveau> searchParEvenement(Evenement evenement) {
        Iterable<Niveau> niveaux = niveauRepository.findByEvenement(evenement);
        List<Niveau> niveauList = new ArrayList<>();
        niveaux.forEach(niveau -> niveauList.add(niveau));
        return niveauList;
    }

    @Override
    public Niveau enregistrer (String niveauValue, Evenement evenement) {
        Niveau niveau = new Niveau();
        niveau.setIntitule(niveauValue);
        niveau.setEvenement(evenement);
        return niveauRepository.save(niveau);
    }

    @Override
    public void delete (Niveau item) {
        niveauRepository.delete(item);
    }
}
