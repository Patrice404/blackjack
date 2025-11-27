package blackjack.vue;

import javax.swing.*;
import java.awt.*;

/**
 * Un bouton circulaire personnalisé, utilisé pour les actions du Blackjack.
 * Apparence : jeton de casino rouge avec texte centré et effets visuels.
 */
public class VueBouton extends JButton {

    public VueBouton(String texte) {
        super(texte);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setPreferredSize(new Dimension(100, 100)); // taille par défaut
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- Couleur selon l'état ---
        Color base = new Color(170, 0, 0);
        if (getModel().isPressed()) {
            base = new Color(120, 0, 0);
        } else if (getModel().isRollover()) {
            base = new Color(220, 0, 0);
        }

        // --- Ombre externe ---
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillOval(4, 6, getWidth() - 8, getHeight() - 6);

        // --- Cercle principal ---
        GradientPaint gradient = new GradientPaint(0, 0, base, 0, getHeight(), base.darker());
        g2.setPaint(gradient);
        g2.fillOval(0, 0, getWidth() - 6, getHeight() - 6);

        // --- Bordure blanche ---
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(1, 1, getWidth() - 8, getHeight() - 8);

        // --- Texte centré ---
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 4);

        g2.dispose();
    }
}
