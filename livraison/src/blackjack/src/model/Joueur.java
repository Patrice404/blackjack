package blackjack.model;

import cartes.model.*;
import blackjack.strategies.Strategy;

/**
 * Représente un Joueur humain ou IA dans une partie de Blackjack.
 * Le Joueur hérite des propriétés et méthodes de base de l'{@link Acteur}
 * mais ajoute la gestion des mises, des gains/pertes spécifiques, et de l'abandon.
 */
public class Joueur extends Acteur {

    /**
     * L'objet {@link Argent} représentant la mise actuelle du joueur sur la main en cours.
     */
    private Argent mise;
    
    /**
     * L'objet {@link Argent} représentant une augmentation de mise temporaire (ex: doublement).
     */
    private Argent augmentermise = new Argent(0f);
    
    /**
     * Indicateur pour savoir si le joueur a choisi d'abandonner la main ("Surrender").
     */
    private boolean abandon;

    /**
     * Construit un nouveau Joueur.
     *
     * @param nom Le nom du joueur.
     * @param main Le paquet de cartes représentant la main du joueur.
     * @param strategie La {@link Strategy} de jeu de ce joueur.
     * @param mise L'objet {@link Argent} initial qui gérera la mise en cours.
     * @param argent L'objet {@link Argent} gérant la bankroll totale du joueur.
     */
    public Joueur(String nom, Paquet main, Strategy strategie, Argent mise, Argent argent) {
        super(nom, main, argent, strategie);
        this.mise = mise;
        this.abandon = false;
    }

    /**
     * Place une mise sur la main en cours.
     * La somme est déduite du solde total du joueur et ajoutée à sa mise courante.
     *
     * @param somme Le montant à miser.
     * @return {@code true} si la mise a été placée avec succès (solde suffisant et somme positive), {@code false} sinon.
     */
    public boolean miser(float somme) {
        if (somme > 0 && this.argent.getMontant() >= somme) {
            this.mise.ajoutSomme(somme);
            this.argent.reduireSomme(somme);
            this.augmentermise.setArgent(0f); // Réinitialise l'augmentation de mise
            return true;
        }
        return false;
    }

    /**
     * Ajoute une somme au solde total du joueur (utilisé pour les gains hors mise)
     * et réinitialise la mise courante à zéro.
     *
     * @param somme Le montant gagné.
     */
    @Override
    public void gagner(float somme) {
        super.gagner(somme);
        this.mise.setArgent(0f);
    }

    /**
     * Retourne l'objet gérant la mise actuelle du joueur.
     *
     * @return L'objet {@link Argent} de la mise.
     */
    public Argent getMise() {
        return this.mise;
    }

    /**
     * Retourne le montant de la mise supplémentaire (souvent pour l'action Doubler).
     *
     * @return Le montant de l'augmentation de mise.
     */
    public float getAugmenterMise() {
        return this.augmentermise.getMontant();
    }

    /**
     * Définit le montant de la mise supplémentaire.
     *
     * @param somme Le montant de la mise supplémentaire.
     */
    public void setAugmenterMise(float somme) {
        this.augmentermise.setArgent(somme);
    }

    /**
     * Gère le paiement d'un gain. Ajoute le montant de la mise multiplié
     * par le multiplicateur au solde du joueur, puis réinitialise la mise.
     *
     * @param multiplicateur Le facteur par lequel la mise est multipliée (ex: 2.0 pour un gain normal).
     */
    public void gagnerMise(float multiplicateur) {
        this.argent.ajoutSomme(this.mise.getMontant() * multiplicateur);
        this.mise.setArgent(0f);
    }

    /**
     * Gère la récupération de la mise (Push/Égalité).
     * Le montant misé est rendu au solde du joueur, et la mise est réinitialisée.
     */
    public void recupererMise() {
        this.argent.ajoutSomme(this.mise.getMontant());
        this.mise.setArgent(0f);
    }

    /**
     * Gère la perte de la mise. La mise est réinitialisée à zéro sans affecter le solde,
     * car la réduction du solde a déjà eu lieu lors de l'appel à {@code miser()}.
     */
    public void perdreMise() {
        this.mise.setArgent(0f);
    }

    /**
     * Retourne le nombre de cartes actuellement dans la main du joueur.
     *
     * @return La taille de la main.
     */
    public int getNbCarte(){
        return this.main.getTaille();
    }

    /**
     * Vérifie si le joueur a abandonné la main.
     *
     * @return {@code true} si le joueur a abandonné, {@code false} sinon.
     */
    public boolean getAbandon(){
            return this.abandon;
    }
    
    /**
     * Définit l'état d'abandon du joueur pour la main en cours.
     *
     * @param abandon L'état d'abandon à définir.
     */
    public void setAbandon(boolean abandon){
        this.abandon = abandon;
    }
}