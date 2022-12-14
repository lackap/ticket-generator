package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NiveauRepository extends CrudRepository<Niveau, String> {

    List<Niveau> findByEvenement(Evenement evenement);

    Niveau findByEvenementAndIntitule(Evenement evenement, String intitule);
}
