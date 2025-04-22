import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer; // Importăm specific javax.swing.Timer

public class MemoryGame extends JFrame {
    private final int rows = 3;
    private final int cols = 4;
    private final String[] imageNames = {
            "Afrodisiaco", "CosaNuestra", "PlayaSaturno",
            "Saturno", "TrapCakeVol2", "Viceversa"
    };

    private final ArrayList<Card> cards = new ArrayList<>(); // Folosim ArrayList în loc de List
    private Card selectedCard1 = null;
    private Card selectedCard2 = null;
    private int pairsFound = 0;
    private final ImageIcon backIcon = new ImageIcon(getClass().getResource("/resources/Spate.png"));

    public MemoryGame() {
        setTitle("Memory Game cu Imagini");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(rows, cols));
        setSize(600, 600);

        loadCards();
        Collections.shuffle(cards);
        for (Card card : cards) {
            add(card);
        }

        setVisible(true);
    }

    private void loadCards() {
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + name + ".png"));
            Card card1 = new Card(icon);
            Card card2 = new Card(icon);
            cards.add(card1);
            cards.add(card2);
        }
    }

    private class Card extends JButton {
        private final ImageIcon frontIcon;
        private boolean revealed = false;
        private boolean matched = false;

        public Card(ImageIcon frontIcon) {
            this.frontIcon = frontIcon;
            setIcon(backIcon);
            addActionListener(e -> handleClick());
        }

        public void handleClick() {
            if (revealed || matched || selectedCard2 != null) return;

            reveal();
            if (selectedCard1 == null) {
                selectedCard1 = this;
            } else {
                selectedCard2 = this;
                Timer timer = new Timer(1000, e -> checkMatch());
                timer.setRepeats(false);
                timer.start();
            }
        }

        public void reveal() {
            setIcon(frontIcon);
            revealed = true;
        }

        public void hideAgain() {
            setIcon(backIcon);
            revealed = false;
        }

        public ImageIcon getFrontIcon() {
            return frontIcon;
        }

        public void setMatched(boolean value) {
            matched = value;
        }
    }

    private void checkMatch() {
        if (selectedCard1.getFrontIcon().toString().equals(selectedCard2.getFrontIcon().toString())) {
            selectedCard1.setMatched(true);
            selectedCard2.setMatched(true);
            pairsFound++;
            if (pairsFound == imageNames.length) {
                JOptionPane.showMessageDialog(this, "Felicitări! Ai găsit toate perechile!");
                dispose();
                System.exit(0);
            }
        } else {
            selectedCard1.hideAgain();
            selectedCard2.hideAgain();
        }

        selectedCard1 = null;
        selectedCard2 = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}