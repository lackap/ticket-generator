package com.forum.ticketgenerator.pdf.bean;

import com.itextpdf.kernel.color.Color;

public class PdfLegendBean {
    private String id;
    private String label;
    private Color color;

    public PdfLegendBean(String id, String label, Color color) {
        this.id = id;
        this.label = label;
        this.color = color;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public Color getColor () {
        return color;
    }

    public void setColor (Color color) {
        this.color = color;
    }
}
