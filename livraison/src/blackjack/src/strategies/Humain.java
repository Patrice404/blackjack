package blackjack.strategies;

import java.util.List;
import java.util.Scanner;

import blackjack.model.*;

/**
 * Implémentation de l'interface {@link Strategy} représentant un joueur humain.
 * <p>
 * Contrairement aux IA, cette stratégie ne contient **aucune logique de décision**
 * pour les méthodes {@code play} et {@code miser}. Elle sert uniquement de marqueur
 * pour le système de jeu ({@link Blackjack}) afin d'indiquer que l'action doit
 * être sollicitée via l'interface utilisateur graphique (IHM).
 */
public class Humain implements Strategy {
    
    /**
     * Scanner utilisé pour une éventuelle interaction console (bien que non utilisé
     * dans le contexte d'une IHM graphique, il est conservé ici pour référence).
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Pour une stratégie humaine, cette méthode est un marqueur et retourne {@code null}.
     * <p>
     * L'action de jeu réelle est gérée par la boucle principale du jeu ({@code Blackjack.run})
     * qui attend une action de l'IHM graphique avant de continuer.
     *
     * @param joueur L'acteur humain qui joue.
     * @param croupier Le Croupier.
     * @return Toujours {@code null}, car l'action est déterminée par l'IHM.
     */
    @Override
    public Action play(Acteur joueur, Croupier croupier) {
        return null;
    }
    
    /**
     * Pour une stratégie humaine, cette méthode est un marqueur et retourne 0.
     * <p>
     * Le montant de la mise réelle est défini par l'utilisateur via l'IHM graphique.
     *
     * @param joueur Le joueur humain.
     * @return Toujours {@code 0f}, car la mise est gérée par l'IHM.
     */
    @Override
    public float miser(Joueur joueur){
        return 0f;
    }
}