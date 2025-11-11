package blackjack.strategies;
import java.util.List;

import blackjack.model.Acteur;
import blackjack.model.Action;
import blackjack.model.Joueur;

public interface Strategy {

    public Action play(Joueur joueur, Croupier croupier);
    public float miser(Joueur joueur);
    
}
