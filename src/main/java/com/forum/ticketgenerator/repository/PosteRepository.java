package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Poste;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosteRepository  extends CrudRepository<Poste, String> {

    List<Poste> findByFamilleMetierInAndEvenement(List<FamilleMetier> famillesMetier, Evenement evenement);

    List<Poste> findByFamilleMetierAndEvenement(FamilleMetier familleMetier, Evenement evenement);

}
