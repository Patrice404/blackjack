package blackjack.strategies;

import java.util.List;

import blackjack.UnchangeableSettings;
import blackjack.model.*;

/**
 * Implémentation de l'interface {@link Strategy} pour le {@link Croupier}.
 * <p>
 * Le Croupier est régi par des règles de jeu strictes et inébranlables.
 * Il doit tirer des cartes tant que son score est strictement inférieur à
 * un seuil prédéfini (généralement 17).
 */
public class CroupierIA implements Strategy {

    /**
     * Détermine l'action de jeu du Croupier selon les règles strictes.
     * <p>
     * Le Croupier tire une carte (DEMANDER_UNE_CARTE) si son score actuel
     * est inférieur à la limite fixée dans {@code UnchangeableSettings.NB_SCORE_MAX_CROUPIER} (souvent 17).
     * Sinon, il s'arrête (NE_RIEN_FAIRE).
     *
     * @param joueur L'acteur qui joue (ici, le Croupier lui-même).
     * @param croupier Le Croupier (pour son propre score).
     * @return {@link Action#DEMANDER_UNE_CARTE} si le score est trop bas,
     * {@link Action#NE_RIEN_FAIRE} sinon.
     */
    @Override
    public Action play(Acteur joueur, Croupier croupier) {
        int score = croupier.getScore();

        // Règle du Croupier : il doit tirer tant que son score est < 17 (ou la limite configurée)
        if (score < UnchangeableSettings.NB_SCORE_MAX_CROUPIER ) {
            return Action.DEMANDER_UNE_CARTE;
        }

        return Action.NE_RIEN_FAIRE;
    }

    /**
     * Pour le Croupier, cette méthode n'est pas utilisée car il ne mise pas.
     *
     * @param joueur Le joueur (ici, le Croupier n'est pas censé miser).
     * @return Toujours {@code 0f}.
     */
    @Override
    public float miser(Joueur joueur){
        return 0f;
    }

}