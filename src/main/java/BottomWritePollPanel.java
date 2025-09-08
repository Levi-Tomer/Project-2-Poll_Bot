import javax.swing.*;
import java.awt.*;

public class BottomWritePollPanel extends JPanel{
    private JButton backButton;
    private JButton publishButton;

    // Constructor......................................................................................................
    public BottomWritePollPanel() {
        // Setting up the panel:
        this.setBounds(0, Constants.QUESTION_PANEL_HEIGHT * 3, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT - Constants.QUESTION_PANEL_HEIGHT * 3);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating and adding the back button:
        this.backButton = new JButton("Back");
        backButton.setBounds(50, 0, 200, 50);
        backButton.setBackground(Color.WHITE);
        backButton.setVisible(true);
        this.add(backButton);
        // Creating and adding the back button.

        // Creating and adding the "delay poll publish" option:
        Integer[] delayOptions = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
        JComboBox<Integer> delayCombo = new JComboBox<>(delayOptions);
        delayCombo.setSelectedItem(0);
        delayCombo.setBounds(Constants.WINDOW_WIDTH / 2 - 50, 10, 100, 30);
        this.add(delayCombo);
        // Creating and adding description:
        JLabel delayDescription = new JLabel("Delay publish (minutes).");
        Font descriptionFont = new Font("Arial", Font.PLAIN, 15);
        delayDescription.setBounds(Constants.WINDOW_WIDTH / 2 - 70, 50, 140, 50);
        this.add(delayDescription);
        // Creating and adding description.
        // Creating and adding the "delay poll publish" option.

        // Creating and adding the continue/publish button:
        this.publishButton = new JButton("Publish poll");
        publishButton.setBounds(Constants.WINDOW_WIDTH - 250, 0, 200, 50);
        publishButton.setBackground(Color.WHITE);
        publishButton.setVisible(true);
        this.add(publishButton);
        // Creating and adding the continue/publish button.
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
    public JButton getBackButton() {
        return backButton;
    }

    public JButton getPublishButton() {
        return publishButton;
    }
}
