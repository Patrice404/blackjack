package blackjack.vue;

import javax.swing.*;
import java.awt.*;



public class VueFin extends JDialog {

    private boolean rejouer = false;

    public VueFin(JFrame parent, String message) {
        super(parent, "Fin de partie", true); // fenêtre modale

        // ---- Style général ----
        getContentPane().setBackground(new Color(10, 60, 10)); // vert foncé
        setLayout(new BorderLayout());
        setResizable(false);

        // ---- Message principal ----
      
        JLabel labelMessage = new JLabel( message +  "  Souhaitez-vous continuer à jouer ?",
            SwingConstants.CENTER
        );
        labelMessage.setFont(new Font("Georgia", Font.BOLD, 18));
        labelMessage.setForeground(new Color(255, 215, 0)); // doré
        labelMessage.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        add(labelMessage, BorderLayout.CENTER);

        // ---- Panel des boutons ----
        JPanel panelBoutons = new JPanel();
        panelBoutons.setOpaque(false);
        panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));

        // ---- Boutons ----
        JButton boutonOui = createStyledButton("Oui", new Color(0, 150, 0));
        JButton boutonNon = createStyledButton("Non", new Color(170, 0, 0));

        // ---- Actions ----
         boutonOui.addActionListener(e -> {
             rejouer = true;
             dispose();
         });

         boutonNon.addActionListener(e -> {
             rejouer = false;
             dispose();
         });

        panelBoutons.add(boutonOui);
        panelBoutons.add(boutonNon);
        add(panelBoutons, BorderLayout.SOUTH);

        // ---- Taille et position ----
        pack();
        setSize(700, 200);
        setLocationRelativeTo(parent);
    }

    /** 🔹 Méthode utilitaire pour créer un bouton stylé */
    private JButton createStyledButton(String text, Color baseColor) {
        JButton bouton = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getModel().isPressed() ? baseColor.darker() :
                           getModel().isRollover() ? baseColor.brighter() :
                           baseColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 3);
                g2.dispose();
            }
        };
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setFocusPainted(false);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setOpaque(false);
        bouton.setPreferredSize(new Dimension(100, 35));
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return bouton;
    }

   


    // Pour tester rapidement :
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            new VueFin(frame, "Le joueur 1 à gagner").setVisible(true);
            frame.dispose();

        });
    }

}

    
    

