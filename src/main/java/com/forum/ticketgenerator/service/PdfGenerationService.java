package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.pdf.bean.PdfLegendBean;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.GrooveBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PdfGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerationService.class);

    private List<PdfLegendBean> pdfLegendBeans;
    private Border DEFAULT_BORDER = new GrooveBorder(1.0f);

    public void genererPdf() {
        String name = "forum_entreprises_" + Instant.now().toEpochMilli() + " .pdf";
        try {
            Document document = createDocument(name);
            buildDocumentHeader(document);
            buildTableLegend(document);
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
            Table pdfPTable = new Table(UnitValue.createPercentArray(new float[]{40,20,40}));
            float cellSize = (document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin()) / 4;
            pdfPTable.setWidth(UnitValue.createPercentValue(100));
            pdfPTable.setFixedLayout();
            addImageToHeader(pdfPTable, "META-INF/resources/img/clee_45.png", cellSize);
            Cell emptyCell = new Cell();
            emptyCell.setBorder(Border.NO_BORDER);
            pdfPTable.addCell(emptyCell);
            addImageToHeader(pdfPTable, "META-INF/resources/img/metropole.png", cellSize);
            document.add(pdfPTable);
        } catch (Exception e ) {
            LOGGER.error("File not found : META-INF/resources/img/clee_45.png");
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
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
    }

    private void buildDocumentBody(Document document) throws DocumentException {
        Table table = new Table(new float[] { 10, 10, 80, 10, 10});
        table.setWidthPercent(100);
        table.setBorder(DEFAULT_BORDER);
        table.setMarginTop(40.0f);
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
        Optional<Color> cellColor = getPdfLegendBeans().stream().filter(bean -> bean.getId().equals(posteMatching.getSecteurActivite())).map(PdfLegendBean::getColor).findFirst();
        Cell colorCell = new Cell();
        Paragraph paragraph = new Paragraph();
        if (cellColor.isPresent()) {
            paragraph.setBackgroundColor(cellColor.get());
        }
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
        entrepriseCell.setBold();
        entrepriseCell.add(posteMatching.getNom());
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

    private java.awt.Image loadImage(String imageFilename) {
        java.awt.Image img = null;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource((imageFilename)));
        } catch (IOException ex) {
            LOGGER.error("Error while loading image ", ex);
        }
        return img;
    }

    private void buildTableLegend(Document document) throws DocumentException {
        Table table = new Table(new float[] { 10, 15, 10, 15,10, 15, 10, 15});
        table.setWidthPercent(100);
        table.setMarginTop(40.0f);
        table.setBorder(DEFAULT_BORDER);
        Cell headerCell = new Cell(1, 8);
        Paragraph legend = new Paragraph(new Text("Légende"));
        legend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        legend.setBold();
        headerCell.setBorder(DEFAULT_BORDER);
        headerCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headerCell.add(legend);
        table.addCell(headerCell);
        for (PdfLegendBean pdfLegendBean : getPdfLegendBeans()) {
            addLegendCell(table, pdfLegendBean.getColor(), pdfLegendBean.getLabel());
        }
        document.add(table);
    }

    public void addLegendCell(Table table, Color color, String label) {
        Cell colorCell = new Cell();
        Paragraph paragraph = new Paragraph();
        paragraph.setBackgroundColor(color);
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
        labelCell.setHeight(40.f);
        labelCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        labelCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(labelCell);
    }

    private List<PdfLegendBean> getPdfLegendBeans() {
        if (this.pdfLegendBeans == null) {
            pdfLegendBeans = new ArrayList<>();
            pdfLegendBeans.add(new PdfLegendBean("AGRICULTURE", "Agriculture", Color.GREEN));
            pdfLegendBeans.add(new PdfLegendBean("BTP", "BTP", Color.YELLOW));
            pdfLegendBeans.add(new PdfLegendBean("HOTELLERIE RESTAURATION", "Hôtellerie Restauration", Color.ORANGE));
            pdfLegendBeans.add(new PdfLegendBean("FONCTION PUBLIQUE", "Fonction Publique", Color.LIGHT_GRAY));
            pdfLegendBeans.add(new PdfLegendBean("INDUSTRIE", "Industrie", Color.RED));
            pdfLegendBeans.add(new PdfLegendBean("NUMERIQUE", "Numérique", Color.DARK_GRAY));
            pdfLegendBeans.add(new PdfLegendBean("SANTE SOCIAL", "Santé Social", Color.PINK));
            pdfLegendBeans.add(new PdfLegendBean("SERVICE", "Service", Color.BLUE));
        }
        return pdfLegendBeans;
    }

}
