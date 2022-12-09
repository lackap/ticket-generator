package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.security.SecurityService;
import com.forum.ticketgenerator.service.PdfGenerationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.frontend.installer.DefaultFileDownloader;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Component
@UIScope
@PreserveOnRefresh
public class PdfGenerationView extends VerticalLayout {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private SecurityService securityService;

    public PdfGenerationView () {
        setAlignItems(Alignment.CENTER);
    }

    private Anchor anchor;

    @PostConstruct
    public void init(){
        getStyle().set("text-align", "center");
        Button buttonGeneratePdf = new Button();
        buttonGeneratePdf.setText("Générer PDF");
        Element image = new Element("object");
        image.setAttribute("type", "image/svg+xml");
        image.setVisible(false);
        buttonGeneratePdf.addClickListener(
                event -> {
                    byte[] pdfValue = pdfGenerationService.genererPdf(securityService.getAuthenticatedUser().getEvenement());
                    StreamResource resource = new StreamResource("forum" + Instant.now().toEpochMilli() + ".pdf",
                            () -> new ByteArrayInputStream(pdfValue));
                    image.setAttribute("data", resource);
                    anchor.setHref(image.getAttribute("data"));
                    UI.getCurrent().getPage().executeJs("$0.click()", anchor);
                });
        add(buttonGeneratePdf);
        anchor = new Anchor();
        anchor.setTarget("_blank");
        anchor.setText("Download");
        add(anchor);
        UI.getCurrent().getElement().appendChild(image);
    }

}
