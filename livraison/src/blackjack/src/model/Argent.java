package blackjack.model;

import cartes.utils.AbstractModeleEcoutable;

/**
 * Représente la somme d'argent (ou la bankroll) détenue par un joueur.
 * <p>
 * Cette classe hérite d'AbstractModeleEcoutable, permettant de notifier les vues
 * (comme l'affichage du solde du joueur) de tout changement de montant.
 */
public class Argent extends AbstractModeleEcoutable{
    
    /**
     * Le montant actuel d'argent détenu.
     */
    private float argent;

    /**
     * Construit un nouvel objet Argent avec une somme initiale spécifiée.
     *
     * @param somme Le montant initial (bankroll de départ).
     */
    public Argent(float somme) {
        this.argent = somme;
    }

    /**
     * Ajoute une somme au montant actuel, représentant un gain ou un dépôt.
     * La somme n'est ajoutée que si elle est strictement positive.
     * Notifie les écouteurs du changement.
     *
     * @param somme Le montant positif à ajouter.
     */
    public void ajoutSomme(float somme){
        if(somme > 0){
            this.argent+=somme;
        }
        this.fireChangement();
    }

    /**
     * Réduit le montant actuel par la somme spécifiée, représentant une perte ou une mise.
     * Le montant n'est réduit que si la somme est strictement positive.
     * Notifie les écouteurs du changement.
     *
     * @param somme Le montant positif à soustraire.
     */
    public void reduireSomme(float somme){
        if(somme > 0){
            this.argent-=somme;
        }
        this.fireChangement();
    }

    /**
     * Retourne le montant actuel d'argent détenu.
     *
     * @return Le solde actuel en tant que {@code float}.
     */
    public float getMontant() {
        return argent;
    }

    /**
     * Définit directement le montant actuel d'argent.
     * Notifie les écouteurs du changement.
     *
     * @param somme Le nouveau montant.
     */
    public void setArgent(float somme) {
        this.argent = somme;
        this.fireChangement();
    }


}