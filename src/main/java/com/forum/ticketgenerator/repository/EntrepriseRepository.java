package com.forum.ticketgenerator.repository;

import com.forum.ticketgenerator.model.database.Entreprise;
import com.forum.ticketgenerator.model.database.Formation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepriseRepository  extends CrudRepository<Entreprise, String> {
    public List<Entreprise> findByPostesFamilleMetierIn(List<String> famillesMetier);
    public List<Entreprise> findByPostesFamilleMetier(String familleMetier);

    public List<Entreprise> findBySecteursActiviteName(String secteur);

    public Entreprise findByNom(String nom);

}
