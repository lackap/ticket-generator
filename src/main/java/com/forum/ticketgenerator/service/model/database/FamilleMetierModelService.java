package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.repository.FamilleMetierRepository;
import com.forum.ticketgenerator.service.model.IFamilleMetierModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilleMetierModelService implements IFamilleMetierModelService {

    @Autowired
    private FamilleMetierRepository familleMetierRepository;

    @Transactional
    @Override
    public List<FamilleMetier> searchAllFamilleMetier() {
        Iterable<FamilleMetier> famillesMetier = familleMetierRepository.findAll();
        List<FamilleMetier> famillesMetierList = new ArrayList<>();
        famillesMetier.forEach(familleMetier -> famillesMetierList.add(familleMetier));
        return famillesMetierList;
    }

    @Override
    public void enregistrer (String familleMetierValue) {
        FamilleMetier familleMetier = new FamilleMetier();
        familleMetier.setIntitule(familleMetierValue);
        familleMetierRepository.save(familleMetier);
    }

}
