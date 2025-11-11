package blackjack.strategies;

import java.util.List;

import blackjack.model.Acteur;
import blackjack.model.Action;
import blackjack.model.Croupier;
import blackjack.model.Joueur;

public class JoueurIA implements Strategy {

  
    @Override
    public Action play(Joueur joueur, Croupier croupier) {

        if (croupier == null) {
            // Par sécurité : stratégie simplifiée
            return joueur.getScore() < 17 ? Action.DEMANDER_UNE_CARTE : Action.NE_RIEN_FAIRE;
        }

        int scoreJoueur = joueur.getScore();
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

    public float miser(Joueur joueur){
        return joueur.getArgent().getMontant()/4;
    }

}
