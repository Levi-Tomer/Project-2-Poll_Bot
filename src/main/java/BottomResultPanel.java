import javax.swing.*;
import java.awt.*;

public class BottomResultPanel extends JPanel {
    private JButton backButton;

    // Constructor......................................................................................................
    public BottomResultPanel() {
        // Setting up the panel:
        this.setBounds(0, Utils.QUESTION_PANEL_HEIGHT * 3, Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT - Utils.QUESTION_PANEL_HEIGHT * 3);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating and adding the status task:
        JLabel statusHeadline = new JLabel("Once the poll is done, a button will appear to go back to the main menu.");
        Font statusHeadLineFont = new Font("Arial", Font.PLAIN, 15);
        statusHeadline.setFont(statusHeadLineFont);
        statusHeadline.setBounds(10, 10, Utils.WINDOW_WIDTH, 15);
        statusHeadline.setVisible(true);
        this.add(statusHeadline);
        // Creating and adding the status task.

        // Creating and adding the back button:
        this.backButton = new JButton("Back to Main Menu");
        backButton.setBounds(Utils.WINDOW_WIDTH / 2 - 100, 0, 200, 50);
        backButton.setBackground(Color.WHITE);
        backButton.setVisible(false);
        this.add(backButton);
        // Creating and adding the back button.
    }

    // toString.........................................................................................................

    // compareTo........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
    public JButton getBackButton() {
        return backButton;
    }
}
