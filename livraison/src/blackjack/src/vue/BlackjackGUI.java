package blackjack.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import blackjack.UnchangeableSettings;
import blackjack.model.*;
import blackjack.model.Action;

/**
 * Fenêtre principale représentant le plateau complet du Blackjack.
 * Contient le croupier, les joueurs et les actions possibles.
 */
public class BlackjackGUI extends JFrame implements ActionListener {

    private Blackjack blackjack;
    private VueCroupier vueCroupier;
    private ArrayList<VueJoueur> vuesJoueurs;
    private VueAction vueAction;
    private JPanel plateauPanel;
    private VueControle vueControle;
    private boolean miser;
    private VueDemandeMise vueDemandeMise;
    private Action actionChoisie = null;

    public BlackjackGUI(Blackjack blackjack) {
        super("🎰 Blackjack - Table de jeu");
        UnchangeableSettings.loadSettings();

        this.blackjack = blackjack;
        this.vueDemandeMise = new VueDemandeMise(this);
        this.vuesJoueurs = new ArrayList<>();

        // ---- Paramètres de la fenêtre ----
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(InterfaceSetting.WIDTH - 50, InterfaceSetting.HEIGHT - 20);
        setLocationRelativeTo(null);
        setResizable(false);

        // ---- Conteneur principal (plateau) ----
        plateauPanel = this.plateauPanel();
        plateauPanel.setLayout(new BorderLayout());
        plateauPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ---- Vue du croupier ----
        vueCroupier = new VueCroupier(blackjack.getCroupier());
        vueCroupier.setPreferredSize(InterfaceSetting.DIMENSION_CROUPIER);
        plateauPanel.add(vueCroupier, BorderLayout.NORTH);

        // ---- Vue des joueurs ----
        JPanel panelJoueurs = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panelJoueurs.setOpaque(false);

        vueControle = new VueControle(this);
        panelJoueurs.add(vueControle);

        for (Joueur joueur : blackjack.getJoueurs()) {
            VueJoueur vueJoueur = new VueJoueur(joueur, false);
            vuesJoueurs.add(vueJoueur);
            panelJoueurs.add(vueJoueur);
        }

        // ---- Vue des actions ----
        vueAction = new VueAction(this);
        panelJoueurs.add(vueAction);

        plateauPanel.add(panelJoueurs, BorderLayout.SOUTH);

        setContentPane(plateauPanel);

        this.blackjack.faireMiserIA();
        this.miser = this.blackjack.queDesIA();
    }

    private JPanel plateauPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dégradé de fond (vert foncé → noir)
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 90, 0),
                        0, getHeight(), Color.BLACK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Halo central (effet table de jeu)
                RadialGradientPaint rgp = new RadialGradientPaint(
                        new Point(getWidth() / 2, getHeight() / 2),
                        300,
                        new float[] { 0f, 1f },
                        new Color[] { new Color(0, 150, 0, 180), new Color(0, 0, 0, 0) });
                g2.setPaint(rgp);
                g2.fillOval(getWidth() / 2 - 300, getHeight() / 2 - 150, 600, 300);
            }
        };
    }

    /** Met à jour les différentes vues */
    public void rafraichir() {
        vueCroupier.rafraichir();
        for (VueJoueur vue : vuesJoueurs) {
            vue.rafraichir();
        }
        repaint();
    }

    // --- Getters ---
    public VueCroupier getVueCroupier() {
        return vueCroupier;
    }

    public ArrayList<VueJoueur> getVuesJoueurs() {
        return vuesJoueurs;
    }

    public VueAction getVueAction() {
        return null;// vueAction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vueControle.getDistribuerBtn())) {
            if (miser) {
                new Thread(() -> this.blackjack.run(this)).start();
            } else {
                new VueInfo(this, "Il faut d'abord miser");
            }
        }
        if (e.getSource().equals(this.vueControle.getArreterBtn())) {
            System.exit(0);
        }
        if (e.getSource().equals(this.vueControle.getRejouerBtn())) {
            this.blackjack.ramasserCarte();
            this.blackjack.faireMiserIA();
            this.miser = this.blackjack.queDesIA();

        }
        if (e.getSource().equals(this.vueControle.getMelangerBtn())) {
            this.blackjack.melanger();
        }

        if (e.getSource().equals(this.vueControle.getMiserBtn())) {
            this.vueDemandeMise.setVisible(true);
            String joueurName = this.vueDemandeMise.getNom();
            float mise = this.vueDemandeMise.getMise();
            boolean ok = false;
            for (Joueur joueur : this.blackjack.getJoueurs()) {
                if (joueur.getNom().equalsIgnoreCase(joueurName)) {
                    if (joueur.miser(mise)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (ok) {
                new VueInfo(this, "Votre mise a été prise en compte");
            } else {
                new VueInfo(this, "Votre mise n'a pas été prise en compte");
            }
            ok = true;
            for (Joueur joueur : this.blackjack.getJoueurs()) {
                if (joueur.getMise().getMontant() == 0) {
                    ok = false;
                }
            }
            this.miser = ok;

        }

        if (e.getSource() instanceof VueBouton && ((VueBouton) e.getSource()).getActionCommand() != null) {
            try {
                Action action = Action.valueOf(((VueBouton) e.getSource()).getActionCommand());
                this.actionChoisie = action;
                synchronized (this) {
                    notify(); // on réveille le thread de jeu (run)
                }
            } catch (Exception ex) {
                // bouton inconnu → ignorer
            }
        }

    }

    public void setActionChoisie(Action action) {
        this.actionChoisie = action;
    }

    public Action getActionChoisie() {
        return this.actionChoisie;
    }

    public void setMiser(boolean miser) {
        this.miser = miser;
    }
}
