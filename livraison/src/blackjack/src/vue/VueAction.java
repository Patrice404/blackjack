package blackjack.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import blackjack.model.Action;

/**
 * Vue représentant les actions possibles du joueur sous forme
 * de boutons ronds disposés en cercle.
 * Cette version est totalement transparente (pas de fond).
 */
public class VueAction extends JPanel {

    private EnumMap<Action, VueBouton> boutons;

    public VueAction(ActionListener listener) {
        this.boutons = new EnumMap<>(Action.class);
        setLayout(null);
        setPreferredSize(InterfaceSetting.DIMENSION_ACTION);
        setOpaque(false); 

        int centerX = 250;
        int centerY = 250;
        int radius = 100;

        Action[] actions = Action.values();
        int n = actions.length;

        for (int i = 0; i < n; i++) {
            Action action = actions[i];

            double angle = 2 * Math.PI * i / n - Math.PI / 2;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));

            VueBouton bouton = new VueBouton(formatLabel(action));
            bouton.setBounds(x - 50, y - 50, 100, 100);
            bouton.addActionListener(listener);
            bouton.setActionCommand(action.name());

            boutons.put(action, bouton);
            add(bouton);
        }
    }
    
    private String formatLabel(Action action) {
        switch (action) {
            case DEMANDER_UNE_CARTE: return "Carte";
            case NE_RIEN_FAIRE: return "Rester";
            case DOUBLER_SA_MISE: return "Doubler";
            case ABANDONNER: return "Abandon";
            default: return action.name();
        }
    }

    public VueBouton getBouton(Action action) {
        return boutons.get(action);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // on ne peint pas de fond ici, donc transparence réelle
        Graphics2D g2 = (Graphics2D) g.create();
        g2.dispose();
    }
}
