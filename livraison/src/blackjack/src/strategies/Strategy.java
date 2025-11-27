package blackjack.strategies;
import java.util.List;

import blackjack.model.Acteur;
import blackjack.model.Action;
import blackjack.model.Joueur;
import blackjack.model.Croupier;

public interface Strategy {

    public Action play(Acteur joueur, Croupier croupier);
    public float miser(Joueur joueur);
    
}
