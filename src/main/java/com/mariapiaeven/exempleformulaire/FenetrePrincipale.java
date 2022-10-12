package com.mariapiaeven.exempleformulaire;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.mariapiaeven.exempleformulaire.models.Pays;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

public class FenetrePrincipale extends JFrame implements WindowListener {

    protected boolean themeSombreActif = true;
    protected int defaultMargin = 10;

    public FenetrePrincipale() {
        setSize(500, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(this);

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

        //---------- DISPOSITION DES COMPOSANTS -------
        panneau.add(
                HelperForm.generateRow(boutonTheme, 10, 10,
                        0, 0, HelperForm.ALIGN_RIGHT),
                BorderLayout.NORTH);

        //--- FORMULAIRE ---
        Box formulaire = Box.createVerticalBox();
        panneau.add(formulaire, BorderLayout.CENTER);

        //--- LISTE CIVILITE---

        String[] listeCivilites = {"Monsieur", "Madame", "Mademoiselle", "Autre"};
        JComboBox<String> selectCivilite = new JComboBox<>(listeCivilites);

        formulaire.add(HelperForm.generateField("Civilité", selectCivilite));

        //------ CHAMPS TEXT / NOM ---------
        ChampsSaisie champsNom = new ChampsSaisie("[\\p{L}-'\s]");
        formulaire.add(HelperForm.generateField("Nom", champsNom));

        //------ CHAMPS TEXT / PRENOM ---------
        JTextField champsPrenom = new JTextField();
        formulaire.add(HelperForm.generateField("Prénom", champsPrenom));

        //--- LISTE PAYS ---
        Pays[] listePays = {
                new Pays("France", "FR", "fr.png"),
                new Pays("Royaume-Unis", "GBR", "gb.png"),
                new Pays("Pérou", "PE", "pe.png"),
        };

        JComboBox<Pays> selectPays = new JComboBox<>(listePays);

        selectPays.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                Pays pays = (Pays) value;
                setText(pays.getNom());

                try {
                    Image image = ImageIO.read(new File("src/main/resources/drapeaux/" + pays.getImage()));

                    //On redimensionne l'image
                    Image resizedImage = image.getScaledInstance(20, 16, Image.SCALE_SMOOTH);

                    setIconTextGap(10);

                    //on change l'icone du JLabel par l'image redimensionée
                    setIcon(new ImageIcon(resizedImage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return this;
            }
        });

        formulaire.add(HelperForm.generateField("Pays", selectPays));

        //------- BOUTON VALIDER FORMULAIRE ----------------
        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> {

            boolean erreurNom = false;
            boolean erreurPrenom = false;
            String message = "Le formulaire comporte des erreurs : ";

            champsNom.resetMessage();
//            champsNom.setBorder(BorderFactory.createEmptyBorder());
            champsPrenom.setBorder(BorderFactory.createEmptyBorder());

            if (champsNom.getText().equals("")) {
                erreurNom = true;
                message += "\n - Nom est obligatoire, ";
                champsNom.erreur("Champs obligatoire");
            }

            if (champsPrenom.getText().equals("")) {
                erreurPrenom = true;
                message += "\n - Prénom est obligatoire, ";
                champsPrenom.setBorder(BorderFactory.createLineBorder(Color.red));
            }

            //supprimer les deux derniers caracteres
            message = message.substring(0, message.length() - 1);

            if (erreurNom || erreurPrenom) {
                JOptionPane.showMessageDialog(this, message, "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
            }

        });

        boutonValider.setSize(new Dimension(100, 30));

        //---------- BOUTON EN BAS -------
        panneau.add(
                HelperForm.generateRow(boutonValider, 0, 10,
                        10, 0, HelperForm.ALIGN_RIGHT),
                BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new FenetrePrincipale();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        String[] choix = {"Oui", "Ne pas fermer l'application"};
        int choixUtilisateur = JOptionPane.showOptionDialog(
                this,
                "Voulez-vos vraiment fermer l'application",
                "Confirmer",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                choix[1]
        );

        if (choixUtilisateur == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}


