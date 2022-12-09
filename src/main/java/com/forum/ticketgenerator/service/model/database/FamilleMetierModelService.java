package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.repository.FamilleMetierRepository;
import com.forum.ticketgenerator.service.model.IParametrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilleMetierModelService implements IParametrageService<FamilleMetier> {

    @Autowired
    private FamilleMetierRepository familleMetierRepository;

    @Transactional
    @Override
    public List<FamilleMetier> searchParEvenement(Evenement evenement) {
        Iterable<FamilleMetier> famillesMetier = familleMetierRepository.findByEvenement(evenement);
        List<FamilleMetier> famillesMetierList = new ArrayList<>();
        famillesMetier.forEach(familleMetier -> famillesMetierList.add(familleMetier));
        return famillesMetierList;
    }

    @Override
    public FamilleMetier enregistrer (String familleMetierValue, Evenement evenement) {
        FamilleMetier familleMetier = new FamilleMetier();
        familleMetier.setIntitule(familleMetierValue);
        familleMetier.setEvenement(evenement);
        return familleMetierRepository.save(familleMetier);
    }

    @Override
    public void delete (FamilleMetier item) {
        familleMetierRepository.delete(item);
    }

}
