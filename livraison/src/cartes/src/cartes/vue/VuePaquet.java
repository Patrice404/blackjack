package cartes.vue;

import javax.swing.*;
import cartes.model.*;
import cartes.utils.*;

/**
 * Classe abstraite de base représentant une **vue graphique** d'un {@link Paquet}
 * de cartes.
 * <p>
 * En tant que {@code JPanel}, elle peut être affichée dans une interface graphique
 * Swing.
 * <p>
 * En tant qu'{@code EcouteurModele}, elle est abonnée au {@link Paquet} (le modèle)
 * et est notifiée de tout changement d'état du paquet afin de pouvoir se redessiner
 * (méthode {@code modeleMisAJour} non implémentée ici).
 */
public abstract class VuePaquet extends JPanel implements EcouteurModele{
    
    /**
     * Le modèle {@link Paquet} dont cette vue observe l'état.
     */
    protected Paquet paquet;
    
    /**
     * Une désignation ou un nom pour cette vue de paquet (ex: "Pioche", "Défausse", "Main du joueur").
     */
    private String designation;

    /**
     * Construit la vue d'un paquet et s'enregistre comme écouteur du paquet spécifié.
     *
     * @param paquet Le paquet de cartes à visualiser (le modèle).
     * @param designation Le nom ou la désignation descriptive de ce paquet dans l'IHM.
     */
    public VuePaquet(Paquet paquet, String designation) {
        this.paquet = paquet;
        this.designation = designation;
        // La vue s'abonne au modèle pour être mise à jour
        paquet.ajoutEcouteur(this);
    }

     /**
      * Retourne le paquet de cartes associé à cette vue.
      *
      * @return L'objet {@link Paquet} actuel.
      */
     public Paquet getPaquet() {
         return paquet;
     }

}