package cartes.model;


public class FactoryPaquet {


    private static String[] couleur = {"Pique♠","Coeur♥","Carreau♦","Trefles♣"};


    private static String[] hauteur = {"2","3","4","5","6","7","8","9","10","Valet","Dame","Roi","As"};


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
