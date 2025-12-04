package cartes.vue;

import java.awt.*;
import cartes.model.*;
import cartes.utils.*; // Ajout de l'import manquant pour EcouteurModele

/**
 * Représente la vue graphique d'un {@link Paquet} de cartes lorsque **toutes**
 * les cartes doivent être cachées (dos de carte).
 * <p>
 * Cette vue est typiquement utilisée pour représenter la **pioche** d'un jeu
 * de cartes. Elle affiche un dos de carte stylisé et la taille actuelle du paquet.
 */
public class VuePaquetCache extends VuePaquet {
    

    /**
     * Largeur fixe d'une carte dans cette vue.
     */
    private final int largeurCarte = 80;
    
    /**
     * Hauteur fixe d'une carte dans cette vue.
     */
    private final int hauteurCarte = 120;

    /**
     * Construit une vue pour un paquet de cartes cachées.
     * Le paquet est désigné par défaut comme "Pioche".
     *
     * @param paquet Le modèle {@link Paquet} de cartes à visualiser.
     */
    public VuePaquetCache(Paquet paquet) {
        super(paquet, "Pioche");
        // Le champ paquet est déjà initialisé dans le super()
        setPreferredSize(new Dimension(largeurCarte, hauteurCarte));
    }
    
    /**
     * Surcharge de la méthode de dessin de composant.
     * Dessine un dos de carte stylisé et opaque si le paquet n'est pas vide.
     * Si le paquet est vide, rien n'est dessiné.
     *
     * @param g L'objet graphique utilisé pour le dessin.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        if(paquet.getTaille()>0){
            int x = 0; // Position X pour commencer le dessin de la carte
            int y = 0; // Position Y pour commencer le dessin de la carte
            
            // 1. Bordure Blanche
            g2D.setColor(Color.WHITE);
            g2D.fillRoundRect(x, y, largeurCarte, hauteurCarte, 10, 10);

            // 2. Fond Rouge du dos de carte
            Color fond = new Color(180, 20, 20);
            g2D.setColor(fond);
            g2D.fillRoundRect(x + 4, y + 4, largeurCarte - 8, hauteurCarte - 8, 10, 10);
            
            // 3. Motif de diamants translucides
            g2D.setColor(new Color(255, 255, 255, 60)); // blanc translucide
            int step = 10;
            for (int i = x + 6; i < x + largeurCarte - 6; i += step) {
                for (int j = y + 6; j < y + hauteurCarte - 6; j += step) {
                    Polygon diamond = new Polygon();
                    diamond.addPoint(i, j + step / 2);
                    diamond.addPoint(i + step / 2, j);
                    diamond.addPoint(i + step, j + step / 2);
                    diamond.addPoint(i + step / 2, j + step);
                    g2D.fillPolygon(diamond);
                }
            }

            // 4. Contour noir
            g2D.setColor(Color.BLACK);
            g2D.drawRoundRect(x, y, largeurCarte, hauteurCarte, 10, 10);

            // 5. Dessin du symbole central
            g2D.setFont(new Font("Serif", Font.BOLD, 18));
            g2D.setColor(Color.WHITE);
            String symbole = "★"; 
            FontMetrics fm = g2D.getFontMetrics();
            int tx = x + (largeurCarte - fm.stringWidth(symbole)) / 2;
            int ty = y + (hauteurCarte + fm.getAscent()) / 2 - 5;
            g2D.drawString(symbole, tx, ty);

            // 6. Affichage de la taille (compte) du paquet en bas à droite
            g2D.setFont(new Font("SansSerif", Font.BOLD, 12));
            String texte = "" + paquet.getTaille();
            FontMetrics fm2 = g2D.getFontMetrics();
            g2D.setColor(Color.WHITE);
            g2D.drawString(texte, x + largeurCarte - fm2.stringWidth(texte) - 8, y + hauteurCarte - 8);

        }
            
    }
             
    /**
     * Méthode de l'interface {@code EcouteurModele}.
     * Est appelée chaque fois que le modèle {@link Paquet} change.
     * Elle déclenche le redessin de la vue pour mettre à jour l'affichage
     * (notamment le compte des cartes).
     *
     * @param source L'objet qui a notifié l'écouteur (ici, le Paquet).
     */
    @Override
    public void modeleMisAJour(Object source){
        repaint();
    }
      
    
}