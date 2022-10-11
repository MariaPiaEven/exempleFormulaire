import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {

    protected boolean themeSombreActif = true;
    protected int defaultMargin = 10;

    public FenetrePrincipale() {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //ajout du panneau principal avec un layout de 5 zones (north, south, east, west, center)
        JPanel panneau = new JPanel(new BorderLayout());
        setContentPane(panneau);

        //------- BOUTON THEME ----------------

        JButton boutonTheme = new JButton("Changer le theme");
        panneau.add(boutonTheme, BorderLayout.NORTH);

        boutonTheme.addActionListener(
                e -> {
                    try {
                        if (themeSombreActif) {
                            UIManager.setLookAndFeel(new FlatLightLaf());
                        } else {
                            UIManager.setLookAndFeel(new FlatDarculaLaf());
                        }
                        SwingUtilities.updateComponentTreeUI(this);
                        themeSombreActif = !themeSombreActif;

                    } catch (UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        //------- BOUTON VALIDER FORMULAIRE ----------------
        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> System.out.println("Formulaire validé"));
        boutonValider.setSize(new Dimension(100, 30));

        //---------- DISPOSITION DES COMPOSANTS -------
        Box conteneurZoneBoutonOption = Box.createVerticalBox();
        panneau.add(conteneurZoneBoutonOption, BorderLayout.NORTH);

        conteneurZoneBoutonOption.add(
                Box.createRigidArea(new Dimension(1,defaultMargin)));
        Box zoneBoutonOption = Box.createHorizontalBox();
        conteneurZoneBoutonOption.add(zoneBoutonOption);

        zoneBoutonOption.add(Box.createHorizontalGlue());
        zoneBoutonOption.add(boutonTheme);
        zoneBoutonOption.add(Box.createRigidArea(
                new Dimension(defaultMargin,1)));

        Box containerZoneBoutonAction = Box.createVerticalBox();
        panneau.add(containerZoneBoutonAction, BorderLayout.SOUTH);

        Box zoneBoutonAction = Box.createHorizontalBox();
        containerZoneBoutonAction.add(zoneBoutonAction);
        containerZoneBoutonAction.add(
                Box.createRigidArea(new Dimension(1,defaultMargin)));

        zoneBoutonAction.add(Box.createHorizontalGlue());
        zoneBoutonAction.add(boutonValider);
        zoneBoutonAction.add(Box.createRigidArea(
                new Dimension(defaultMargin,1)));

        setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new FenetrePrincipale();
    }
}


