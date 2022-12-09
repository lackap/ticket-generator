package com.forum.ticketgenerator.model;

import com.forum.ticketgenerator.pdf.bean.PdfLegendBean;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import lombok.Data;

public enum ColorAvailable {
    GREEN("Vert"),
    YELLOW("Jaune"),
    ORANGE("Orange"),
    RED("Rouge"),
    LIGHT_GRAY("Gris"),
    PINK("Rose"),
    BLUE("Bleu");

    private String value;

    ColorAvailable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
