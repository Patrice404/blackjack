package blackjack.model;

import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;
import blackjack.UnchangeableSettings;
import blackjack.strategies.Humain;
import blackjack.vue.BlackjackGUI;
import blackjack.vue.VueInfo;
import cartes.model.Carte;
import cartes.model.Paquet; // Ajout pour la clarté

/**
 * Représente le jeu de Blackjack lui-même, gérant l'état global du jeu,
 * la séquence des tours, la distribution des cartes, le calcul des scores
 * et la résolution des résultats.
 */
public class Blackjack {
    
    /** Le {@link Croupier} qui gère le jeu. */
    private Croupier croupier;
    
    /** La liste des {@link Joueur}s participant à la partie. */
    private List<Joueur> joueurs;

    /**
     * Construit une nouvelle instance de jeu de Blackjack.
     *
     * @param croupier Le Croupier qui mènera la partie.
     * @param joueurs La liste des joueurs participant à la partie.
     * @throws BJException Si les paramètres sont invalides (croupier null ou liste de joueurs vide).
     */
    public Blackjack(Croupier croupier, ArrayList<Joueur> joueurs) throws BJException {
        if (croupier == null || joueurs == null || joueurs.isEmpty()) {
            throw new BJException("Paramètres invalides");
        }
        this.croupier = croupier;
        this.joueurs = joueurs;
    }

    /**
     * Retourne l'objet Croupier du jeu.
     *
     * @return Le {@link Croupier}.
     */
    public Croupier getCroupier() {
        return this.croupier;
    }

    /**
     * Retourne la liste des joueurs participants.
     *
     * @return La {@code List<Joueur>}.
     */
    public List<Joueur> getJoueurs() {
        return this.joueurs;
    }

    /**
     * Exécute le déroulement d'une manche complète de Blackjack.
     * Gère l'initialisation, le tour des joueurs, le tour du croupier,
     * et le calcul des résultats.
     *
     * @param gui L'interface graphique utilisée pour interagir avec le joueur humain et rafraîchir la vue.
     */
    public void run(BlackjackGUI gui) {
        boolean croupierMustPlay = true;
        try {
            this.initialisation();
        } catch (BJException e) {
            e.printStackTrace();
        }

        // --- Tour des joueurs ---
        for (Joueur joueur : joueurs) {
            // Un joueur avec Blackjack initial passe son tour.
            if (joueur.detientBlackjack()) {
                continue;
            }

            boolean termine = false;
            while (!termine && !joueur.detientBust() && !joueur.getAbandon()) {

                Action action = null;

                // 1. Détermination de l'action (Humain ou IA)
                if (joueur.getStrategy() instanceof Humain) {
                    gui.setActionChoisie(null);
                    // Attente passive de l'action de l'utilisateur via l'IHM
                    synchronized (gui) {
                        try {
                            gui.wait(); 
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    action = gui.getActionChoisie();
                } else {
                    // IA joue
                    action = joueur.play(this.croupier);
                }

                // 2. Traitement de l’action
                switch (action) {
                    case DEMANDER_UNE_CARTE: // Hit
                        try {
                            this.croupier.donnerCarte(joueur, 1);
                        } catch (BJException e) {
                            System.out.println("Pas assez de carte de disponible");
                            return;
                        }
                        // Si l'unique joueur en jeu a busté, le croupier n'a pas besoin de révéler sa carte cachée.
                        if(joueur.detientBust() && this.joueurs.size()==1){
                            croupierMustPlay = false;    
                        }
                        break;
                    case NE_RIEN_FAIRE: // Stand
                        termine = true;
                        break;
                    case ABANDONNER: // Surrender
                        if(joueur.getNbCarte()==2){
                            // Le joueur gagne 0.5f fois la mise (récupère la moitié)
                            joueur.gagnerMise(0.5f); 
                            joueur.setAbandon(true);
                            new VueInfo(gui, joueur.getNom() + " a abandonné.");

                            // Si c'est le seul joueur, le croupier n'a plus à jouer.
                            if(this.joueurs.size()==1){
                                croupierMustPlay = false;
                            }
                        }else{
                            new VueInfo(gui,"Vous ne pouvez plus abandonner " + joueur.getNom());
                        }
                        termine = true; // Fin du tour après l'abandon
                        break;
                    case DOUBLER_SA_MISE: // Double Down
                        joueur.miser(joueur.getMise().getMontant()); // Double la mise
                        try {
                            this.croupier.donnerCarte(joueur, 1); // Tire une et unique carte
                        } catch (BJException e) {
                            e.printStackTrace();
                        }
                        termine = true; // Fin du tour après le doublement
                        break;

                }

                gui.rafraichir();

                // Délai pour la fluidité visuelle
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                     Thread.currentThread().interrupt();
                }
            }
        }
         
        // --- Tour du croupier ---
        boolean fin = false;
        while (croupierMustPlay && !fin && !croupier.detientBust()) {
            Action act = this.croupier.play(this.croupier); // CroupierIA décide
            if (act == Action.DEMANDER_UNE_CARTE) {
                this.croupier.choisirUneCarte();
            } else {
                fin = true; // Croupier fait Stand
            }
            gui.rafraichir();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
                 Thread.currentThread().interrupt();
            }
        }

        // --- Résultats finaux ---
        gui.rafraichir();
        calculerResultats(gui);
    }

    /**
     * Calcule les gains ou pertes de chaque joueur après la fin de la manche
     * et distribue/ramasse les montants en conséquence.
     *
     * @param gui L'interface graphique pour afficher les messages de résultat.
     */
    public void calculerResultats(BlackjackGUI gui) {
        int valeurCroupier = croupier.getScore();        

        for (Joueur joueur : joueurs) {
            if(!joueur.getAbandon()){
                int valeurJoueur = joueur.getScore();

                if (joueur.detientBust()) {
                    joueur.perdreMise();
                    new VueInfo(gui, joueur.getNom() + " a perdu (BUST).");
                } else if (croupier.detientBust()) {
                    // Croupier a Bust, tous les joueurs actifs gagnent
                    float multiplicateur = joueur.detientBlackjack() ? 2.5f : 2.0f;
                    joueur.gagnerMise(multiplicateur);
                    String message = joueur.detientBlackjack() ? " a gagné par Blackjack (Croupier BUST)." : " a gagné (Croupier BUST).";
                    new VueInfo(gui, joueur.getNom() + message);
                } else if (joueur.detientBlackjack() && !croupier.detientBlackjack()) {
                    // Le joueur a Blackjack et le Croupier n'en a pas (paie 3:2)
                    joueur.gagnerMise(2.5f); 
                    new VueInfo(gui, joueur.getNom() + " a gagné par Blackjack.");
                } else if (valeurJoueur > valeurCroupier) {
                    // Le joueur a un score plus élevé (paie 2:1)
                    joueur.gagnerMise(2);
                    new VueInfo(gui, joueur.getNom() + " a gagné.");
                } else if (valeurJoueur == valeurCroupier) {
                    // Égalité (Push)
                    joueur.recupererMise(); 
                    new VueInfo(gui, "Égalité entre " + joueur.getNom() + " et le croupier.");
                } else {
                    // Le joueur perd (Score inférieur ou Croupier a Blackjack)
                    this.croupier.getArgent().ajoutSomme(joueur.getMise().getMontant());
                    joueur.perdreMise();
                    new VueInfo(gui, joueur.getNom() + " perd contre le croupier.");
                }
            } else {
                // Si le joueur avait abandonné, réinitialise l'état d'abandon pour la prochaine manche.
                joueur.setAbandon(false); 
            }
        }
        gui.setMiser(false);
    }

    /**
     * Ramasse toutes les cartes jouées et les cartes visibles du croupier
     * et les replace dans la pioche ({@code pocheCache}) pour les mélanger.
     */
    public void melanger(){
        this.ramasserCarte(); // Ramasse toutes les cartes jouées
        
        // Transfère les cartes visibles du croupier vers la pioche
        for (Carte carte :  this.croupier.getPocheVisible().getListeCartes()) {
            this.croupier.getPocheCachee().ajouterCarteAuDessus(carte);
        }
        this.croupier.getPocheVisible().viderPaquet();
       
        this.croupier.getPocheCachee().melangerCarte();
    }

    /**
     * Ramasse toutes les cartes de la main du Croupier et de tous les Joueurs
     * et les place dans la poche visible du Croupier (la défausse temporaire).
     * Les scores et mains des acteurs sont réinitialisés.
     */
    public void ramasserCarte() {
        // Ramasse les cartes du Croupier
        for (Carte carte : this.croupier.getMain().getListeCartes()) {
            this.croupier.getPocheVisible().ajouterCarteEnDessous(carte);
        }
        this.croupier.getMain().viderPaquet();
        this.croupier.setScore(0);

        // Ramasse les cartes des Joueurs
        for (Joueur joueur : this.joueurs) {
            for (Carte carte : joueur.getMain().getListeCartes()) {
                this.croupier.getPocheVisible().ajouterCarteEnDessous(carte);
            }
            joueur.getMain().viderPaquet();
            joueur.setScore(0);
        }
    }

    /**
     * Prépare le début d'une nouvelle manche :
     * Mélange le paquet, ramasse toutes les cartes jouées (si nécessaire) et
     * distribue les cartes initiales (une carte à chaque joueur, une au croupier,
     * une seconde carte à chaque joueur).
     *
     * @throws BJException Si le Croupier manque de cartes pour la distribution.
     */
    public void initialisation() throws BJException {
        this.croupier.melangerPaquet();
        this.ramasserCarte(); // Assure que toutes les mains sont vides
        
        // Distribution (une carte chacun)
        for (Joueur joueur : this.joueurs) {
            this.croupier.donnerCarte(joueur, 1);
        }
        
        // Carte du croupier (visible)
        this.croupier.choisirUneCarte(); 
        
        // Distribution (seconde carte chacun)
        for (Joueur joueur : this.joueurs) {
            this.croupier.donnerCarte(joueur, 1);
        }
    }

    /**
     * Demande à toutes les IA de placer leur mise pour la manche.
     * Cette méthode est ignorée pour les joueurs humains (ils misent via l'IHM).
     */
    public void faireMiserIA(){
        for(Joueur joueur : this.joueurs){
            if(!(joueur.getStrategy() instanceof Humain)){
                joueur.miser(joueur.getStrategy().miser(joueur));
            }
        }
    }

    /**
     * Vérifie si tous les participants sont des IA.
     *
     * @return {@code true} si aucun joueur n'a de stratégie {@link Humain}, {@code false} sinon.
     */
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