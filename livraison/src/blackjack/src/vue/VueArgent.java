package blackjack.vue;
import cartes.utils.*;

import javax.swing.*;
import java.awt.*;
import blackjack.model.Argent;
import cartes.utils.EcouteurModele;

/**
 * VueArgent — représente graphiquement une somme d'argent.
 * Affiche le montant dans un rectangle stylisé avec une couleur et un texte formaté.
 */
public class VueArgent extends JPanel implements EcouteurModele {

    private Argent argent;
    private JLabel labelValeur;

    public VueArgent(String titre, Argent argent) {
        this.argent = argent;
        this.argent.ajoutEcouteur(this);

        setPreferredSize(InterfaceSetting.ARGENT_DIMENSION);
        setBackground(InterfaceSetting.SOMBRE_COLOR); // fond sombre élégant
        setBorder(BorderFactory.createLineBorder(InterfaceSetting.GOLD_COLOR, 3, true)); // doré
        setLayout(new BorderLayout());

        // --- Titre en haut ---
        JLabel labelTitre = new JLabel(titre, SwingConstants.CENTER);
        labelTitre.setFont(InterfaceSetting.TEXT_FONT);
        labelTitre.setForeground(InterfaceSetting.GOLD_COLOR); // doré
        add(labelTitre, BorderLayout.NORTH);

        // --- Valeur en grand ---
        labelValeur = new JLabel(formatValeur(argent.getMontant()), SwingConstants.CENTER);
        labelValeur.setFont(InterfaceSetting.MONTANT_FONT);
        labelValeur.setForeground(Color.WHITE);
        add(labelValeur, BorderLayout.CENTER);

        // On s’abonne au modèle
        argent.ajoutEcouteur(this);
    }

    /** Met à jour la valeur affichée lorsque le modèle change */
    @Override
    public void modeleMisAJour(Object source) {
        if (source == this.argent) {
            labelValeur.setText(formatValeur(argent.getMontant()));
        }
    }

    /** Formate la valeur (ex: 150.00 €) */
    private String formatValeur(float montant) {
        return String.format("%.2f €", montant);
    }

    /** Permet de changer le montant manuellement (utile pour les tests) */
    public void setValeur(float nouvelleValeur) {
        labelValeur.setText(formatValeur(nouvelleValeur));
    }
}
