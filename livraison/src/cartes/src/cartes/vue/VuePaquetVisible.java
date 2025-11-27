package cartes.vue;

import javax.swing.*;
import java.awt.*;
import cartes.model.*;

public class VuePaquetVisible extends VuePaquet {

    private boolean cacherPremiere;
    private int decalage = 25; // Décalage horizontal entre les cartes

    public VuePaquetVisible(Paquet paquet, boolean cacherPremiere) {
        super(paquet, "Vue");
        this.cacherPremiere = cacherPremiere;
        setLayout(null); // Positionnement absolu pour gérer le chevauchement
        setBackground(new Color(0, 100, 0)); // vert casino
        setPreferredSize(new Dimension(150, 120));
        modeleMisAJour(this);
    }

    /** Met à jour l’affichage du paquet */
    public void modeleMisAJour(Object object) {
        this.removeAll();

        int i = 0;
        for (Carte carte : paquet.getListeCartes()) {
            JPanel cartePanel = creerCartePanel(carte, cacherPremiere && i == 0);

            int x = i * decalage;
            int y = 20; // même ligne
            cartePanel.setBounds(x, y, 70, 100);
            this.add(cartePanel);
            i++;
        }

        revalidate();
        repaint();
    }

    /** Crée un panneau représentant une carte (rectangulaire, texte + couleur) */
    private JPanel creerCartePanel(Carte carte, boolean cachee) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        if (cachee) {
            panel.setBackground(Color.DARK_GRAY);
            JLabel label = new JLabel("??");
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(label);
        } else {
            panel.setBackground(Color.WHITE);
            JLabel label = new JLabel(carte.getHauteur() + symboleCouleur(carte.getCouleur()));
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(couleurTexte(carte.getCouleur()));
            panel.add(label);
        }

        return panel;
    }

    /** Symboles selon couleur */
    private String symboleCouleur(String couleur) {
        if (couleur.contains("Pique")) return "♠";
        if (couleur.contains("Coeur")) return "♥";
        if (couleur.contains("Carreau")) return "♦";
        if (couleur.contains("Trefles")) return "♣";
        return "?";
    }

    /** Couleur du texte selon la couleur */
    private Color couleurTexte(String couleur) {
        if (couleur.contains("Coeur") || couleur.contains("Carreau"))
            return Color.RED;
        return Color.BLACK;
    }

    public void setCacherPremiere(boolean cacherPremiere) {
        this.cacherPremiere = cacherPremiere;
        modeleMisAJour(this);
    }

    public void setDecalage(int decalage) {
        this.decalage = decalage;
        modeleMisAJour(this);
    }
}
