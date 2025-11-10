
package cartes.vue;

import java.awt.*;
import cartes.model.*;;

public class VuePaquetCache extends VuePaquet {
    
    //private Paquet paquet;

    private final int largeurCarte = 80;
    private final int hauteurCarte = 120;

    public VuePaquetCache(Paquet paquet) {
        super(paquet, "Pioche");
        this.paquet = paquet;
        setPreferredSize(new Dimension(80, 120));
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        if(paquet.getTaille()>0){
            int x = 10;
            int y = 10;
            g2D.setColor(Color.WHITE);
            g2D.fillRoundRect(x, y, largeurCarte, hauteurCarte, 10, 10);

            //Fond du dos de carte
            Color fond = new Color(180, 20, 20);
            g2D.setColor(fond);
            g2D.fillRoundRect(x + 4, y + 4, largeurCarte - 8, hauteurCarte - 8, 10, 10);
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

            // Contour noir
            g2D.setColor(Color.BLACK);
            g2D.drawRoundRect(x, y, largeurCarte, hauteurCarte, 10, 10);


            // ---- Logo central ou symbole ----
            g2D.setFont(new Font("Serif", Font.BOLD, 18));
            g2D.setColor(Color.WHITE);
            String symbole = "★"; // tu peux mettre "♠", "♦", "♥", "♣", "J", etc.
            FontMetrics fm = g2D.getFontMetrics();
            int tx = x + (largeurCarte - fm.stringWidth(symbole)) / 2;
            int ty = y + (hauteurCarte + fm.getAscent()) / 2 - 5;
            g2D.drawString(symbole, tx, ty);

            // ---- Compteur de cartes (petit et discret) ----
            g2D.setFont(new Font("SansSerif", Font.BOLD, 12));
            String texte = "" + paquet.getTaille();
            FontMetrics fm2 = g2D.getFontMetrics();
            g2D.setColor(Color.WHITE);
            g2D.drawString(texte, x + largeurCarte - fm2.stringWidth(texte) - 8, y + hauteurCarte - 8);

        }
            
    }
             
    @Override
    public void modeleMisAJour(Object source){
        repaint();
    }
      
    
}