package blackjack.model;

import cartes.model.*;
import blackjack.strategies.Strategy;

public class Joueur extends Acteur {

    private Argent mise;
    private Argent augmentermise = new Argent(0f);
    private boolean abandon;

    public Joueur(String nom, Paquet main, Strategy strategie, Argent mise, Argent argent) {
        super(nom, main, argent, strategie);
        this.mise = mise;
        this.abandon = false;
    }

    public boolean miser(float somme) {
        if (somme > 0 && this.argent.getMontant() >= somme) {
            this.mise.ajoutSomme(somme);
            this.argent.reduireSomme(somme);
            this.augmentermise.setArgent(0f);
            return true;
        }
        return false;
    }

    public void gagner(float somme) {
        super.gagner(somme);
        this.mise.setArgent(0f);
    }

    public Argent getMise() {
        return this.mise;
    }

    public float getAugmenterMise() {
        return this.augmentermise.getMontant();
    }

    public void setAugmenterMise(float somme) {
        this.augmentermise.setArgent(somme);
    }

    public void gagnerMise(float multiplicateur) {
        this.argent.ajoutSomme(this.mise.getMontant() * multiplicateur);
        this.mise.setArgent(0f);
    }

    public void recupererMise() {
        this.argent.ajoutSomme(this.mise.getMontant());
        this.mise.setArgent(0f);
    }

    public void perdreMise() {
        this.mise.setArgent(0f);
    }

    public int getNbCarte(){
        return this.main.getTaille();
    }

    public boolean getAbandon(){
            return this.abandon;
    }
    
    public void setAbandon(boolean abandon){
        this.abandon = abandon;
    }
}
