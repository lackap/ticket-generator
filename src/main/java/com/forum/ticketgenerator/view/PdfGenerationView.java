package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.event.FormationEvent;
import com.forum.ticketgenerator.service.ModelService;
import com.forum.ticketgenerator.service.PdfGenerationService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;

@Component
public class PdfGenerationView extends VerticalLayout {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    private Button buttonGeneratePdf;

    public PdfGenerationView () {

    }

    @PostConstruct
    public void init(){
        buttonGeneratePdf = new Button();
        buttonGeneratePdf.setText("Générer PDF recherche");
        buttonGeneratePdf.addClickListener(event -> {
              pdfGenerationService.genererPdf();
        });
        add(buttonGeneratePdf);
    }

}
