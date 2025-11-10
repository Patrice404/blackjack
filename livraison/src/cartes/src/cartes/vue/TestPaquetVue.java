package cartes.vue;

import javax.swing.*;
import cartes.model.*;

public class TestPaquetVue {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Paquet p = new Paquet();
            p.ajouterCarteEnDessous(new Carte("As", "Pique♠"));
            p.ajouterCarteEnDessous(new Carte("10", "Coeur♥"));
            p.ajouterCarteEnDessous(new Carte("Roi", "Carreau♦"));
            p.ajouterCarteEnDessous(new Carte("8", "Trefles♣"));

            JFrame frame = new JFrame("Main du joueur");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            //VuePaquetCache vue = new VuePaquetCache(p);
            VuePaquetVisible v = new VuePaquetVisible(p, false);
            v.setDecalage(70); // tu peux ajuster ici (plus petit = plus superposé)
           // frame.add(vue);
            frame.add(v);
            frame.setVisible(true);
        });
    }
}
