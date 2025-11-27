package blackjack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import blackjack.model.BJException;

public class UnchangeableSettings {

    public static String SETTINGS_FILE = "reglages.xml";

    // Game constants

    public static int NB_HUMAIN_PLAYERS;
    public static int NB_IA_PLAYERS;
    public static int NB_SCORE_MAX_PLAYERS;
    public static int NB_SCORE_MAX_CROUPIER;
    public static int PLAYER_WIDTH = 200;
    public static int PLAYER_HEIGHT = 300;

    /**
     * Loads settings from an XML configuration file.
     *
     * @param file the path to the configuration file
     */
    public static void loadSettings() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UnchangeableSettingsHandler handler = new UnchangeableSettingsHandler();

            saxParser.parse(SETTINGS_FILE, handler);

        } catch (Exception e) {
            new BJException("Config file loading failed");
        }
    }
}
