package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilleMetierRepository extends CrudRepository<FamilleMetier, String> {

    List<FamilleMetier> findByEvenement(Evenement evenement);

    FamilleMetier findByEvenementAndIntitule(Evenement evenement, String intitule);

}
