package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.FamilleMetier;
import com.forum.ticketgenerator.model.database.Niveau;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NiveauRepository extends CrudRepository<Niveau, String> {

}
