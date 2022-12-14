package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepriseRepository  extends CrudRepository<Entreprise, String> {

    Entreprise findByNom(String nom);

    Entreprise findByNomAndPostesEvenement(String nom, Evenement evenement);

    @Query("select distinct e from Entreprise e  inner join e.postes p where p.evenement = ?1")
    List<Entreprise> findByPostesEvenement(Evenement evenement);


}
