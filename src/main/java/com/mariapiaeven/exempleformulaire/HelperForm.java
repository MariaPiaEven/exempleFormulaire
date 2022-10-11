package com.mariapiaeven.exempleformulaire;

import javax.swing.*;
import java.awt.*;

public class HelperForm {

    private HelperForm() {}

    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_LEFT = 2;

    public static Box generateField(String texteLabel, Component component){
        Box champs = Box.createHorizontalBox();
        champs.add(Box.createRigidArea(new Dimension(10,1)));
        JLabel label = new JLabel(texteLabel);
        label.setPreferredSize(new Dimension(120,30));
        champs.add(label);
        champs.add(component);
        champs.add(Box.createHorizontalGlue());

        return champs;
    }

    public static Box generateRow(
            JComponent component,
            int marginTop,
            int marginRight,
            int marginBottom,
            int marginLeft,
            int alignement
    ) {
        Box conteneurVertical = Box.createVerticalBox();
        //ajout de la marge verticale en hauteur
        conteneurVertical.add(Box.createRigidArea(new Dimension(1, marginTop)));

        Box conteneurHorizontal = Box.createHorizontalBox();
        conteneurVertical.add(conteneurHorizontal);

        //ajout de la marge verticale en bas
        conteneurVertical.add(Box.createRigidArea(new Dimension(1, marginBottom)));

        //ajout de la marge Horizontal a gauche
        conteneurHorizontal.add(Box.createRigidArea(new Dimension(marginLeft, 1)));

        if (alignement == ALIGN_RIGHT){
            conteneurHorizontal.add(Box.createHorizontalGlue());
        }
        conteneurHorizontal.add(component);

        //ajout de la marge horizontal a droite
        conteneurHorizontal.add(Box.createRigidArea(new Dimension(marginRight,1)));

        return conteneurVertical;

    }
}
