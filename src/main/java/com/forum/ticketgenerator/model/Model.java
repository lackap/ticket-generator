package com.forum.ticketgenerator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private Map<String, Entreprise> entreprises;
    private Map<String, Formation> formations;
    private List<PosteMatching> postesMatching;
    private static  Model INSTANCE;

    private Model() {
        this.entreprises = new HashMap<>();
        this.formations = new HashMap<>();
        this.postesMatching = new ArrayList<>();
    }

    public static Model getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    public Map<String, Entreprise> getEntreprises () {
        return entreprises;
    }

    public void setEntreprises (Map<String, Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public Map<String, Formation> getFormations () {
        return formations;
    }

    public void setFormations (Map<String, Formation> formations) {
        this.formations = formations;
    }

    public List<PosteMatching> getPostesMatching () {
        return postesMatching;
    }

    public void setPostesMatching (List<PosteMatching> postesMatching) {
        this.postesMatching = postesMatching;
    }
}
