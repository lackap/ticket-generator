package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends CrudRepository<Formation, String> {
    public List<Formation> findByNomCentre(String nomCentre);

    public List<Formation> findByNomCentreAndDiplomesIntituleDiplome(String nomCentre, String intituleDiplome);

    public List<Formation> findByDiplomesIntituleDiplome(String intituleDiplome);
}
