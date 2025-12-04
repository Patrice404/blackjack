package cartes.vue;

import javax.swing.*;
import java.awt.*;
import cartes.model.*;

/**
 * Représente une vue graphique d'un {@link Paquet} de cartes où les cartes sont
 * affichées, généralement visibles, en chevauchement horizontal.
 * <p>
 * Cette vue est typiquement utilisée pour représenter la main d'un joueur,
 * la défausse ou le tapis. L'affichage peut être configuré pour cacher
 * la première carte (la carte du dessus).
 */
public class VuePaquetVisible extends VuePaquet {

    /**
     * Indicateur pour déterminer si la première carte (celle du dessus, index 0)
     * doit être affichée face cachée.
     */
    private boolean cacherPremiere;
    
    /**
     * Décalage horizontal (en pixels) appliqué entre chaque carte consécutive
     * pour créer un effet de chevauchement.
     */
    private int decalage = 25; 

    /**
     * Construit une vue d'un paquet de cartes visibles.
     *
     * @param paquet Le modèle {@link Paquet} de cartes à visualiser.
     * @param cacherPremiere Si {@code true}, la première carte sera affichée face cachée.
     */
    public VuePaquetVisible(Paquet paquet, boolean cacherPremiere) {
        super(paquet, "Vue");
        this.cacherPremiere = cacherPremiere;
        // Utilisation d'un Layout null pour positionner les cartes manuellement et créer le chevauchement.
        setLayout(null); 
        setBackground(new Color(0, 100, 0)); // Fond vert pour simuler un tapis de jeu
        setPreferredSize(new Dimension(150, 120));
        // Force la première mise à jour de l'affichage
        modeleMisAJour(this);
    }

    /**
     * Implémentation de la méthode {@code modeleMisAJour} de l'interface {@code EcouteurModele}.
     * Cette méthode est appelée lors de tout changement dans le modèle {@link Paquet}.
     * Elle vide le panneau et recrée l'affichage de toutes les cartes en chevauchement.
     *
     * @param object L'objet qui a notifié l'écouteur.
     */
    public void modeleMisAJour(Object object) {
        // Supprime tous les composants (cartes) existants
        this.removeAll();

        int i = 0;
        for (Carte carte : paquet.getListeCartes()) {
            // Crée le panneau pour la carte courante. La première carte est potentiellement cachée.
            JPanel cartePanel = creerCartePanel(carte, cacherPremiere && i == 0);

            // Positionnement en chevauchement
            int x = i * decalage;
            int y = 20; 
            
            // Les dimensions de la carte sont fixées ici (70x100)
            cartePanel.setBounds(x, y, 70, 100); 
            this.add(cartePanel);
            i++;
        }

        // Calcule la nouvelle taille préférée basée sur le nombre de cartes et le décalage
        int newWidth = (paquet.getTaille() > 0) ? decalage * (paquet.getTaille() - 1) + 70 + 20 : 150;
        setPreferredSize(new Dimension(newWidth, 120));
        
        // Signalisation à Swing que la structure des composants a changé
        revalidate(); 
        repaint();
    }

    /** * Crée un panneau Swing ({@code JPanel}) pour représenter l'apparence graphique d'une carte.
     * Le panneau est soit face cachée, soit affiche la hauteur et le symbole de la carte.
     *
     * @param carte L'objet {@link Carte} à représenter.
     * @param cachee Si {@code true}, affiche le dos de carte.
     * @return Un {@code JPanel} représentant visuellement la carte.
     */
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
            // Récupère le symbole et la couleur pour l'affichage
            String symbole = symboleCouleur(carte.getSymbole());
            Color couleur = couleurTexte(carte.getSymbole());
            
            JLabel label = new JLabel(carte.getHauteur() + symbole);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(couleur);
            panel.add(label);
        }

        return panel;
    }

    /** * Détermine le symbole Unicode à utiliser pour la couleur de la carte.
     *
     * @param couleur La couleur de la carte (ex: "Pique♠").
     * @return Le symbole Unicode correspondant (ex: "♠").
     */
    private String symboleCouleur(String couleur) {
        if (couleur.contains("Pique")) return "♠";
        if (couleur.contains("Coeur")) return "♥";
        if (couleur.contains("Carreau")) return "♦";
        if (couleur.contains("Trefles")) return "♣";
        return "?";
    }

    /** * Détermine la couleur de texte (rouge ou noir) à utiliser pour les symboles.
     *
     * @param couleur La couleur de la carte (ex: "Coeur♥").
     * @return {@code Color.RED} pour Coeur et Carreau, {@code Color.BLACK} pour les autres.
     */
    private Color couleurTexte(String couleur) {
        if (couleur.contains("Coeur") || couleur.contains("Carreau"))
            return Color.RED;
        return Color.BLACK;
    }

    /**
     * Définit si la première carte doit être cachée ou non et met à jour l'affichage.
     *
     * @param cacherPremiere Nouvelle valeur du flag.
     */
    public void setCacherPremiere(boolean cacherPremiere) {
        this.cacherPremiere = cacherPremiere;
        modeleMisAJour(this);
    }

    /**
     * Définit le décalage horizontal entre les cartes et met à jour l'affichage.
     *
     * @param decalage Le nouveau décalage horizontal en pixels.
     */
    public void setDecalage(int decalage) {
        this.decalage = decalage;
        modeleMisAJour(this);
    }
}