package cartes.model;

import java.util.*;
import cartes.utils.*;

/**
 * Représente un paquet ou une pile de cartes.
 * Cette classe gère une collection ordonnée de {@link Carte} et fournit
 * des méthodes pour manipuler le paquet (ajouter, retirer, mélanger, couper).
 * <p>
 * Elle étend {@code AbstractModeleEcoutable}, notifiant les écouteurs
 * de tout changement d'état du paquet.
 */
public class Paquet extends AbstractModeleEcoutable {
    
    /**
     * La liste des cartes contenues dans le paquet.
     * La carte à l'index 0 est considérée comme la carte "du dessus" du paquet.
     */
    private List<Carte> listeCartes;

    /**
     * Constructeur par défaut. Initialise un paquet vide de cartes.
     */
    public Paquet() {
        this.listeCartes = new ArrayList<>();
    }

    /**
     * Retourne la liste interne des cartes.
     *
     * @return La {@code List<Carte>} représentant le paquet actuel.
     */
    public List<Carte> getListeCartes() {
        return listeCartes;
    }

    /**
     * Vide complètement le paquet de toutes ses cartes et notifie les écouteurs.
     */
    public void viderPaquet(){
        this.listeCartes.clear();
        this.fireChangement();
    }
    
    /**
     * Ajoute une carte en haut (au dessus) du paquet (à l'index 0).
     * Notifie les écouteurs du changement.
     *
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteAuDessus(Carte carte){
        listeCartes.add(0,carte); 
        super.fireChangement();
    }
  
    /**
     * Ajoute une carte en bas (en dessous) du paquet (à la fin de la liste).
     * Notifie les écouteurs du changement.
     *
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteEnDessous(Carte carte){
        listeCartes.add(carte);
        super.fireChangement();
    }

    /**
     * Retire et retourne une carte choisie aléatoirement dans le paquet.
     * Notifie les écouteurs du changement.
     *
     * @return La carte retirée, ou {@code null} si le paquet est vide.
     */
    public Carte tirerCarteHasard(){
        
        if(listeCartes.isEmpty()) return null;
        
        Random alea = new Random();
        
        int choix = alea.nextInt(listeCartes.size());
        Carte carteTiree = listeCartes.remove(choix);
        super.fireChangement();
        return carteTiree;
    }

    /**
     * Retourne la première carte (celle du dessus) sans la retirer du paquet.
     *
     * @return La première carte, ou {@code null} si le paquet est vide.
     */
    public Carte tirerPremiereCarte(){
        if(listeCartes.isEmpty()) return null;
        return listeCartes.get(0);
    }    

    /**
     * Retire et retourne la première carte (celle du dessus) du paquet.
     * Notifie les écouteurs du changement.
     *
     * @return La première carte retirée, ou {@code null} si le paquet est vide.
     */
    public Carte retirerPremiereCarte(){
        if(listeCartes.isEmpty()) return null;
        Carte carte = listeCartes.remove(0);
        super.fireChangement();
        return carte;
        
    } 

    /**
     * Retourne une carte à une position spécifique sans la retirer du paquet.
     * La position 0 correspond à la première carte.
     * Notifie les écouteurs.
     *
     * @param position L'index de la carte à retourner (0-basé).
     * @return La carte à la position spécifiée.
     * @throws IndexOutOfBoundsException Si la position est invalide.
     */
    public Carte tirerCarteSouhaitee(int position){
        if(position >= listeCartes.size() || position < 0){
            throw new IndexOutOfBoundsException("Choix de position invalide !");
        }
        return listeCartes.get(position);
    }

    

    /**
     * Retire et retourne la carte à une position spécifique du paquet.
     * La position 0 correspond à la première carte.
     * Notifie les écouteurs du changement.
     *
     * @param position L'index de la carte à retirer (0-basé).
     * @return La carte retirée.
     * @throws IndexOutOfBoundsException Si la position est invalide.
     */
    public Carte retirerCarteSouhaitee(int position){
        if(position>=listeCartes.size() || position<0){
            throw new IndexOutOfBoundsException("Choix de position invalide !");
        }
        Carte carte = listeCartes.remove(position);
        super.fireChangement();
        return carte;
    }
    
    
    /**
     * Mélange aléatoirement les cartes dans le paquet en utilisant
     * {@code Collections.shuffle()}.
     * Note: Cette méthode ne notifie pas les écouteurs.
     */
    public void melangerCarte(){
        Collections.shuffle(listeCartes);
    }
    
    /**
     * Retourne le nombre de cartes actuellement dans le paquet.
     *
     * @return La taille du paquet.
     */
    public int getTaille(){
        return this.listeCartes.size();
    }

    /**
     * Coupe le paquet en deux parties à une position choisie aléatoirement
     * (entre la 4ème carte et l'avant-dernière carte, excluant les 3 cartes
     * du dessus et les 3 cartes du dessous) et reconstitue le paquet en plaçant
     * la partie inférieure au-dessus de la partie supérieure.
     * Notifie les écouteurs du changement.
     *
     * @throws IllegalStateException Si la taille du paquet est inférieure à 7,
     * ce qui rend la coupe impossible (moins de 3 cartes de chaque côté).
     */
    public void couper(){
        
        if(listeCartes.size() < 7){ // Doit être >= 7 pour avoir au moins 3+1+3
            throw new IllegalStateException("Impossible de couper, taille de liste insuffisante (minimum 7 cartes requis) !");
        }        
        Random alea = new Random();
        
        // Le choix doit être entre 3 (inclus) et size - 3 (exclus), donc: 3 + alea.nextInt(size - 6)
        int choix = 3 + alea.nextInt(listeCartes.size() - 6);
        
        // Dessus: [0] à [choix-1]
        List<Carte> dessus = new ArrayList<>(listeCartes.subList(0, choix));
        // Dessous: [choix] à [fin]
        List<Carte> dessous = new ArrayList<>(listeCartes.subList(choix, listeCartes.size()));
        
        listeCartes.clear();
        listeCartes.addAll(dessous); // Les cartes d'en dessous sont mises au dessus
        listeCartes.addAll(dessus);  // Les cartes d'en dessus sont mises en dessous
        super.fireChangement();
    }
    
    
    /**
     * Retourne une représentation textuelle du paquet, listant toutes ses cartes.
     *
     * @return Une chaîne de caractères décrivant l'état du paquet.
     */
    @Override
    public String toString() {
        return "Paquet{" + "listeCartes=" + listeCartes + '}';
    }
    
}