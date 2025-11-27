package cartes.model;

import java.util.*;
import cartes.utils.*;;

public class Paquet extends AbstractModeleEcoutable {
    
    private List<Carte> listeCartes;

    public Paquet() {
        this.listeCartes = new ArrayList<>();
    }

    public List<Carte> getListeCartes() {
        return listeCartes;
    }

    public void viderPaquet(){
        this.listeCartes.clear();
        this.fireChangement();
    }
    public void ajouterCarteAuDessus(Carte carte){
        listeCartes.add(0,carte); 
        super.fireChangement();
    }
  
    public void ajouterCarteEnDessous(Carte carte){
        listeCartes.add(carte);
        super.fireChangement();
    }

    public Carte tirerCarteHasard(){
        
        if(listeCartes.isEmpty()) return null;
        
        Random alea = new Random();
        
        int choix = alea.nextInt(listeCartes.size());
        Carte carteTiree = listeCartes.remove(choix);
        super.fireChangement();
        return carteTiree;
    }

    public Carte tirerPremiereCarte(){
        if(listeCartes.isEmpty()) return null;
        super.fireChangement();
        return listeCartes.get(0);
    }    

    public Carte retirerPremiereCarte(){
        if(listeCartes.isEmpty()) return null;
        Carte carte = listeCartes.remove(0);
        //notify();
        super.fireChangement();
        return carte;
        
    } 

    public Carte tirerCarteSouhaitee(int position){
        if(position >= listeCartes.size() || position < 0){
            throw new IndexOutOfBoundsException("Choix de position invalide !");
        }
        super.fireChangement();
        return listeCartes.get(position);
    }

    

    public Carte retirerCarteSouhaitee(int position){
        if(position>listeCartes.size() || position<0){
            throw new IndexOutOfBoundsException("Choix de position invalide !");
        }
        Carte carte = listeCartes.remove(position);
        super.fireChangement();
        return carte;
    }
    
    
    public void melangerCarte(){
        Collections.shuffle(listeCartes);
    }
    
    public int getTaille(){
        return this.listeCartes.size();
    }

    /*
    Permet de couper le paquet des cartes à une position aléatoire
    sans les 3 prémières et dernières cartes. Puis reconstruire le paquet.
    ex : paquet = [1,2,3,4,5,6,7,8,9,10]
    dessus : [1,2,3,4,5] dessous [6,7,8,9,10] ==> [6,7,8,9,10,1,2,3,4,5] 
    */
    public void couper(){
        
        if(listeCartes.size()<0 || listeCartes.size()<6){
            throw new IllegalStateException("Impossible de couper, taille de liste insuffisante !");
        }        
        Random alea = new Random();
        
        int choix = 3 + alea.nextInt(listeCartes.size() - 6);
        
        List<Carte> dessus = new ArrayList<>(listeCartes.subList(0, choix));
        List<Carte> dessous = new ArrayList<>(listeCartes.subList(choix, listeCartes.size()));
        listeCartes.clear();
        listeCartes.addAll(dessous);
        listeCartes.addAll(dessus);
        super.fireChangement();
    }
    
    
    @Override
    public String toString() {
        return "Paquet{" + "listeCartes=" + listeCartes + '}';
    }
    
}
