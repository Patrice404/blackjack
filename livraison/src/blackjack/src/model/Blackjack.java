package blackjack.model;

import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;
import blackjack.UnchangeableSettings;
import blackjack.strategies.Humain;
import blackjack.vue.BlackjackGUI;
import blackjack.vue.VueInfo;
import cartes.model.Carte;

public class Blackjack {
    private Croupier croupier;
    private List<Joueur> joueurs;

    public Blackjack(Croupier croupier, ArrayList<Joueur> joueurs) {
        if (croupier == null || joueurs == null || joueurs.isEmpty()) {
            new BJException("Paramètres invalides");
        }
        this.croupier = croupier;
        this.joueurs = joueurs;
    }

    public Croupier getCroupier() {
        return this.croupier;
    }

    public List<Joueur> getJoueurs() {
        return this.joueurs;
    }

    public void run(BlackjackGUI gui) {
        try {
            this.initialisation();
        } catch (BJException e) {
            e.printStackTrace();
        }

       
        for (Joueur joueur : joueurs) {
            if (joueur.detientBlackjack()) {
                continue;
            }

            boolean termine = false;
            while (!termine && !joueur.detientBust()) {

                Action action = null;

                // --- Si joueur humain ---
                if (joueur.getStrategy() instanceof Humain) {
                    gui.setActionChoisie(null);
                    // Attente d'un clic sur l'interface
                    synchronized (gui) {
                        try {
                            gui.wait(); // on attend notify()
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    action = gui.getActionChoisie();

                } else {
                    // --- Sinon IA ---
                    action = joueur.play(this.croupier);
                }

                // --- Traitement de l’action ---
                switch (action) {
                    case DEMANDER_UNE_CARTE:
                        try {
                            this.croupier.donnerCarte(joueur, 1);
                        } catch (BJException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case NE_RIEN_FAIRE:
                        termine = true;
                        break;
                    case ABANDONNER:
                        //joueur.gagnerMise(0.5f);
                        //joueur.perdreMise();
                        break;
                    case DOUBLER_SA_MISE:
                        joueur.miser(joueur.getMise().getMontant());
                        try {
                            this.croupier.donnerCarte(joueur, 1);
                        } catch (BJException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;

                }

                gui.rafraichir();

                // petit délai pour fluidité visuelle
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        }
        //Si un joueur a buster
         
        // --- Tour du croupier ---
        boolean fin = false;
        while (!fin && !croupier.detientBust()) {
            Action act = this.croupier.play(this.croupier);
            if (act == Action.DEMANDER_UNE_CARTE) {
                this.croupier.choisirUneCarte();
                ;
            } else {
                fin = true;
            }
            gui.rafraichir();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }

        // --- Résultats finaux ---
        gui.rafraichir();
        calculerResultats(gui);

    }

    public void calculerResultats(BlackjackGUI gui) {
        int valeurCroupier = croupier.getScore();        

        for (Joueur joueur : joueurs) {

            int valeurJoueur = joueur.getScore();

            if (joueur.detientBust()) {
                joueur.perdreMise();
                new VueInfo(gui, joueur.getNom() + " a perdu (BUST).");
                gui.setMiser(false);
            } else if (croupier.detientBust()) {
                joueur.gagnerMise(2); // le croupier a perdu → le joueur gagne double
                if (joueur.detientBlackjack()) {
                    new VueInfo(gui, joueur.getNom() + " à gagner par Blackjack.");
                    gui.setMiser(false);

                } else {
                    new VueInfo(gui, joueur.getNom() + " à ganger.").setVisible(true);
                    gui.setMiser(false);

                }
            } else if (joueur.detientBlackjack() && !croupier.detientBlackjack()) {
                joueur.gagnerMise(2.5f); // Blackjack payé 3:2
                new VueInfo(gui, joueur.getNom() + " à gagner par Blackjack");
                gui.setMiser(false);

            } else if (valeurJoueur > valeurCroupier) {
                joueur.gagnerMise(2);
                new VueInfo(gui, joueur.getNom() + " à gagner.");
                gui.setMiser(false);

            } else if (valeurJoueur == valeurCroupier) {
                joueur.recupererMise(); // égalité → on rend la mise
                new VueInfo(gui, "Égalité.").setVisible(true);
                gui.setMiser(false);

            } else {
                this.croupier.getArgent().ajoutSomme(joueur.getMise().getMontant());
                joueur.perdreMise();
                new VueInfo(gui, joueur.getNom() + " perd contre le croupier.");
                gui.setMiser(false);

            }
        }
    }

    public void melanger(){
        this.ramasserCarte();
        for (Carte carte :  this.croupier.getPocheVisible().getListeCartes()) {
            this.croupier.getPocheCachee().ajouterCarteAuDessus(carte);
        }
        this.croupier.getPocheVisible().viderPaquet();
       
        this.croupier.getPocheCachee().melangerCarte();
    }

    public void ramasserCarte() {
        for (Carte carte : this.croupier.getMain().getListeCartes()) {
            this.croupier.getPocheVisible().ajouterCarteEnDessous(carte);
        }
        this.croupier.getMain().viderPaquet();
        this.croupier.setScore(0);

        for (Joueur joueur : this.joueurs) {
            for (Carte carte : joueur.getMain().getListeCartes()) {
                this.croupier.getPocheVisible().ajouterCarteEnDessous(carte);
            }
            joueur.getMain().viderPaquet();
            joueur.setScore(0);
        }
    }

    public void initialisation() throws BJException {
        this.croupier.melangerPaquet();
        this.ramasserCarte();
        // partage des cartes
        for (Joueur joueur : this.joueurs) {
            this.croupier.donnerCarte(joueur, 1);
        }
        this.croupier.choisirUneCarte();
        for (Joueur joueur : this.joueurs) {
            this.croupier.donnerCarte(joueur, 1);
        }

    }

    public void faireMiserIA(){
        for(Joueur joueur : this.joueurs){
            if(!(joueur.getStrategy() instanceof Humain)){
                joueur.miser(joueur.getStrategy().miser(joueur));
            }
        }
    }

    public boolean queDesIA(){
        boolean reponse=true;
        for(Joueur joueur : this.joueurs){
            if(joueur.getStrategy() instanceof Humain){
                reponse=false;
            }
        }
        return reponse;
    }
}
