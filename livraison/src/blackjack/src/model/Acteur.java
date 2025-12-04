package blackjack.model;

import java.util.List;
import blackjack.UnchangeableSettings;
import blackjack.strategies.Strategy;
import cartes.model.Carte;
import cartes.model.Paquet;
import cartes.utils.AbstractModeleEcoutable;

/**
 * Représente un participant au jeu de Blackjack (joueur ou croupier).
 * <p>
 * Un acteur possède une main de cartes (Paquet), un score calculé à partir de
 * ces cartes, et potentiellement une somme d'argent (pour les joueurs).
 * Il hérite d'AbstractModeleEcoutable pour notifier les vues de ses changements d'état.
 */
public class Acteur extends AbstractModeleEcoutable {
    
    /** Le nom de l'acteur (ex: "Joueur 1", "Croupier"). */
    protected String nom;
    
    /** Le paquet de cartes détenu par l'acteur (sa main). */
    protected Paquet main;
    
    /** La somme d'argent détenue par l'acteur (pour les joueurs). */
    protected Argent argent;
    
    /** Le score actuel de la main de l'acteur, calculé selon les règles du Blackjack. */
    protected int score;
    
    /** La stratégie de jeu utilisée par cet acteur (manuelle ou algorithmique). */
    protected Strategy strategie;

    /**
     * Construit un nouvel Acteur.
     *
     * @param nom Le nom de l'acteur.
     * @param main Le paquet de cartes représentant la main de l'acteur.
     * @param argent L'objet Argent gérant la bankroll de l'acteur.
     * @param strategy La stratégie de jeu associée à cet acteur.
     */
    public Acteur(String nom, Paquet main, Argent argent, Strategy strategy) {
        this.nom = nom;
        this.main = main;
        this.argent = argent;
        this.strategie = strategy;

        this.score = 0;
    }

    /**
     * Ajoute une somme d'argent aux avoirs de l'acteur (utilisé pour un gain).
     *
     * @param somme Le montant à ajouter.
     */
    public void gagner(float somme) {
        this.argent.ajoutSomme(somme);
    }

    /**
     * Ajoute une carte à la main de l'acteur et met à jour son score.
     * <p>
     * Le calcul du score gère l'As : il est compté 11 par défaut, sauf si cela
     * ferait dépasser la limite ({@code UnchangeableSettings.NB_SCORE_MAX_PLAYERS}),
     * auquel cas il est compté 1.
     * Notifie les écouteurs du changement.
     *
     * @param c La {@link Carte} reçue.
     */
    public void recevoirCarte(Carte c) {
        main.ajouterCarteAuDessus(c);
        
        // Vérifie si la carte est un As (valeurs 1 et 11)
        if (c.getValeurs().size() > 1) { 
            // C'est un As : tente de compter 11
            if (this.score + 11 > UnchangeableSettings.NB_SCORE_MAX_PLAYERS) {
                this.score++; // Compte 1
            } else {
                this.score += 11; // Compte 11
            }
        } else {
            // Carte normale (2 à 10 ou figure)
            this.score += (int) c.getValeurs().get(0);
        }
        this.fireChangement();
    }

    /**
     * Retourne une représentation textuelle de l'acteur (son nom).
     *
     * @return Le nom de l'acteur.
     */
    @Override
    public String toString() {
        return this.nom;
    }

    /**
     * Retourne le nom de l'acteur.
     *
     * @return Le nom.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Vérifie si l'acteur détient un Blackjack (score de 21 avec exactement 2 cartes).
     *
     * @return {@code true} si l'acteur a un Blackjack, {@code false} sinon.
     */
    public boolean detientBlackjack() {
        return this.main.getTaille() == 2 && this.score == 21;
    }

    /**
     * Retourne la stratégie de jeu associée à cet acteur.
     *
     * @return L'objet {@link Strategy}.
     */
    public Strategy getStrategy() {
        return this.strategie;
    }

    /**
     * Vérifie si l'acteur a dépassé le score maximum (Bust/Crève).
     *
     * @return {@code true} si le score est supérieur au maximum, {@code false} sinon.
     */
    public boolean detientBust() {
        return this.score > UnchangeableSettings.NB_SCORE_MAX_PLAYERS;
    }

    /**
     * Retourne la main de cartes de l'acteur.
     *
     * @return Le {@link Paquet} représentant la main.
     */
    public Paquet getMain() {
        return this.main;
    }

    /**
     * Retourne l'objet gérant l'argent de l'acteur.
     *
     * @return L'objet {@link Argent}.
     */
    public Argent getArgent() {
        return this.argent;
    }

    /**
     * Retourne le score actuel de la main de l'acteur.
     *
     * @return Le score numérique.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Détermine si la main de l'acteur est "soft" (implique qu'un As compte comme 11
     * sans faire dépasser le score de 21).
     * <p>
     * Cette méthode recalcule le meilleur score possible pour déterminer si
     * un As peut encore être compté comme 11 sans dépasser 21.
     *
     * @return {@code true} si la main est soft, {@code false} sinon.
     */
    public boolean detientSoft() {
        int total = 0;
        int nbAs = 0;

        // 1. Calculer le total en comptant tous les As comme 11
        for (Carte c : this.main.getListeCartes()) {
            int valeur = c.getValeurs().get(0);
            if (c.getValeurs().contains(11)) {
                nbAs++;
                valeur = 11;
            }
            total += valeur;
        }

        // 2. Si le total dépasse 21, on "rabaisse" des As de 11 → 1
        // (Cette boucle permet de trouver le score le plus élevé <= 21)
        while (total > 21 && nbAs > 0) {
            total -= 10; // Convertit un As de 11 à 1 (diminue le total de 10)
            nbAs--;
        }

        // 3. La main est "soft" si, après réduction, il reste au moins un As compté comme 11.
        // Or, dans le calcul ci-dessus, si nbAs est non nul après la boucle, cela signifie
        // que les As restants comptent pour 11 dans le 'total'.
        // Cependant, le code d'origine vérifie simplement si nbAs > 0 après la réduction
        // pour indiquer si *une* configuration soft était possible et utilisée pour atteindre le total.
        
        // Note: La logique simple du code initial est:
        // Si nbAs > 0 à la fin, cela signifie qu'il y avait un As, 
        // et le total final est le score le plus élevé sans bust.
        // Si ce total final est SOFT (i.e., un As compté 11), le nbAs sera > 0.
        return nbAs > 0;
    }

    /**
     * Définit manuellement le score de l'acteur et notifie les écouteurs.
     * Cette méthode est souvent utilisée pour réinitialiser le score ou pour
     * ajuster le score lors de manipulations complexes de la main.
     *
     * @param score Le nouveau score.
     */
    public void setScore(int score) {
        this.score = score;
        this.fireChangement();
    }

    /**
     * Exécute la décision de jeu de l'acteur basée sur sa {@link Strategy}.
     *
     * @param croupier Le {@link Croupier} (nécessaire pour la stratégie de base).
     * @return L'{@link Action} choisie par l'acteur.
     */
    public Action play(Croupier croupier){
        return this.strategie.play(this,croupier);
    }

}