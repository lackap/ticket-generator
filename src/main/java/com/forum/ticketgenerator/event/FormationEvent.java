package com.forum.ticketgenerator.event;

import com.forum.ticketgenerator.model.PosteMatching;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;

import java.util.List;

public class FormationEvent extends ComponentEvent<Button> {

    private List<PosteMatching> postesMatching;

    public FormationEvent (Button source, boolean fromClient, List<PosteMatching> postesMatching) {
        super(source, fromClient);
        this.postesMatching = postesMatching;
    }

    public List<PosteMatching> getPostesMatching () {
        return postesMatching;
    }

    public void setPostesMatching (List<PosteMatching> postesMatching) {
        this.postesMatching = postesMatching;
    }
}
