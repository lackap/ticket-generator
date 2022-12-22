package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Formation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends CrudRepository<Formation, String> {
    Formation findByNomCentre(String nomCentre);

    Formation findByNomCentreAndDiplomesEvenement(String nomCentre, Evenement evenement);

    List<Formation> findByDiplomesEvenement(Evenement evenement);
}
