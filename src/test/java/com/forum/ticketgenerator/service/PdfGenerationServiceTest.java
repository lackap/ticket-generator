package com.forum.ticketgenerator.service;

import com.forum.ticketgenerator.model.Model;
import com.forum.ticketgenerator.model.PosteMatching;
import com.forum.ticketgenerator.model.database.Evenement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PdfGenerationServiceTest {

    @Test
    public void pdf_test() {
        List<PosteMatching> liste = new ArrayList<>();
        PosteMatching posteMatching = new PosteMatching();
        posteMatching.setNom("Ent1");
        posteMatching.setStand("2");
        posteMatching.setSecteurActivite("NUMERIQUE");
        posteMatching.setIntitule("Intitule");
        liste.add(posteMatching);
        Model.getInstance().setPostesMatching(liste);
        PdfGenerationService pdfGenerationService = new PdfGenerationService();
        pdfGenerationService.genererPdf(new Evenement());
    }
}
