
package cartes.vue;

import javax.swing.*;
import cartes.model.*;
import cartes.utils.*;

public abstract class VuePaquet extends JPanel implements EcouteurModele{
    
    protected Paquet paquet;
    private String designation;

    public VuePaquet(Paquet paquet, String designation) {
        this.paquet = paquet;
        paquet.ajoutEcouteur(this);
    }

     public Paquet getPaquet() {
         return paquet;
     }

   
    
}
