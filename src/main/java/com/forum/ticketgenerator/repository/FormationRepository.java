package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends CrudRepository<Formation, String> {
    public Formation findByNomCentre(String nomCentre);

    public Formation findByNomCentreAndDiplomesEvenement(String nomCentre, Evenement evenement);

    public List<Formation> findByNomCentreAndDiplomesIntituleDiplome(String nomCentre, String intituleDiplome);

    public List<Formation> findByDiplomesIntituleDiplome(String intituleDiplome);

    public List<Formation> findByDiplomesEvenement(Evenement evenement);
}
