package blackjack.strategies;

import java.util.List;

import blackjack.model.Acteur;
import blackjack.model.Action;
import blackjack.model.Croupier;
import blackjack.model.Joueur;

/**
 * Implémentation de l'interface {@link Strategy} pour un joueur contrôlé
 * par une intelligence artificielle simple.
 * <p>
 * Cette IA utilise une version simplifiée de la stratégie de base du Blackjack
 * (Basic Strategy) pour prendre ses décisions d'action.
 */
public class JoueurIA implements Strategy {

    /**
     * Détermine l'action de jeu de l'IA basée sur le score du joueur
     * et la carte visible du Croupier.
     * <p>
     * La logique simplifiée est basée sur des règles courantes :
     * <ul>
     * <li>Tire toujours sur un score de 11 ou moins (main "hard").</li>
     * <li>S'arrête toujours sur un score de 17 ou plus (main "hard").</li>
     * <li>Si le score est entre 13 et 16 : tire si la carte visible du Croupier est ≥ 7, s'arrête sinon.</li>
     * <li>Gère aléatoirement l'abandon pour certaines mains faibles contre un Croupier fort.</li>
     * </ul>
     *
     * @param joueur L'acteur qui joue actuellement (doit être un {@link Joueur} ou {@link Acteur}).
     * @param croupier Le {@link Croupier} (pour sa carte visible).
     * @return L'{@link Action} choisie (Tirer, Rester, ou Abandonner aléatoirement).
     */
    @Override
    public Action play(Acteur joueur, Croupier croupier) {

        if (croupier == null) {
            // Par sécurité : stratégie simplifiée
            return joueur.getScore() < 17 ? Action.DEMANDER_UNE_CARTE : Action.NE_RIEN_FAIRE;
        }

        int scoreJoueur = joueur.getScore();
        // Récupère la valeur de la carte visible du Croupier (la première carte)
        int carteCroupier = croupier.getMain().getListeCartes().get(0).getValeurs().get(0);
        boolean soft = joueur.detientSoft();


        // --- Si le joueur est déjà bust ou a un blackjack ---
        if (joueur.detientBust() || joueur.detientBlackjack()) {
            return Action.NE_RIEN_FAIRE;
        }

        // --- Décision principale : stratégie du Blackjack ---
        if (joueur.detientSoft()) {
            // Mains "soft" (avec As compté comme 11)
            if (scoreJoueur <= 17) {
                return Action.DEMANDER_UNE_CARTE;
            } else {
                return Action.NE_RIEN_FAIRE;
            }
        } else {
            // Mains "hard"
            if (scoreJoueur <= 11) {
                return Action.DEMANDER_UNE_CARTE;
            }

            // Si main très faible contre croupier fort, possibilité d'abandonner
            if (scoreJoueur == 12 && carteCroupier >= 7) {
                return Math.random() < 0.5 ? Action.ABANDONNER : Action.DEMANDER_UNE_CARTE;
            }

            // Si main 13–16
            if (scoreJoueur >= 13 && scoreJoueur <= 16) {
                if (carteCroupier >= 7) {
                    // Le croupier est fort → tirer, parfois abandonner
                    if (Math.random() < 0.2) return Action.ABANDONNER;
                    return Action.DEMANDER_UNE_CARTE;
                } else {
                    return Action.NE_RIEN_FAIRE;
                }
            }

            // 17 ou plus → rester
            if (scoreJoueur >= 17) {
                return Action.NE_RIEN_FAIRE;
            }
        }

        // Sécurité (ne devrait pas arriver)
        return Action.NE_RIEN_FAIRE;
    }

    /**
     * Détermine le montant que l'IA doit miser.
     * <p>
     * L'IA utilise une stratégie de mise très simple : miser 1/4 de son argent total.
     *
     * @param joueur Le {@link Joueur} pour lequel la mise est calculée.
     * @return Le montant de la mise (1/4 de la bankroll du joueur).
     */
    public float miser(Joueur joueur){
        return joueur.getArgent().getMontant()/4;
    }

}