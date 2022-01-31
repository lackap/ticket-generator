package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.service.ModelService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@Component
public class LoadingView extends HorizontalLayout {


    @Autowired
    private ModelService modelService;

    private Upload uploadEntreprise;

    private Upload uploadFormation;

    public LoadingView() {

    }

    @PostConstruct
    private void init() {

    Text textEntreprise = new Text("Charger les entreprises : ");
    add(textEntreprise);
    Button crapButton = new Button("press to get an upload button");
    add(crapButton);
    crapButton.addClickListener(buttonClickEvent -> {
        addEntrepriseButton();
        crapButton.setVisible(false);
    });

    Text textFormation = new Text("Charger les formations : ");
    add(textFormation);
    MemoryBuffer memoryBufferFormation = new MemoryBuffer();
    uploadFormation = new Upload(memoryBufferFormation);
    uploadFormation.addSucceededListener(event -> {
        InputStream inputStream = memoryBufferFormation.getInputStream();
        try {
            ModelService.loadFormations(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    add(uploadFormation);
}

    private void addEntrepriseButton () {
        FileBuffer memoryBufferEntreprise = new FileBuffer();
        uploadEntreprise = new Upload(memoryBufferEntreprise);
        uploadEntreprise.addSucceededListener(event -> {
            InputStream inputStream = memoryBufferEntreprise.getInputStream();
            try {
                ModelService.loadEntreprises(new InputStreamReader(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(uploadEntreprise);
    }

}
