package blackjack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler to parse the game settings XML file and initialize the game
 * constants in {@link UnchangeableSettings}.
 */
public class UnchangeableSettingsHandler extends DefaultHandler {
    private String currentElement = "";

    /**
     * Called at the start of an XML element. Saves the current element name for
     * processing its value.
     *
     * @param uri        the Namespace URI
     * @param localName  the local name (without prefix)
     * @param qName      the qualified name (with prefix)
     * @param attributes the attributes attached to the element
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName; // Sauvegarde le nom de la balise actuelle
    }

    /**
     * Processes the character data inside an XML element and updates the
     * corresponding constant in {@link UnchangeableSettings}.
     *
     * @param ch     the characters from the XML document
     * @param start  the start position in the array
     * @param length the number of characters to read
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();

        if (!value.isEmpty()) {
            switch (currentElement) {

                case "nbHumainsPlayers":
                    int intVal = Integer.parseInt(value);
                    if (intVal >= 0) {
                        UnchangeableSettings.NB_HUMAIN_PLAYERS = intVal;
                    }
                    break;
                case "nbIaPlayers":
                    UnchangeableSettings.NB_IA_PLAYERS = Integer.parseInt(value);
                    break;
                case "nbScoreMaxPlayer":
                    UnchangeableSettings.NB_SCORE_MAX_PLAYERS = Integer.parseInt(value);

                    break;
                case "nbScoreMaxCroupier": 
                    UnchangeableSettings.NB_SCORE_MAX_CROUPIER = Integer.parseInt(value);
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * Called at the end of an XML element. Resets the current element name.
     *
     * @param uri       the Namespace URI
     * @param localName the local name (without prefix)
     * @param qName     the qualified name (with prefix)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = ""; // Réinitialise l'élément actuel
    }
}
