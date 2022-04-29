package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class PdfGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationService.class);

    public void genererPdf() {
        String name = "forum_entreprises_" + Instant.now().toEpochMilli() + " .pdf";
        try {
            Document document = createDocument(name);
            buildDocumentHeader(document);
            buildDocumentBody(document);
            document.close();
            openDocument(name);
        }  catch (Exception e ) {
            LOGGER.error("Erreur lors de la génération du pdf " + name, e);
        }
    }

    private Document createDocument(String fileName) throws FileNotFoundException, DocumentException {
        PdfWriter pdfWriter = new PdfWriter(fileName);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        return document;
    }

    private void buildDocumentHeader(Document document) {
        try {
            Table pdfPTable = new Table(UnitValue.createPercentArray(new float[]{50,50}));
            float cellSize = (document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin()) / 4;
            pdfPTable.setWidth(UnitValue.createPercentValue(100));
            pdfPTable.setFixedLayout();
            addImageToHeader(pdfPTable, "META-INF/resources/img/logo clee - 45 orleans.png", cellSize);
            addImageToHeader(pdfPTable, "META-INF/resources/img/ORLEANS METROPOLE.png", cellSize);
            document.add(pdfPTable);
        } catch (Exception e ) {
            LOGGER.error("File not found : META-INF/resources/img/orleans_metropole.png", e);
        }
    }

    private void addImageToHeader(Table table, String imageName, float cellSize) throws IOException {
        Image image = new Image(ImageDataFactory.create(loadImage(imageName), null, false));
        float scaler = 0.85f - ((image.getImageWidth() - cellSize) / image.getImageWidth());
        image.scale(scaler, scaler);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = new Cell();
        cell.add(image);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell);
    }

    private void buildDocumentBody(Document document) throws DocumentException {
        Table table = new Table(5);
        addTableHeader(table);
        Model.getInstance().getPostesMatching().forEach(poste -> addRow(table, poste));
        document.add(table);
    }

    private void openDocument(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "explorer " + file.getAbsolutePath());
                processBuilder.start();
            }
        }
    }

    private void addRow(Table table, PosteMatching posteMatching) {
        table.addCell(posteMatching.getNom());
        table.addCell(posteMatching.getIntitule());
        table.addCell(posteMatching.getNiveau());
        table.addCell(posteMatching.getContrat());
        table.addCell(posteMatching.getStand() != null ? posteMatching.getStand().toString() : "");
    }

    private void addTableHeader(Table table) {
        Stream.of("Entreprise", "Intitule de poste", "Niveau", "Contrat", "N°Stand")
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(Color.LIGHT_GRAY).add(new Paragraph(columnTitle));
                    table.addHeaderCell(header);
                });
    }

    private java.awt.Image loadImage(String imageFilename) {
        java.awt.Image img = null;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource((imageFilename)));
        } catch (IOException ex) {
            LOGGER.error("Error while loading image ", ex);
        }
        return img;
    }
}
