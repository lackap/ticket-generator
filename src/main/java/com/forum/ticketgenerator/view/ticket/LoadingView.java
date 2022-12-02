package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.Model;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@UIScope
public class LoadingView extends VerticalLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingView.class);

    private Upload uploadEntreprise;

    private Upload uploadFormation;

    public LoadingView() {

    }

    @PostConstruct
    private void init() {
        FileBuffer memoryBufferEntreprise = new FileBuffer();
        uploadEntreprise = new Upload(memoryBufferEntreprise);
        uploadEntreprise.setUploadButton(new Button("Charger les entreprises : "));
        uploadEntreprise.setDropLabel(new Label("Déposer le fichier ici"));
        uploadEntreprise.addSucceededListener(event -> {
            try {
                Model.getInstance().setEntrepriseFile(memoryBufferEntreprise.getFileData().getFile().getAbsolutePath());
                fireEvent(new ReloadEvent(uploadEntreprise, false));
            } catch (IOException e) {
                LOGGER.error("Erreur lors du chargement des entreprises", e);
            }
        });
        add(uploadEntreprise);

        FileBuffer memoryBufferFormation = new FileBuffer();
        uploadFormation = new Upload(memoryBufferFormation);
        uploadFormation.setUploadButton(new Button("Charger les formations : "));
        uploadFormation.setDropLabel(new Label("Déposer le fichier ici"));
        uploadFormation.addSucceededListener(event -> {
            try {
                Model.getInstance().setFormationsFile(memoryBufferFormation.getFileData().getFile().getAbsolutePath());
                fireEvent(new ReloadEvent(uploadFormation, false));
            } catch (IOException e) {
                LOGGER.error("Erreur lors du chargement des formations", e);
            }
        });
        add(uploadFormation);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
