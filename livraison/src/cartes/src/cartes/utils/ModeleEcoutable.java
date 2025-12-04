package cartes.utils;

/**
 * Interface définissant le contrat pour un Modèle qui peut être écouté
 * (Observable dans le patron Observateur).
 * Elle permet aux écouteurs de s'abonner et de se désabonner du modèle.
 */
public interface ModeleEcoutable {
    
    /**
     * Ajoute un écouteur pour recevoir des notifications de changement d'état.
     *
     * @param e L'écouteur à ajouter.
     */
    void ajoutEcouteur(EcouteurModele e);
    
    /**
     * Retire un écouteur pour qu'il ne reçoive plus de notifications.
     *
     * @param e L'écouteur à retirer.
     */
    void retraitEcouteur(EcouteurModele e);
}