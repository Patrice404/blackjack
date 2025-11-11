package blackjack.strategies;
import java.util.List;

import blackjack.UnchangeableSettings;
import blackjack.model.Acteur;
import blackjack.model.Action;
import blackjack.model.Joueur;

public class CroupierIA implements Strategy {

   
    @Override
    public Action play(Joueur joueur, Croupier croupier) {
        int score = joueur.getScore();

        if (score < UnchangeableSettings.NB_SCORE_MAX_CROUPIER ) {
            return Action.DEMANDER_UNE_CARTE;
        }

        return Action.NE_RIEN_FAIRE;
    }

    @Override
    public float miser(Joueur joueur){
        return 0f;
    }

}
