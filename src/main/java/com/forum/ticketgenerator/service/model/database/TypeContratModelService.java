package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.repository.TypeContratRepository;
import com.forum.ticketgenerator.service.model.IParametrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeContratModelService implements IParametrageService<TypeContrat> {

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Transactional
    @Override
    public List<TypeContrat> searchParEvenement(Evenement evenement) {
        Iterable<TypeContrat> typesContrat = typeContratRepository.findByEvenement(evenement);
        List<TypeContrat> typesContratList = new ArrayList<>();
        typesContrat.forEach(typeContrat -> typesContratList.add(typeContrat));
        return typesContratList;
    }

    @Override
    public TypeContrat enregistrer (String typeContratValue, Evenement evenement) {
        TypeContrat typeContrat = new TypeContrat();
        typeContrat.setIntitule(typeContratValue);
        typeContrat.setEvenement(evenement);
        return typeContratRepository.save(typeContrat);
    }

    @Override
    public void delete (TypeContrat item) {
        typeContratRepository.delete(item);
    }
}
