package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.FamilleMetier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilleMetierRepository extends CrudRepository<FamilleMetier, String> {

}
