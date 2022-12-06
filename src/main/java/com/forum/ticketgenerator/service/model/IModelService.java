package com.forum.ticketgenerator.service.model;

import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Diplome;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.Formation;

import java.io.IOException;
import java.util.List;

public interface IModelService {

    List<PosteMatching> searchFromFormation(Formation formation, Diplome diplome, Evenement evenement) throws IOException;
}
