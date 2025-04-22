import javax.swing.*;

public class MemoryButton extends JButton {
    private final ImageIcon frontImage;
    private final ImageIcon backImage;
    private boolean revealed = false;
    private boolean matched = false;

    public MemoryButton(ImageIcon frontImage, ImageIcon backImage) {
        this.frontImage = frontImage;
        this.backImage = backImage;
        setIcon(backImage);
        addActionListener(e -> handleClick());
    }

    public void handleClick() {
        if (revealed || matched) return;
        reveal();
    }

    public void reveal() {
        if (!matched) {
            setIcon(frontImage);
            revealed = true;
        }
    }

    public void hide() {
        if (!matched) {
            setIcon(backImage);
            revealed = false;
        }
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
        revealed = true;  // The card stays revealed if matched
    }

    public ImageIcon getImage() {
        return frontImage;
    }
}
