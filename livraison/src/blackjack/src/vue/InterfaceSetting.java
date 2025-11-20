package blackjack.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * This class defines various interface settings such as window dimensions, fonts, and colors
 * that are used across the game's graphical user interface.
 */
public class InterfaceSetting {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       
    /**
     * The width of the game window.
     */
    public static int WIDTH = screenSize.width;

    /**
     * The height of the game window.
     */
    public static int HEIGHT = screenSize.height;
    /**
     * The font used for general text in the interface.
     */
    public static Font TEXT_FONT = new Font("Arial", Font.BOLD, 14);

 

    public static Dimension DIMENSION_POCHE_VISIBLE = new Dimension(450, 260);
  
    public static Dimension DIMENSION_MAIN = new Dimension(700, 260);
    
    public static Dimension DIMENSION_PIOCHE= new Dimension(100, 140);

    public static Dimension DIMENSION_CROUPIER = new Dimension(WIDTH-50, HEIGHT/2+-50);

    public static Dimension DIMENSION_JOUEUR = new Dimension(WIDTH/6, 400);
    
    public static Dimension DIMENSION_ACTION = new Dimension(WIDTH/5+20, 400);

    public static int DECALAGE_CARTE = 60;

    public static Color GOLD_COLOR = new Color(255, 215, 0);
    public static Font MONTANT_FONT = new Font("Arial", Font.BOLD, 18);

    public static Dimension ARGENT_DIMENSION = new Dimension(150, 60);
    public static Color SOMBRE_COLOR = new Color(25, 25, 25);

}