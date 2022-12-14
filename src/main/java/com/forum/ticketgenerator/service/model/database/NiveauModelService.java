package com.forum.ticketgenerator.service.model.database;

import com.forum.ticketgenerator.exception.ModelCreationException;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.repository.NiveauRepository;
import com.forum.ticketgenerator.service.model.IParametrageService;
import org.apache.commons.lang3.StringUtils;
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
    public Niveau enregistrer (String niveauValue, Evenement evenement) throws ModelCreationException {
        if (evenement == null) {
            throw new ModelCreationException("Un évènement doit être rattaché a la création du niveau");
        }
        if (StringUtils.isEmpty(niveauValue)) {
            throw new ModelCreationException("Le niveau doit être renseigné");
        }
        Niveau niveauExisting = niveauRepository.findByEvenementAndIntitule(evenement, niveauValue);
        if (niveauExisting != null) {
            throw new ModelCreationException("Niveau " + niveauValue + " déjà existant pour l'évènement " + evenement.getIntitule());
        }
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
