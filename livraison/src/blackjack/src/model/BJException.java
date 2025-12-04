package blackjack.model;

/**
 * Exception spécifique au jeu de Blackjack, utilisée pour signaler des
 * erreurs ou des conditions exceptionnelles survenant pendant le déroulement
 * de la partie (par exemple, manque de cartes dans la pioche, paramètres invalides).
 * <p>
 * Cette classe est une exception vérifiée (checked exception) puisqu'elle hérite
 * directement de {@code Exception}.
 */
public class BJException extends Exception {
    
    /**
     * Construit une nouvelle exception {@code BJException} avec le message de détail spécifié.
     *
     * @param message Le message de détail (qui peut être récupéré par la méthode {@code getMessage()}).
     */
    public BJException(String message){
        super(message);
    }

   
}