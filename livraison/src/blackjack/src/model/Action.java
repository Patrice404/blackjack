package blackjack.model;

/**
 * Définit les actions possibles qu'un {@link Acteur} peut choisir
 * d'effectuer pendant son tour de jeu au Blackjack.
 */
public enum Action {
    /**
     * L'acteur demande une carte supplémentaire ("Hit").
     */
    DEMANDER_UNE_CARTE,
    
    /**
     * L'acteur décide de conserver son score actuel et de passer son tour ("Stand").
     */
    NE_RIEN_FAIRE,
    
    /**
     * L'acteur double sa mise initiale et reçoit exactement une carte supplémentaire ("Double Down").
     * Cette action met fin à son tour.
     */
    DOUBLER_SA_MISE,
    
    /**
     * L'acteur abandonne la manche, perdant la moitié de sa mise ("Surrender").
     */
    ABANDONNER
}