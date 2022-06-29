package com.forum.ticketgenerator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<PosteMatching> postesMatching;
    private String entreprisesFile;
    private String formationsFile;
    private static  Model INSTANCE;

    private Model() {
        this.postesMatching = new ArrayList<>();
    }

    public static Model getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    public String getEntreprisesFile () {
        return entreprisesFile;
    }

    public String getFormationsFile () {
        return formationsFile;
    }

    public void setEntrepriseFile (String entrepriseFile) throws IOException {
        this.entreprisesFile = entrepriseFile;

    }

    public void setFormationsFile (String formationFile) throws IOException {
        this.formationsFile = formationFile;
    }

    public List<PosteMatching> getPostesMatching () {
        return postesMatching;
    }

    public void setPostesMatching (List<PosteMatching> postesMatching) {
        this.postesMatching = postesMatching;
    }
}
