package com.forum.ticketgenerator.service;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import org.springframework.stereotype.Service;

@Service
public class ColorService {


    public Color getCouleur(String couleur) {
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

    public String getCouleurLabel(String color) {

        if (color == null) {
            return "";
        }
        switch (color) {
            case "GREEN" :
                return "Vert";
            case "ORANGE":
                return "Orange";
            case "YELLOW":
                return "Jaune";
            case "RED":
                return "Rouge";
            case "LIGHT_GRAY":
                return "Gris";
            case "PINK":
                return "Rose";
            case "BLUE":
                return "Bleu";
            case "SABLE":
                return "Sable";
            default:
                return "Blanc";
        }
    }
}
