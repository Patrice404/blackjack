
package cartes.model;

import java.util.ArrayList;

public class Carte {

    
    private String hauteur;
    private String couleur;
    private ArrayList<Integer> valeurs;


    public Carte(String hauteur, String couleur) {
        this.hauteur = hauteur;
        this.couleur = couleur;
         this.valeurs = new ArrayList<>();
        this.definirValeurs();
    }
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
    public String getHauteur() {
        return hauteur;
    }

    public String getCouleur() {
        return couleur;
    }

    public ArrayList<Integer> getValeurs(){
        return this.valeurs;
    }
    

    @Override
    public String toString() {
        return hauteur+"-"+couleur;
    }
    
    
}
