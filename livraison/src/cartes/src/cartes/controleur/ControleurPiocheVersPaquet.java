
package cartes.controleur;
import cartes.model.*;
import cartes.vue.*;
import java.awt.event.*;


public class ControleurPiocheVersPaquet implements MouseListener {
    private VuePaquet vueSource;
    private Paquet destination;

    public ControleurPiocheVersPaquet(VuePaquet vueSource, Paquet destination) {
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
