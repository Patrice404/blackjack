package blackjack.strategies;

import java.util.List;
import java.util.Scanner;

import blackjack.model.*;

public class Humain implements Strategy {
    Scanner scanner = new Scanner(System.in);

    @Override
    public Action play(Joueur joueur, Croupier croupier) {
        return null;
    }
    @Override
    public float miser(Joueur joueur){
        return 0f;
    }
}