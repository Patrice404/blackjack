package blackjack.model;

import blackjack.UnchangeableSettings;
import blackjack.strategies.CroupierIA;
import blackjack.vue.VueInfo;
import cartes.model.*;
import cartes.utils.AbstractModeleEcoutable; // Ajout de l'import pour la super classe

/**
 * Représente le Croupier (la Banque) dans une partie de Blackjack.
 * Le Croupier agit à la fois comme un {@link Acteur} (il a une main et un score)
 * et comme le donneur et le gestionnaire des cartes de jeu.
 * <p>
 * Il utilise une stratégie {@link CroupierIA} spécifique et gère la pioche
 * ({@code pocheCache}) d'où sont tirées les cartes.
 */
public class Croupier extends Acteur {
    
    /**
     * Paquet temporaire pour les cartes du Croupier qui doivent être affichées face visible
     * après la manche (ex: cartes des joueurs). Non utilisé pour la pioche.
     */
    private Paquet pocheVisible;
    
    /**
     * Le paquet de cartes principal (la pioche) à partir duquel les cartes sont distribuées.
     */
    private Paquet pocheCache;

    /**
     * Construit un nouveau Croupier.
     * Le Croupier est initialisé avec une main vide, un montant d'argent nul,
     * et utilise une {@code CroupierIA}.
     *
     * @param nom Le nom du Croupier (ex: "Croupier", "Banque").
     * @param pochecache Le paquet initial de cartes (le sabot ou la pioche) utilisé pour la distribution.
     */
    public Croupier(String nom, Paquet pochecache) {
        // Appelle le constructeur de Acteur : nom, main vide, Argent(0), CroupierIA
        super(nom, new Paquet(), new Argent(0f), new CroupierIA());
        this.pocheCache = pochecache;
        this.pocheVisible = new Paquet();
    }

    /**
     * Mélange aléatoirement le paquet de cartes principal (la pioche).
     */
    public void melangerPaquet() {
        this.pocheCache.melangerCarte();
    }

    /**
     * Retire la première carte de la pioche ({@code pocheCache}) et l'ajoute
     * à la main du Croupier, puis met à jour son score.
     * <p>
     * Le calcul du score tient compte de la valeur spéciale de l'As, en utilisant
     * le réglage spécifique au Croupier ({@code NB_SCORE_MAX_CROUPIER}).
     */
    public void choisirUneCarte() {
        try {
            Carte c = this.pocheCache.retirerPremiereCarte();
            this.main.ajouterCarteAuDessus(c);
            
            // Logique de calcul du score du Croupier
            if (c.getValeurs().size() > 1) { // Est-ce un As ?
                if (this.score + 11 > UnchangeableSettings.NB_SCORE_MAX_CROUPIER) {
                    this.score++;
                } else {
                    this.score += 11;
                }
            } else {
                this.score += (int) c.getValeurs().get(0);
            }
            
            this.fireChangement();
            Thread.sleep(2000); // Pause pour simuler le temps de distribution

        } catch (Exception e) {
            System.err.println("Erreur lors du tirage de carte par le Croupier : " + e.getMessage());
        }
    }

    /**
     * Distribue un nombre spécifié de cartes de la pioche au joueur.
     * Les cartes sont retirées de la pioche du Croupier et ajoutées à la main du joueur.
     *
     * @param joueur Le {@link Joueur} destinataire des cartes.
     * @param nb Le nombre de cartes à distribuer.
     * @throws BJException Si le paquet n'a pas assez de cartes.
     */
    public void donnerCarte(Joueur joueur, int nb) throws BJException {
        try {
            for (int i = 0; i < nb; i++) {
                Carte c = this.pocheCache.retirerPremiereCarte();
                if (c == null) {
                    new VueInfo(null, "Il n'y a pas assez de carte");
                    throw new BJException("Il n'y a pas assez de carte");
                }
                joueur.recevoirCarte(c);
                Thread.sleep(1000); // Pause pour simuler le temps de distribution
            }

        } catch (BJException e) {
            throw e; // Relaie l'exception BJException
        } catch (Exception e) {
            System.err.println("Erreur lors de la distribution de carte : " + e.getMessage());
        }
    }

    /**
     * Ajoute une carte au paquet visible du Croupier (utilisé pour les cartes
     * ramassées ou les cartes de défausse, selon l'implémentation).
     *
     * @param carte La carte à rendre visible.
     */
    public void mettreDansPocheVisible(Carte carte) {
        this.pocheVisible.ajouterCarteAuDessus(carte);
    }

    /**
     * Retourne le paquet de cartes temporairement visibles géré par le Croupier.
     *
     * @return Le {@link Paquet} visible.
     */
    public Paquet getPocheVisible() {
        return this.pocheVisible;
    }

    /**
     * Retourne la pioche de cartes non distribuées (le sabot).
     *
     * @return Le {@link Paquet} caché (la pioche).
     */
    public Paquet getPocheCachee() {
        return this.pocheCache;
    }

    /**
     * Ajoute une somme d'argent au Croupier (représentant les gains de la banque).
     *
     * @param somme Le montant positif à ramasser.
     * @throws Exception Si la somme est négative.
     */
    public void ramasserSomme(float somme) throws Exception {
        if (somme < 0) {
            throw new Exception("La somme ne peut être inférieur à zéro");
        }
        super.gagner(somme);
    }

    /**
     * Retourne le score actuel de la main du Croupier.
     *
     * @return Le score numérique.
     */
    @Override
    public int getScore() {
        return this.score;
    }
}