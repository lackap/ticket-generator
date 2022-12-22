package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import com.forum.ticketgenerator.model.database.SecteurActivite;
import com.forum.ticketgenerator.repository.SecteurActiviteRepository;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.GrooveBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PdfGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationService.class);

    private final Border DEFAULT_BORDER = new GrooveBorder(1.0f);

    @Autowired
    private SecteurActiviteRepository secteurActiviteRepository;

    public byte[] genererPdf(Evenement evenement) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            Document document = createDocument(pdfWriter);
            buildDocumentHeader(document, evenement);
            buildDocumentBody(document);
            document.close();
            return byteArrayOutputStream.toByteArray();
        }  catch (Exception e ) {
            LOGGER.error("Erreur lors de la génération du pdf");
        }
        return null;
    }

    private Document createDocument(PdfWriter pdfWriter) {
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        return new Document(pdfDocument, PageSize.A4.rotate());
    }

    private void buildDocumentHeader(Document document, Evenement evenement) {
        try {
            Table pdfPTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
            float cellSize = (document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin()) / 4;
            pdfPTable.setWidth(UnitValue.createPercentValue(100));
            pdfPTable.setFixedLayout();
            addLegendToHeader(pdfPTable, evenement);
            addImageToHeader(pdfPTable, cellSize);
            document.add(pdfPTable);
        } catch (Exception e ) {
            LOGGER.error("File not found : META-INF/resources/img/clee_45.png");
        }
    }

    private void addImageToHeader(Table table, float cellSize) throws IOException {
        Image image = new Image(ImageDataFactory.create(loadImage(), null, false));
        float scaler = 0.85f - ((image.getImageWidth() - cellSize) / image.getImageWidth());
        image.scale(scaler, scaler);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = new Cell();
        cell.add(image);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
    }

    private void buildDocumentBody(Document document) {
        Table table = new Table(new float[] { 10, 10, 80, 10, 10});
        table.setWidthPercent(100);
        table.setBorder(DEFAULT_BORDER);
        table.setMarginTop(40.0f);
        addTableHeader(table);
        Map<Optional<String>, List<PosteMatching>> orderedPostes = Model.getInstance().getPostesMatching().stream().collect(
                Collectors.groupingBy(posteMatching -> Optional.ofNullable(posteMatching.getSecteurActivite())));
        for (Map.Entry<Optional<String>, List<PosteMatching>> entry : orderedPostes.entrySet()) {
            entry.getValue().forEach(poste -> addRow(table, poste));
        }
        document.add(table);
    }

    private void addRow(Table table, PosteMatching posteMatching) {
        Color cellColor = getCouleur(posteMatching.getSecteurActiviteColor());
        Cell colorCell = new Cell();
        Paragraph paragraph = new Paragraph();
        paragraph.setBackgroundColor(cellColor);
        paragraph.setPadding(2);
        paragraph.setWidth(8);
        paragraph.setHeight(8);
        paragraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
        colorCell.setBorder(DEFAULT_BORDER);
        colorCell.setPadding(3);
        colorCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        colorCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        colorCell.add(paragraph);
        table.addCell(colorCell);
        Cell entrepriseCell = new Cell();
        Text entrepriseText = new Text(posteMatching.getNom());
        entrepriseText.setBold();
        entrepriseCell.setBorder(DEFAULT_BORDER);
        entrepriseCell.add(new Paragraph(entrepriseText));
        table.addCell(entrepriseCell);
        Cell intituleCell = new Cell();
        intituleCell.setBorder(DEFAULT_BORDER);
        intituleCell.add(new Paragraph(new Text(posteMatching.getIntitule())));
        table.addCell(intituleCell);
        Cell standCell = new Cell();
        standCell.setBorder(DEFAULT_BORDER);
        standCell.add(new Paragraph(new Text(posteMatching.getStand() != null ? posteMatching.getStand() : "")));
        table.addCell(standCell);
        Cell commentCell = new Cell();
        commentCell.setBorder(DEFAULT_BORDER);
        table.addCell(commentCell);
    }

    private void addTableHeader(Table table) {
        Cell typeCell = new Cell();
        typeCell.setBorder(DEFAULT_BORDER);
        typeCell.setBackgroundColor(Color.LIGHT_GRAY).add(new Paragraph(""));
        typeCell.setWidthPercent(5);
        table.addHeaderCell(typeCell);
        Cell entCell = new Cell();
        entCell.setBorder(DEFAULT_BORDER);
        entCell.setBackgroundColor(Color.LIGHT_GRAY).add(new Paragraph("Entreprise"));
        entCell.setWidthPercent(15);
        table.addHeaderCell(entCell);
        Cell posteCell = new Cell();
        posteCell.setBorder(DEFAULT_BORDER);
        posteCell.setBackgroundColor(Color.LIGHT_GRAY).add(new Paragraph("Intitule de poste"));
        posteCell.setWidthPercent(45);
        table.addHeaderCell(posteCell);
        Cell standCell = new Cell();
        standCell.setBorder(DEFAULT_BORDER);
        standCell.setBackgroundColor(Color.LIGHT_GRAY).add(new Paragraph("Stand"));
        standCell.setWidthPercent(5);
        table.addHeaderCell(standCell);
        Cell commentCell = new Cell();
        commentCell.setBorder(DEFAULT_BORDER);
        commentCell.setBackgroundColor(Color.LIGHT_GRAY).add(new Paragraph("Commentaire"));
        commentCell.setWidthPercent(20);
        table.addHeaderCell(commentCell);
    }

    private java.awt.Image loadImage() {
        java.awt.Image img = null;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource(("META-INF/resources/img/metropole.png")));
        } catch (IOException ex) {
            LOGGER.error("Error while loading image ", ex);
        }
        return img;
    }

    private void addLegendToHeader(Table mainTable, Evenement evenement) {
        Table table = new Table(new float[] { 10, 15, 10, 15,10, 15, 10, 15});
        table.setWidthPercent(100);
        table.setBorder(DEFAULT_BORDER);
        Cell headerCell = new Cell(1, 8);
        String secteurName = evenement.getLabelSecteurActivite() != null ? evenement.getLabelSecteurActivite() : "secteurs d'activité";
        Paragraph legend = new Paragraph(new Text("Légende " + secteurName));
        legend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        legend.setBold();
        headerCell.setBorder(DEFAULT_BORDER);
        headerCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headerCell.add(legend);
        table.addCell(headerCell);
        List<SecteurActivite> secteurActivites = secteurActiviteRepository.findByEvenement(evenement);
        for (SecteurActivite secteurActivite : secteurActivites) {
            addLegendCell(table, secteurActivite.getCouleur(), secteurActivite.getIntitule());
        }
        Cell cell = new Cell();
        cell.add(table);
        mainTable.addCell(cell);
    }

    public void addLegendCell(Table table, String couleur, String label) {
        Cell colorCell = new Cell();
        Paragraph paragraph = new Paragraph();
        paragraph.setBackgroundColor(getCouleur(couleur));
        paragraph.setPadding(2);
        paragraph.setWidth(8);
        paragraph.setHeight(8);
        paragraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
        colorCell.setBorder(Border.NO_BORDER);
        colorCell.setPadding(3);
        colorCell.setHeight(20);
        colorCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        colorCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        colorCell.add(paragraph);
        table.addCell(colorCell);
        Cell labelCell = new Cell();
        labelCell.add(label);
        labelCell.setBorder(Border.NO_BORDER);
        labelCell.setHeight(20.f);
        labelCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        labelCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(labelCell);
    }

    private Color getCouleur(String couleur) {
        if (couleur == null) {
            return Color.WHITE;
        }
        switch (couleur) {
            case "GREEN" :
                return Color.GREEN;
            case "ORANGE":
                return Color.ORANGE;
            case "YELLOW":
                return Color.YELLOW;
            case "RED":
                return Color.RED;
            case "LIGHT_GRAY":
                return Color.LIGHT_GRAY;
            case "PINK":
                return Color.PINK;
            case "BLUE":
                return Color.BLUE;
            case "SABLE":
                return new DeviceRgb(194, 178, 128);
            default:
                return Color.WHITE;
        }
    }

}
