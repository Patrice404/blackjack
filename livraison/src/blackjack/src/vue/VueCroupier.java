package blackjack.vue;

import javax.swing.*;
import java.awt.*;

import blackjack.model.Croupier;
import cartes.vue.VuePaquetVisible;
import cartes.utils.EcouteurModele;
import cartes.vue.VuePaquetCache;

/**
 * VueCroupier — Représente le croupier visuellement :
 * - Nom affiché en haut
 * - Ses trois paquets : poche visible, main, pioche
 * - Son argent total en bas
 */
public class VueCroupier extends JPanel implements EcouteurModele{

    private Croupier croupier;
    private VuePaquetVisible vuePocheVisible;
    private VuePaquetVisible vueMain;
    private VuePaquetCache vuePioche;
    private VueArgent vueArgent;
    private JLabel nomLabel;

    public VueCroupier(Croupier croupier) {
        super();
        this.croupier = croupier;
        this.croupier.ajoutEcouteur(this);

        // 🎲 Style général
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0, 90, 0));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 200, 100), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        // 🧍 Nom du croupier
        nomLabel = new JLabel(this.getTitre(), SwingConstants.CENTER);
        nomLabel.setFont(InterfaceSetting.TEXT_FONT);
        nomLabel.setForeground(new Color(255, 215, 0)); // doré
        add(nomLabel, BorderLayout.NORTH);

        // ♠️ Vue des paquets
        JPanel panelPaquets = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        panelPaquets.setOpaque(false);

        vuePocheVisible = new VuePaquetVisible(croupier.getPocheVisible(), false);
        vuePocheVisible.setDecalage(10);
        vuePocheVisible.setPreferredSize(InterfaceSetting.DIMENSION_POCHE_VISIBLE);

        vueMain = new VuePaquetVisible(croupier.getMain(), false);
        vueMain.setDecalage(InterfaceSetting.DECALAGE_CARTE);
        vueMain.setPreferredSize(InterfaceSetting.DIMENSION_MAIN);

        vuePioche = new VuePaquetCache(croupier.getPocheCachee()); 
        vuePioche.setPreferredSize(InterfaceSetting.DIMENSION_PIOCHE);

        // Ajout dans l'ordre
        panelPaquets.add(creerEncadre("Visible", vuePocheVisible));
        panelPaquets.add(creerEncadre("Main", vueMain));
        panelPaquets.add(creerEncadre("Pioche", vuePioche));

        add(panelPaquets, BorderLayout.CENTER);

        // 💰 Argent du croupier
        JPanel bas = new JPanel(new BorderLayout());
        bas.setOpaque(false);
        bas.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        vueArgent = new VueArgent("Banque", this.croupier.getArgent());
        bas.add(vueArgent, BorderLayout.CENTER);

        add(bas, BorderLayout.SOUTH);
    }

     /** Retourne le texte complet du label (nom + score) */
    private String getTitre() {
        return "Croupier : " + croupier.getNom() + " : " + this.croupier.getScore();
    }

     /** Appelée quand le modèle change (depuis Joueur) */
    @Override
    public void modeleMisAJour(Object source) {
        if (source == this.croupier) {
            // Met à jour les sous-vues et le label
            SwingUtilities.invokeLater(() -> {
                nomLabel.setText(getTitre());
                revalidate();
                repaint();
            });
        }
    }

    /** Encadre un composant avec un titre visuel */
    private JPanel creerEncadre(String titre, JComponent contenu) {
        JPanel encadre = new JPanel(new BorderLayout());
        encadre.setOpaque(false);
        encadre.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                titre,
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE));
        encadre.add(contenu, BorderLayout.CENTER);
        return encadre;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, new Color(0, 100, 0),
                0, getHeight(), new Color(0, 60, 0));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /** Met à jour les vues selon le modèle */
    public void rafraichir() {
        vueMain.modeleMisAJour(croupier.getMain());
        vuePocheVisible.modeleMisAJour(croupier.getMain());
        vuePioche.modeleMisAJour(croupier.getMain());
        vueArgent.setValeur(croupier.getArgent().getMontant());
    }

    public Croupier getCroupier() {
        return croupier;
    }

}
