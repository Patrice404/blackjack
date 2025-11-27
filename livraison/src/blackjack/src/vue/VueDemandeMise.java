package blackjack.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre modale qui demande au joueur d'entrer un nom et une mise.
 * Style "casino" vert et or.
 */
public class VueDemandeMise extends JDialog {

    private float mise = 0; // 💰 mise saisie
    private String nom = ""; // 🧍 nom du joueur
    private JTextField champNom;
    private JTextField champMise;
    private boolean validee = false;

    public VueDemandeMise(JFrame parent) {
        super(parent, "Nouvelle mise", true); // fenêtre modale

        // 🎨 Style général
        getContentPane().setBackground(new Color(20, 70, 20));
        setLayout(new BorderLayout(15, 15));
        setResizable(false);

        // ---- Titre ----
        JLabel titre = new JLabel("🎲 Entrez votre nom et votre mise :", SwingConstants.CENTER);
        titre.setFont(new Font("Georgia", Font.BOLD, 20));
        titre.setForeground(new Color(255, 215, 0)); // doré
        titre.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        add(titre, BorderLayout.NORTH);

        // ---- Centre : champs nom et mise ----
        JPanel panelCentre = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentre.setOpaque(false);
        panelCentre.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel labelNom = creerLabel("Nom du joueur :");
        JLabel labelMise = creerLabel("Montant de la mise :");

        champNom = creerChampTexte();
        champMise = creerChampTexte();

        panelCentre.add(labelNom);
        panelCentre.add(champNom);
        panelCentre.add(labelMise);
        panelCentre.add(champMise);
        add(panelCentre, BorderLayout.CENTER);

        // ---- Boutons ----
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBoutons.setOpaque(false);

        JButton boutonValider = creerBouton("Valider");
        JButton boutonAnnuler = creerBouton("Annuler");

        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String saisieNom = champNom.getText().trim();
                    if (saisieNom.isEmpty()) {
                        JOptionPane.showMessageDialog(VueDemandeMise.this,
                                "Veuillez entrer un nom de joueur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    float valeur = Float.parseFloat(champMise.getText());
                    if (valeur <= 0) {
                        JOptionPane.showMessageDialog(VueDemandeMise.this,
                                "La mise doit être positive.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    nom = saisieNom;
                    mise = valeur;
                    validee = true;
                    setVisible(false);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VueDemandeMise.this,
                            "Veuillez entrer un nombre valide pour la mise.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        boutonAnnuler.addActionListener(e -> {
            validee = false;
            dispose();
        });

        panelBoutons.add(boutonValider);
        panelBoutons.add(boutonAnnuler);
        add(panelBoutons, BorderLayout.SOUTH);

        // ---- Taille et centrage ----
        pack();
        setSize(400, 230);
        setLocationRelativeTo(parent);
    }

    /** Crée un label stylé doré */
    private JLabel creerLabel(String texte) {
        JLabel label = new JLabel(texte);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(255, 215, 0)); // doré
        return label;
    }

    /** Crée un champ de texte stylé casino */
    private JTextField creerChampTexte() {
        JTextField champ = new JTextField();
        champ.setFont(new Font("Arial", Font.BOLD, 16));
        champ.setHorizontalAlignment(JTextField.CENTER);
        champ.setBackground(new Color(10, 50, 10));
        champ.setForeground(Color.WHITE);
        champ.setCaretColor(Color.WHITE);
        champ.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true));
        return champ;
    }

    /** 🔘 Crée un bouton stylé casino */
    private JButton creerBouton(String texte) {
        JButton b = new JButton(texte) {
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
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setPreferredSize(new Dimension(110, 35));
        return b;
    }

    // ---- Getters ----
    public float getMise() {
        return mise;
    }

    public String getNom() {
        return nom;
    }

    public boolean isValidee() {
        return validee;
    }

    /** 🧩 Exemple d’utilisation */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueDemandeMise vue = new VueDemandeMise(null);
            vue.setVisible(true);
            if (vue.isValidee()) {
                System.out.println("Nom : " + vue.getNom());
                System.out.println("Mise : " + vue.getMise());
            } else {
                System.out.println("Action annulée.");
            }
        });
    }
}
