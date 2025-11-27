
package cartes.controleur;

import cartes.model.*;
import cartes.vue.*;
import java.awt.event.*;


public class ControleurChoixCarteVersPaquet implements MouseListener {
    private VuePaquetVisible vueSource;
    private Paquet destination;

    public ControleurChoixCarteVersPaquet(VuePaquetVisible vueSource, Paquet destination) {
        this.vueSource = vueSource;
        this.destination = destination;
        vueSource.addMouseListener(this);
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        Carte carte = vueSource.getPaquet().retirerPremiereCarte();
        if(carte!=null){
             destination.ajouterCarteEnDessous(carte);
        }
        // Paquet paquetSource = vueSource.getPaquet();

        // if (paquetSource.getTaille() > 0) {
        //     Carte carte = paquetSource.retirerPremiereCarte();
        //     destination.ajouterCarteEnDessous(carte);
        //     System.out.println("Carte piochée : " + carte);
        // }
    }



    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
        
    }

    public void mouseExited(MouseEvent me) {
   }
    
 
    
}
