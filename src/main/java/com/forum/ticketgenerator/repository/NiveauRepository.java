package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NiveauRepository extends CrudRepository<Niveau, String> {

    List<Niveau> findByEvenement(Evenement evenement);
}
