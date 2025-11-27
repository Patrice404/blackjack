
package cartes.utils;

import java.util.*;


public abstract class AbstractModeleEcoutable implements ModeleEcoutable {
    
    private List<EcouteurModele> listeEcouteur = new ArrayList<>();
    
    
    public void ajoutEcouteur(EcouteurModele e){
        listeEcouteur.add(e);
    }
    
    public void retraitEcouteur(EcouteurModele e){
        listeEcouteur.remove(e);  
    }
    
    protected void fireChangement(){
        for(EcouteurModele ecouteur : listeEcouteur){
            ecouteur.modeleMisAJour(this);
        }
    }
    
}
