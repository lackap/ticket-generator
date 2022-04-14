package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class PdfGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationService.class);

    public void genererPdf() {

        try {
            String name = "forum_entreprises_" + Instant.now().toEpochMilli() + " .pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(name));

            document.open();
            try {
                Image imageMetropole = Image.getInstance(getClass().getClassLoader().getResource("META-INF/resources/img/partenaires.png"));
                imageMetropole.setWidthPercentage(40);
                float scaler = ((document.getPageSize().getWidth() / 2 - document.leftMargin()
                        - document.rightMargin()) / imageMetropole.getWidth()) * 100;

                imageMetropole.scalePercent(scaler);
                imageMetropole.setAlignment(Element.ALIGN_CENTER);
                document.add(imageMetropole);
            } catch (Exception e ) {
                LOGGER.error("File not found : META-INF/resources/img/orleans_metropole.png", e);
            }

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            Model.getInstance().getPostesMatching().stream().forEach(poste -> addRow(table, poste));

            document.add(table);
            document.close();
            File file = new File(name);
            if (file.exists()) {
               if (Desktop.isDesktopSupported()) {
                   Desktop.getDesktop().open(file);
               } else {
                   ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "explorer " + file.getAbsolutePath());
                    processBuilder.start();
               }
            }
        }  catch (Exception e ) {

        }
    }

    private void addRow(PdfPTable table, PosteMatching posteMatching) {
        table.addCell(posteMatching.getNom());
        table.addCell(posteMatching.getIntitule());
        table.addCell(posteMatching.getNiveau());
        table.addCell(posteMatching.getContrat());
        table.addCell(posteMatching.getStand() != null ? posteMatching.getStand().toString() : "");
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Entreprise", "Intitule de poste", "Niveau", "Contrat", "N°Stand")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
