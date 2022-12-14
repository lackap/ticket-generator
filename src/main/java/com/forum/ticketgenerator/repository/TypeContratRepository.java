package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.model.database.TypeContrat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeContratRepository extends CrudRepository<TypeContrat, String> {

    List<TypeContrat> findByEvenement(Evenement evenement);

    TypeContrat findByEvenementAndIntitule(Evenement evenement, String intitule);
}
