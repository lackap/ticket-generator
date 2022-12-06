package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.FamilleMetier;

import java.io.IOException;
import java.util.List;

public interface IPosteModelService {

    List<PosteMatching> searchFromFamilleMetier(FamilleMetier familleMetier, Evenement evenement) throws IOException;
}
