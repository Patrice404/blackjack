package blackjack.vue;

import javax.swing.*;
import java.awt.*;

import blackjack.model.*;
import cartes.utils.EcouteurModele;
import cartes.vue.VuePaquetVisible;

public class VueJoueur extends JPanel implements EcouteurModele {

    private Joueur joueur;
    private VuePaquetVisible vueMain;
    private VueArgent vueArgentTotal;
    private VueArgent vueMise;
    private JLabel nomLabel;

    public VueJoueur(Joueur joueur, boolean cacherPremiereCarte) {
        super();
        this.joueur = joueur;
        this.joueur.ajoutEcouteur(this); 

        setLayout(new BorderLayout());
        setBackground(new Color(0, 100, 0)); 
        setPreferredSize(InterfaceSetting.DIMENSION_JOUEUR);

        // ---- Nom du joueur + score ----
        nomLabel = new JLabel(getTitre(), SwingConstants.CENTER);
        nomLabel.setFont(InterfaceSetting.TEXT_FONT);
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(nomLabel, BorderLayout.NORTH);

        // ---- Vue de la main (cartes) ----
        vueMain = new VuePaquetVisible(joueur.getMain(), cacherPremiereCarte);
        vueMain.setOpaque(false);
        vueMain.setDecalage(InterfaceSetting.DECALAGE_CARTE);
        add(vueMain, BorderLayout.CENTER);

        // ---- Zone argent + mise ----
        JPanel panelBas = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBas.setOpaque(false);
        panelBas.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        vueArgentTotal = new VueArgent("Argent", joueur.getArgent());
        vueMise = new VueArgent("Mise", joueur.getMise());

        panelBas.add(vueArgentTotal);
        panelBas.add(vueMise);
        add(panelBas, BorderLayout.SOUTH);
    }

    /** Retourne le texte complet du label (nom + score) */
    private String getTitre() {
        return joueur.getNom() + " : " + joueur.getScore();
    }

    /** Appelée quand le modèle change (depuis Joueur) */
    @Override
    public void modeleMisAJour(Object source) {
        if (source == joueur) {
            // Met à jour les sous-vues et le label
            SwingUtilities.invokeLater(() -> {
                nomLabel.setText(getTitre());
                revalidate();
                repaint();
            });
        }
    }

    public void rafraichir() {
        nomLabel.setText(getTitre());
        vueMain.modeleMisAJour(joueur.getMain());
        vueArgentTotal.setValeur(joueur.getArgent().getMontant());
        vueMise.setValeur(joueur.getMise().getMontant());
        revalidate();
        repaint();
    }

    public Joueur getJoueur() { return joueur; }
    public VuePaquetVisible getVueMain() { return vueMain; }
    public VueArgent getVueArgentTotal() { return vueArgentTotal; }
    public VueArgent getVueMise() { return vueMise; }
}
