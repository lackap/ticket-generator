package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.model.database.TypeContrat;
import com.forum.ticketgenerator.repository.TypeContratRepository;
import com.forum.ticketgenerator.service.model.ITypeContratModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeContratModelService implements ITypeContratModelService {

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Transactional
    @Override
    public List<TypeContrat> searchAllTypeContrat() {
        Iterable<TypeContrat> typesContrat = typeContratRepository.findAll();
        List<TypeContrat> typesContratList = new ArrayList<>();
        typesContrat.forEach(typeContrat -> typesContratList.add(typeContrat));
        return typesContratList;
    }

    @Override
    public void enregistrer (String typeContratValue) {
        TypeContrat typeContrat = new TypeContrat();
        typeContrat.setIntitule(typeContratValue);
        typeContratRepository.save(typeContrat);
    }
}
