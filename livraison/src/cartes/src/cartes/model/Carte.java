package cartes.model;

import java.util.ArrayList;

/**
 * Représente une carte à jouer avec une hauteur (rang), une couleur (symbole),
 * et une ou plusieurs valeurs numériques associées (particulièrement utile
 * pour les jeux comme le Blackjack où l'As a deux valeurs possibles).
 */
public class Carte {

    
    /**
     * La hauteur ou le rang de la carte (ex: "As", "Roi", "2", etc.).
     */
    private String hauteur;
    
    /**
     * Le symbole de la carte (ex: "Pique", "Coeur", etc.).
     */
    private String symbole;
    
    /**
     * Une liste des valeurs numériques possibles de la carte. Pour la plupart des cartes,
     * cette liste ne contient qu'un seul élément. Pour l'As, elle contient [1, 11].
     */
    private ArrayList<Integer> valeurs;


    /**
     * Construit une nouvelle carte en spécifiant sa hauteur et sa couleur, et
     * calcule immédiatement ses valeurs numériques possibles.
     *
     * @param hauteur La hauteur/le rang de la carte (ex: "As", "Roi", "10").
     * @param symbole Le symbole de la carte (ex: "Pique", "Trèfle").
     */
    public Carte(String hauteur, String symbole) {
        this.hauteur = hauteur;
        this.symbole = symbole;
         this.valeurs = new ArrayList<>();
        this.definirValeurs();
    }
    
    /**
     * Définit les valeurs numériques de la carte en fonction de sa hauteur.
     * Pour les figures (Roi, Dame, Valet), la valeur est 10.
     * Pour l'As, les valeurs sont 1 et 11.
     * Pour les cartes numériques, la valeur est la hauteur convertie en entier.
     */
    //Retourne la valeur d'une carte 
    private void definirValeurs(){

        switch (this.getHauteur()) {
            case "As":
                this.valeurs.add(1);
                this.valeurs.add(11);
                break;
            case "Roi":
                this.valeurs.add(10);
                break;
            case "Dame":
                this.valeurs.add(10);
                break;
            case "Valet":
                this.valeurs.add(10);
                break;
            default:
                try {
                    this.valeurs.add(Integer.parseInt(this.getHauteur()));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                    return;
                }
        }
    }
    
    /**
     * Retourne la hauteur (rang) de la carte.
     *
     * @return La hauteur de la carte sous forme de chaîne de caractères.
     */
    public String getHauteur() {
        return hauteur;
    }

    /**
     * Retourne le symbole de la carte.
     *
     * @return Le symbole de la carte sous forme de chaîne de caractères.
     */
    public String getSymbole() {
        return symbole;
    }

    /**
     * Retourne la liste des valeurs numériques possibles de la carte.
     *
     * @return Une {@code ArrayList<Integer>} contenant les valeurs possibles de la carte.
     */
    public ArrayList<Integer> getValeurs(){
        return this.valeurs;
    }
    

    /**
     * Retourne une représentation textuelle de la carte sous la forme "hauteur-couleur".
     *
     * @return Une chaîne de caractères représentant la carte (ex: "As-Pique").
     */
    @Override
    public String toString() {
        return hauteur+"-"+symbole;
    }
    
    
}