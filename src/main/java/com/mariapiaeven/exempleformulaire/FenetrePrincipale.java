package com.mariapiaeven.exempleformulaire;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.mariapiaeven.exempleformulaire.models.Pays;
import com.mariapiaeven.exempleformulaire.models.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FenetrePrincipale extends JFrame implements WindowListener {

    protected boolean themeSombreActif = true;
    protected int defaultMargin = 10;

    protected JComboBox<String> selectCivilite;
    protected ChampsSaisie champsNom;
    protected ChampsSaisie champsPrenom;
    protected ChampsSaisie champsEmail;
    protected JComboBox<Pays> selectPays;
    protected ChampsSaisie champsAge;
    protected JCheckBox champsMarie;


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
        selectCivilite = new JComboBox<>(listeCivilites);

        formulaire.add(HelperForm.generateField("Civilité", selectCivilite));

        //------ CHAMPS TEXT / NOM ---------
        champsNom = new ChampsSaisie("[\\p{L}\s'-]");
        formulaire.add(HelperForm.generateField("Nom", champsNom));

        //------ CHAMPS TEXT / PRENOM ---------
        champsPrenom = new ChampsSaisie("[\\p{L}\s'-]");
        formulaire.add(HelperForm.generateField("Prénom", champsPrenom));

        //------ CHAMPS TEXT / EMAIL---------
        champsEmail = new ChampsSaisie("[a-zA-Z0-9@\\.-]");
        formulaire.add(HelperForm.generateField("Email", champsEmail));
        champsEmail.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (champsEmail.getText().equals("")) {
                    champsEmail.erreur("Champs obligatoire");
                } else {
                    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(champsEmail.getText());
                    if (!matcher.matches()) {
                        champsEmail.erreur("Format invalide");
                    }
                }
            }
        });

        //--- LISTE PAYS ---
        Pays[] listePays = {
                new Pays("France", "FR", "fr.png"),
                new Pays("Royaume-Unis", "GBR", "gb.png"),
                new Pays("Pérou", "PE", "pe.png"),
        };

        selectPays = new JComboBox<>(listePays);

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

        //------ CHAMPS TEXT / AGE---------
        champsAge = new ChampsSaisie("[0-9]");
        formulaire.add(HelperForm.generateField("Age", champsAge, 50));

        //------- CHAMPS CHECKBOX : MARIE/PACSE ----------------
        champsMarie = new JCheckBox();
        formulaire.add(HelperForm.generateField("Marie/Pacse", champsMarie));

        //------- BOUTON VALIDER FORMULAIRE ----------------
        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> {

            boolean erreurNom = false;
            boolean erreurPrenom = false;
            boolean erreurAge = false;
            boolean erreurEmail = false;
            String message = "Le formulaire comporte des erreurs : ";

            champsNom.resetMessage();
            champsPrenom.resetMessage();
            champsAge.resetMessage();

            //------ validateur nom -------
            if (champsNom.getText().equals("")) {
                erreurNom = true;
                message += "\n - Nom est obligatoire, ";
                champsNom.erreur("Champs obligatoire");
            }

            //------ validateur Prenom -------
            if (champsPrenom.getText().equals("")) {
                erreurPrenom = true;
                message += "\n - Prénom est obligatoire, ";
                champsPrenom.erreur("Champs obligatoire");
            }

            //------ validateur age -------
            if (champsAge.getText().equals("")) {
                erreurAge = true;
                message += "\n - Age est obligatoire, ";
                champsAge.erreur("Champs obligatoire");
            } else {
                int age = Integer.parseInt(champsAge.getText());

                if (age <= 0 || age > 150) {
                    erreurAge = true;
                    message += "\n - Age avec une valeur impossible,";
                    champsAge.erreur("Valeur impossible");
                }
            }

            //------ validateur Email -------
            if (champsEmail.getText().equals("")) {
                erreurEmail = true;
                message += "\n - Email est obligatoire, ";
                champsEmail.erreur("Champs obligatoire");
            } else {
                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(champsEmail.getText());
                if (!matcher.matches()) {
                    erreurEmail = true;
                    message += "\n - Email format invalide ";
                    champsEmail.erreur("Format invalide");
                }
            }

            //supprimer la dernier des virgules
            message = message.substring(0, message.length() - 1);

            if (erreurNom || erreurPrenom || erreurAge || erreurEmail) {
                JOptionPane.showMessageDialog(this, message, "Erreur de saisie", JOptionPane.WARNING_MESSAGE);
            } else {
                //----- si il n'y a pas d'erreur ---------
                Utilisateur nouvelleUtilisateur = new Utilisateur(
                        //Cast toutes les elements dans liste deroulante(l'objet) pour les convertir en chaine de texte
                        (String) selectCivilite.getSelectedItem(),
                        champsNom.getText(),
                        champsPrenom.getText(),
                        champsEmail.getText(),
                        //Cast toutes les elements de la class Pays
                        (Pays) selectPays.getSelectedItem(),
                        //Transforme les chaine de texte en nombre
                        Integer.parseInt(champsAge.getText()),
                        champsMarie.isSelected()
                );

                ObjectOutputStream oos = null;
                try {
                    FileOutputStream fichier = new FileOutputStream("personne.eesc");

                    oos = new ObjectOutputStream(fichier);
                    oos.writeObject(nouvelleUtilisateur);
                    oos.flush();

                    JOptionPane.showMessageDialog(
                            this,
                            "L'utilisateur " + nouvelleUtilisateur.getNom() + " a bien été ajouté"
                    );

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Impossible d'enregistrer l'utilisateur"
                    );
                }

            }

        });

        boutonValider.setSize(new Dimension(100, 30));

        //---------- BOUTON EN BAS -------
        panneau.add(
                HelperForm.generateRow(boutonValider, 0, 10,
                        10, 0, HelperForm.ALIGN_RIGHT),
                BorderLayout.SOUTH);

        ouvrirFichier();

        setVisible(true);
    }

    public void ouvrirFichier(){

        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream("personne.eesc");
            ois = new ObjectInputStream(fichier);
            Utilisateur utilisateurFichier = (Utilisateur) ois.readObject();

            //-------- hydratation --------
            selectCivilite.setSelectedItem(utilisateurFichier.getCivilite());
            champsNom.getTextField().setText(utilisateurFichier.getNom());
            champsPrenom.getTextField().setText(utilisateurFichier.getPrenom());
            champsEmail.getTextField().setText(utilisateurFichier.getEmail());
            selectPays.setSelectedItem(utilisateurFichier.getPays());
            champsAge.getTextField().setText(
                    String.valueOf(utilisateurFichier.getAge())
            );
            champsMarie.setSelected(utilisateurFichier.isMarie());

            ois.close();

        } catch (FileNotFoundException e) {
            System.out.println("Premiere fois qu'on ouvre l'application");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Impossible d'ouvrir le fichier"
            );
        } catch (ClassNotFoundException | ClassCastException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fichier corrompu"
            );
        }

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


