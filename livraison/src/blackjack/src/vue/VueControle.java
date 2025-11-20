package blackjack.vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class VueControle extends JPanel {
    private final int centerX = 250;
    private final int centerY = 250;
    private final int radius = 100;

    private VueBouton miserBtn;
    private VueBouton arreterBtn;
    private VueBouton distribuerBtn;
    private VueBouton autreBtn;
    private VueBouton melangerBtn; 

    public VueControle(ActionListener listener) {
        setLayout(null);
        setPreferredSize(InterfaceSetting.DIMENSION_ACTION);
        setOpaque(false);

        // Boutons autour du cercle
        miserBtn = makeBtn("Miser", 0, 4, listener);
        arreterBtn = makeBtn("Arrêter", 1, 4, listener);
        distribuerBtn = makeBtn("Init", 2, 4, listener);
        autreBtn = makeBtn("Rejouer", 3, 4, listener);

        // Bouton central
        melangerBtn = new VueBouton("Mélanger");
        melangerBtn.setBounds(centerX - 50, centerY - 50, 100, 100);
        melangerBtn.addActionListener(listener);

        // Ajout à la vue
        add(miserBtn);
        add(arreterBtn);
        add(distribuerBtn);
        add(autreBtn);
        add(melangerBtn);
    }

    private VueBouton makeBtn(String name, int i, int n, ActionListener listener) {
        double angle = 2 * Math.PI * i / n - Math.PI / 2;
        int x = (int) (centerX + radius * Math.cos(angle));
        int y = (int) (centerY + radius * Math.sin(angle));

        VueBouton bouton = new VueBouton(name);
        bouton.setBounds(x - 50, y - 50, 100, 100);
        bouton.addActionListener(listener);
        return bouton;
    }

    public VueBouton getMiserBtn() {
        return miserBtn;
    }

    public VueBouton getArreterBtn() {
        return arreterBtn;
    }

    public VueBouton getDistribuerBtn() {
        return distribuerBtn;
    }

    public VueBouton getRejouerBtn() {
        return autreBtn;
    }

    public VueBouton getMelangerBtn() {
        return melangerBtn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.dispose();
    }
}
