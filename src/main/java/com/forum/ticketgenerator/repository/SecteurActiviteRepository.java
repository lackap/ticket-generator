package com.forum.ticketgenerator.repository;


import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurActiviteRepository extends CrudRepository<SecteurActivite, String> {

    List<SecteurActivite> findByEvenement(Evenement evenement);
}
