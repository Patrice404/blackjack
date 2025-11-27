package blackjack.strategies;
import java.util.List;

import blackjack.UnchangeableSettings;
import blackjack.model.*;

public class CroupierIA implements Strategy {

   
    @Override
    public Action play(Acteur joueur, Croupier croupier) {
        int score = croupier.getScore();

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
