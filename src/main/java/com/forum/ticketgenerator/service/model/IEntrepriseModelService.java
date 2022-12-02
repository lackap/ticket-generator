package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.mapper.PosteMatchingMapper;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface IEntrepriseModelService {
    void addPoste(String nomEntreprise, String intitule, FamilleMetier familleMetier, Niveau niveau, TypeContrat typeContrat);

    public List<String> getFamilleMetierEntreprises() throws IOException;

    public List<String> getSecteursActivitesEntreprises() throws IOException;

    public List<PosteMatching> searchFromFamilleMetier(String familleMetier) throws IOException;

    public List<PosteMatching> searchFromSecteurActivite(String secteur) throws IOException;

    public List<PosteMatching> searchFromEntrepriseName(String entrepriseName);

}
