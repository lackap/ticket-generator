package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.repository.NiveauRepository;
import com.forum.ticketgenerator.service.model.INiveauModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class NiveauModelService implements INiveauModelService {

    @Autowired
    private NiveauRepository niveauRepository;

    @Transactional
    @Override
    public List<Niveau> searchAllNiveau() {
        Iterable<Niveau> niveaux = niveauRepository.findAll();
        List<Niveau> niveauList = new ArrayList<>();
        niveaux.forEach(niveau -> niveauList.add(niveau));
        return niveauList;
    }

    @Override
    public void enregistrer (String niveauValue) {
        Niveau niveau = new Niveau();
        niveau.setIntitule(niveauValue);
        niveauRepository.save(niveau);
    }
}
