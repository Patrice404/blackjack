package blackjack.strategies;
import java.util.List;

import blackjack.model.Acteur;
import blackjack.model.Action;
import blackjack.model.Joueur;
import blackjack.model.Croupier;

/**
 * Interface définissant le contrat pour toutes les stratégies de jeu au Blackjack.
 * <p>
 * Toute classe implémentant cette interface doit être capable de décider de l'action
 * de jeu (Hit, Stand, etc.) et de déterminer le montant de la mise.
 */
public interface Strategy {

    /**
     * Détermine l'action de jeu que l'acteur doit prendre pour son tour.
     * Cette méthode est le cœur de la logique de jeu de la stratégie (IA, règles strictes, ou Humain).
     *
     * @param joueur L'acteur qui joue actuellement ({@link Acteur} ou {@link Joueur}).
     * @param croupier Le {@link Croupier} (pour connaître sa carte visible).
     * @return L'{@link Action} choisie (ex: DEMANDER_UNE_CARTE, NE_RIEN_FAIRE).
     */
    public Action play(Acteur joueur, Croupier croupier);
    
    /**
     * Détermine le montant que le joueur doit miser pour la prochaine manche.
     *
     * @param joueur Le {@link Joueur} pour lequel la mise est calculée (nécessaire pour connaître son solde).
     * @return Le montant de la mise souhaitée en tant que {@code float}.
     */
    public float miser(Joueur joueur);
    
}