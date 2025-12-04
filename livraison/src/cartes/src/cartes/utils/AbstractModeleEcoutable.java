package cartes.utils;

import java.util.*;

/**
 * Classe abstraite de base implémentant les fonctionnalités du **Modèle Écoutable**
 * dans le patron de conception Observateur (Observer/Observable),
 * permettant à un modèle de notifier ses vues (écouteurs) de ses changements d'état.
 * <p>
 * Les classes de modèle concret (comme {@code Paquet}) doivent hériter de cette
 * classe pour bénéficier des mécanismes d'ajout/retrait d'écouteurs et de notification.
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable {
    
    /**
     * La liste des écouteurs actuellement enregistrés auprès de ce modèle.
     */
    private List<EcouteurModele> listeEcouteur = new ArrayList<>();
    
    
    /**
     * Ajoute un écouteur à la liste des observateurs à notifier.
     *
     * @param e L'écouteur (la vue) à ajouter.
     */
    public void ajoutEcouteur(EcouteurModele e){
        listeEcouteur.add(e);
    }
    
    /**
     * Retire un écouteur de la liste des observateurs.
     *
     * @param e L'écouteur (la vue) à retirer.
     */
    public void retraitEcouteur(EcouteurModele e){
        listeEcouteur.remove(e);  
    }
    
    /**
     * Notifie tous les écouteurs enregistrés d'un changement d'état du modèle.
     * <p>
     * Cette méthode doit être appelée par les sous-classes
     * (modèles concrets) chaque fois qu'un changement interne
     * susceptible d'affecter la vue se produit.
     */
    protected void fireChangement(){
        for(EcouteurModele ecouteur : listeEcouteur){
            ecouteur.modeleMisAJour(this);
        }
    }
    
}