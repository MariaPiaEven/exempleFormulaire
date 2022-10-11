import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

        //------- BOUTON VALIDER FORMULAIRE ----------------
        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> System.out.println("Formulaire validé"));
        boutonValider.setSize(new Dimension(100, 30));

        //---------- DISPOSITION DES COMPOSANTS -------
        panneau.add(
                HelperForm.generateRow(boutonTheme, 10, 10,
                        0, 0, HelperForm.ALIGN_RIGHT),
                BorderLayout.NORTH);

        panneau.add(
                HelperForm.generateRow(boutonValider, 0, 10,
                        10, 0, HelperForm.ALIGN_RIGHT),
                BorderLayout.SOUTH);

        //--- FORMULAIRE ---
        Box formulaire = Box.createVerticalBox();
        panneau.add(formulaire, BorderLayout.CENTER);

        String[]listeCivilites = {"Monsieur", "Madame", "Mademoiselle", "Autre"};
        JComboBox<String> selectCivilite = new JComboBox<>(listeCivilites);
        selectCivilite.setMaximumSize(new Dimension(200,30));

        formulaire.add(HelperForm.generateField("Civilité", selectCivilite));

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

        if (choixUtilisateur == JOptionPane.YES_OPTION){
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


