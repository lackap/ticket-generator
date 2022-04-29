package com.forum.ticketgenerator.view;

import com.forum.ticketgenerator.service.PdfGenerationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class PdfGenerationView extends VerticalLayout {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    public PdfGenerationView () {
        setAlignItems(Alignment.CENTER);
    }

    @PostConstruct
    public void init(){
        getStyle().set("text-align", "center");
        Button buttonGeneratePdf = new Button();
        buttonGeneratePdf.setText("Générer PDF");
        buttonGeneratePdf.addClickListener(event -> pdfGenerationService.genererPdf());
        add(buttonGeneratePdf);
    }

}
