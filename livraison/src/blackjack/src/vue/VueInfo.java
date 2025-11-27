package blackjack.vue;

import javax.swing.*;

import blackjack.UnchangeableSettings;

import java.awt.*;

/**
 * Fenêtre d'information simple pour afficher un message stylé "casino".
 * Exemple : nouveaux tours, résultats, messages d’erreur, etc.
 */
public class VueInfo extends JDialog {

    public VueInfo(JFrame parent, String message) {
        super(parent, "Information", true); // fenêtre modale

        // ---- Style général ----
        getContentPane().setBackground(new Color(10, 60, 10));
        setLayout(new BorderLayout());
        setResizable(false);

        // ---- Label principal ----
        JLabel labelMessage = new JLabel(message, SwingConstants.CENTER);
        labelMessage.setFont(InterfaceSetting.TEXT_FONT);
        labelMessage.setForeground(InterfaceSetting.GOLD_COLOR); // doré
        labelMessage.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));
        add(labelMessage, BorderLayout.CENTER);

        // ---- Bouton OK ----
        JButton boutonOk = new JButton("OK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getModel().isPressed() ? new Color(180, 0, 0) :
                           getModel().isRollover() ? new Color(220, 0, 0) :
                           new Color(170, 0, 0));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 3);
                g2.dispose();
            }
        };
        boutonOk.setForeground(Color.WHITE);
        boutonOk.setFont(new Font("Arial", Font.BOLD, 14));
        boutonOk.setFocusPainted(false);
        boutonOk.setBorderPainted(false);
        boutonOk.setContentAreaFilled(false);
        boutonOk.setOpaque(false);
        boutonOk.setPreferredSize(new Dimension(100, 35));
        boutonOk.addActionListener(e -> dispose());

        JPanel panelBouton = new JPanel();
        panelBouton.setOpaque(false);
        panelBouton.add(boutonOk);
        add(panelBouton, BorderLayout.SOUTH);

        // ---- Taille et position ----
        pack();
        setSize(700, 160);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

}

