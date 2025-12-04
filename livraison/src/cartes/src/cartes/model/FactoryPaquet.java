package cartes.model;


/**
 * Classe utilitaire (usine) responsable de la création et de l'initialisation
 * de différents types de paquets de cartes standard, notamment un paquet de 52 cartes.
 * <p>
 * Les méthodes de cette classe sont statiques et ne nécessitent pas d'instanciation.
 */
public class FactoryPaquet {


    /**
     * Tableau des couleurs (symboles) standard d'un jeu de 52 cartes.
     * Les symboles Unicode sont inclus pour une meilleure représentation.
     */
    private static String[] couleur = {"Pique♠","Coeur♥","Carreau♦","Trefles♣"};


    /**
     * Tableau des hauteurs (rangs) standard d'un jeu de 52 cartes,
     * classées de la plus faible à la plus forte (du 2 à l'As).
     */
    private static String[] hauteur = {"2","3","4","5","6","7","8","9","10","Valet","Dame","Roi","As"};


    /**
     * Crée et retourne un paquet de 52 cartes standard, immédiatement mélangé.
     * <p>
     * Le paquet est construit en itérant sur les 4 couleurs et les 13 hauteurs
     * et en ajoutant chaque {@link Carte} au bas du {@link Paquet}.
     * Le paquet résultant est ensuite mélangé aléatoirement.
     *
     * @return Un nouvel objet {@link Paquet} contenant les 52 cartes mélangées.
     */
    public static Paquet createPaquet52Carte(){

        Paquet p = new Paquet();
        for(String color : couleur){
            for(String h : hauteur){
                Carte c = new Carte(h,color);
                p.ajouterCarteEnDessous(c);
            }
        }
        p.melangerCarte();
        return p;
    }
}