package blackjack.model;

import cartes.utils.AbstractModeleEcoutable;

public class Argent extends AbstractModeleEcoutable{
    private float argent;

    public Argent(float somme) {
        this.argent = somme;
    }

    public void ajoutSomme(float somme){
        if(somme > 0){
            this.argent+=somme;
        }
        this.fireChangement();
    }

    public void reduireSomme(float somme){
        if(somme > 0){
            this.argent-=somme;
        }
        this.fireChangement();
    }

    public float getMontant() {
        return argent;
    }

    public void setArgent(float somme) {
        this.argent = somme;
        this.fireChangement();
    }


}
