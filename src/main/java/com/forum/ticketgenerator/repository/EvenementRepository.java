package com.forum.ticketgenerator.repository;


import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Evenement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvenementRepository  extends CrudRepository<Evenement, String> {
    Evenement findByIntitule(String intitule);
}
