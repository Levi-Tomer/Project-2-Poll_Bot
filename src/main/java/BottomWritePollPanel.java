import javax.swing.*;
import java.awt.*;

public class BottomWritePollPanel extends JPanel{
    private JButton backButton;

    // Constructor......................................................................................................
    public BottomWritePollPanel() {
        // Setting up the panel:
        this.setBounds(0, Constants.QUESTION_PANEL_HEIGHT * 3, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT - Constants.QUESTION_PANEL_HEIGHT * 3);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating the continue/publish button:
        JButton publish = new JButton("Publish poll");
        publish.setBounds(Constants.WINDOW_WIDTH - 250, 0, 200, 50);
        publish.setBackground(Color.WHITE);
        publish.setVisible(true);
        this.add(publish);
        // Creating the continue/publish button.

        // Creating the back button:
        this.backButton = new JButton("Back");
        backButton.setBounds(50, 0, 200, 50);
        backButton.setBackground(Color.WHITE);
        backButton.setVisible(true);
        this.add(backButton);
        // Creating the back button.
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
    public JButton getBackButton() {
        return backButton;
    }
}
