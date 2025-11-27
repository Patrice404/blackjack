package blackjack.model;

import blackjack.UnchangeableSettings;
import blackjack.strategies.CroupierIA;
import blackjack.vue.VueInfo;
import cartes.model.*;;

public class Croupier extends Acteur {
    private Paquet pocheVisible;
    private Paquet pocheCache;

    public Croupier(String nom, Paquet pochecache) {
        super(nom, new Paquet(), new Argent(0f), new CroupierIA());
        this.pocheCache = pochecache;
        this.pocheVisible = new Paquet();
    }

    public void melangerPaquet() {
        this.pocheCache.melangerCarte();
    }

    public void choisirUneCarte() {
        try {
            Carte c = this.pocheCache.retirerPremiereCarte();
            this.main.ajouterCarteAuDessus(c);
            if (c.getValeurs().size() > 1) {
                if (this.score + 11 > UnchangeableSettings.NB_SCORE_MAX_CROUPIER) {
                    this.score++;
                } else {
                    this.score += 11;
                }
            } else {
                this.score += (int) c.getValeurs().get(0);
            }
            this.fireChangement();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void donnerCarte(Joueur joueur, int nb) throws BJException {
        try {
            for (int i = 0; i < nb; i++) {
                Carte c = this.pocheCache.retirerPremiereCarte();
                if (c == null) {
                    new VueInfo(null, "Il n'y a pas assez de carte");
                    throw new BJException("Il n'y a pas assez de carte");
                }
                joueur.recevoirCarte(c);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void mettreDansPocheVisible(Carte carte) {
        this.pocheVisible.ajouterCarteAuDessus(carte);
    }

    public Paquet getPocheVisible() {
        return this.pocheVisible;
    }

    public Paquet getPocheCachee() {
        return this.pocheCache;
    }

    public void ramasserSomme(float somme) {
        if (somme < 0) {
            new Exception("La somme ne peut être inférieur à zéro");
        }
        super.gagner(somme);
    }

    public int getScore() {
        return this.score;
    }
}
