
package blackjack;

import java.util.ArrayList;
import cartes.model.*;
import blackjack.model.*;
import blackjack.strategies.*;
import blackjack.vue.BlackjackGUI;

public class MainClass {
  public static void main(String[] args) {

    UnchangeableSettings.loadSettings();
    Paquet pocheCache = FactoryPaquet.createPaquet52Carte();
    Croupier croupier = new Croupier("Alex",pocheCache);

  
    Joueur j1 = new Joueur("Zade", new Paquet(), new Humain(), new Argent(0f), new Argent(10000f));
    //Joueur j2 = new Joueur("IA", new Paquet(), new JoueurIA(), new Argent(0f), new Argent(10000f));

    ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    joueurs.add(j1);
    //joueurs.add(j2);

    Blackjack blackjack = new Blackjack(croupier, joueurs);

    BlackjackGUI blackjackGUI = new BlackjackGUI(blackjack);
    blackjackGUI.setVisible(true);
   

  }

}
