package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.event.ReloadEvent;
import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.service.ModelService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@UIScope
public class LoadingView extends VerticalLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingView.class);

    @Autowired
    private ModelService modelService;

    private Upload uploadEntreprise;

    private Upload uploadFormation;

    public LoadingView() {

    }

    @PostConstruct
    private void init() {
        MemoryBuffer memoryBufferEntreprise = new MemoryBuffer();
        uploadEntreprise = new Upload(memoryBufferEntreprise);
        uploadEntreprise.setUploadButton(new Button("Charger les entreprises : "));
        uploadEntreprise.setDropLabel(new Label("Déposer le fichier ici"));
        uploadEntreprise.addSucceededListener(event -> {
            InputStream inputStream = memoryBufferEntreprise.getInputStream();
            try {
                Model.getInstance().setEntreprises(modelService.loadEntreprises(new InputStreamReader(inputStream)));
                fireEvent(new ReloadEvent(uploadEntreprise, false));
            } catch (IOException e) {
                LOGGER.error("Erreur lors du chargement des entreprises", e);
            }
        });
        add(uploadEntreprise);

        MemoryBuffer memoryBufferFormation = new MemoryBuffer();
        uploadFormation = new Upload(memoryBufferFormation);
        uploadFormation.setUploadButton(new Button("Charger les formations : "));
        uploadFormation.setDropLabel(new Label("Déposer le fichier ici"));
        uploadFormation.addSucceededListener(event -> {
            InputStream inputStream = memoryBufferFormation.getInputStream();
            try {
                Model.getInstance().setFormations(modelService.loadFormations(new InputStreamReader(inputStream)));
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
