package cartes.utils;

/**
 * Interface définissant le contrat pour un Observateur (Vue) qui écoute
 * les changements d'état d'un Modèle.
 */
public interface EcouteurModele {
    
    /**
     * Méthode appelée par le Modèle Observable lorsqu'un changement a eu lieu.
     * Les classes implémentant cette interface (les Vues) doivent utiliser
     * cette méthode pour se mettre à jour graphiquement.
     *
     * @param source L'objet Modèle qui a déclenché l'événement.
     */
    void modeleMisAJour(Object source);
}