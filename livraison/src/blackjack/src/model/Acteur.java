package blackjack.model;

import java.util.List;
import blackjack.UnchangeableSettings;
import blackjack.strategies.Strategy;
import cartes.model.Carte;
import cartes.model.Paquet;
import cartes.utils.AbstractModeleEcoutable;

public class Acteur extends AbstractModeleEcoutable {
    protected String nom;
    protected Paquet main;
    protected Argent argent;
    protected int score;
    protected Strategy strategie;

    public Acteur(String nom, Paquet main, Argent argent, Strategy strategy) {
        this.nom = nom;
        this.main = main;
        this.argent = argent;
        this.strategie = strategy;

        this.score = 0;
    }

    public void gagner(float somme) {
        this.argent.ajoutSomme(somme);
    }

    // ajoute une carte à la main du joueur
    public void recevoirCarte(Carte c) {
        main.ajouterCarteAuDessus(c);
        if (c.getValeurs().size() > 1) {
            if (this.score + 11 > UnchangeableSettings.NB_SCORE_MAX_PLAYERS) {
                this.score++;
            } else {
                this.score += 11;
            }
        } else {
            this.score += (int) c.getValeurs().get(0);
        }
        this.fireChangement();
    }

    // lance le tour du joueur selon sa stratégie de jeu
    public Action jouerTour(Acteur joueur, List<Acteur> acteurs) {
        return this.strategie.play(this, acteurs);
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public String getNom() {
        return this.nom;
    }

    public boolean detientBlackjack() {
        return this.main.getTaille() == 2 && this.score == 21;
    }

    public Strategy getStrategy() {
        return this.strategie;
    }

    public boolean detientBust() {
        return this.score > UnchangeableSettings.NB_SCORE_MAX_PLAYERS;
    }

    public Paquet getMain() {
        return this.main;
    }

    public Argent getArgent() {
        return this.argent;
    }

    public int getScore() {
        return this.score;
    }

    public boolean detientSoft() {
        int total = 0;
        int nbAs = 0;

        // 1️⃣ Calculer le total en comptant tous les As comme 11
        for (Carte c : this.main.getListeCartes()) {
            int valeur = c.getValeurs().get(0);
            if (c.getValeurs().contains(11)) {
                nbAs++;
                valeur = 11;
            }
            total += valeur;
        }

        // 2️⃣ Si le total dépasse 21, on "rabaisse" des As de 11 → 1
        while (total > 21 && nbAs > 0) {
            total -= 10; // on convertit un As de 11 à 1
            nbAs--;
        }

        // 3️⃣ La main est "soft" si au moins un As compte encore comme 11
        return nbAs > 0;
    }

    public void setScore(int score) {
        this.score = score;
        this.fireChangement();
    }

}
