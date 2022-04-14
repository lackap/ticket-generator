package com.forum.ticketgenerator.event;

import com.forum.ticketgenerator.model.PosteMatching;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;

import java.util.List;

public class SearchEvent extends ComponentEvent<Button> {
    private String label;
    private List<PosteMatching> postesMatching;

    public SearchEvent (Button source, boolean fromClient, List<PosteMatching> postesMatching, String label) {
        super(source, fromClient);
        this.postesMatching = postesMatching;
        this.label = label;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public List<PosteMatching> getPostesMatching () {
        return postesMatching;
    }

    public void setPostesMatching (List<PosteMatching> postesMatching) {
        this.postesMatching = postesMatching;
    }
}
