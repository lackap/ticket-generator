package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class PdfGenerationService {

    public void genererPdf() {

        try {
            String name = "forum_entreprises_" + Instant.now().toEpochMilli() + " .pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(name));

            document.open();

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
        Stream.of("Nom Entreprise", "Intitule poste", "Niveau", "Contrat", "Stand")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
