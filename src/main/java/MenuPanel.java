import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private Button writePollButton;
    private Button gptPollButton;
    private Button exitButton;
    private int writePollButtonYLocation = 175;
    private int gptPollButtonYLocation = 300;

    // Constructor......................................................................................................
    public MenuPanel() {
        // Setting up the panel:
        this.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Setting up the headline:
        JLabel menuHeadline1 = new JLabel("Hello Shai.");
        JLabel menuHeadline2 = new JLabel("What poll would you like to create today?");
        Font headLineFont = new Font("Arial", Font.PLAIN, 25);
        menuHeadline1.setFont(headLineFont);
        menuHeadline2.setFont(headLineFont);
        menuHeadline1.setBounds(50, 50, Constants.WINDOW_WIDTH, 25);
        menuHeadline2.setBounds(50, 75, Constants.WINDOW_WIDTH, 25);
        menuHeadline1.setVisible(true);
        menuHeadline2.setVisible(true);
        this.add(menuHeadline1);
        this.add(menuHeadline2);
        // Setting up the headline.

        // Setting up the buttons:
        Font buttonExplanationFont = new Font("Arial", Font.PLAIN, 15);
        // Write poll button:
        this.writePollButton = new Button("Write your own", 50, writePollButtonYLocation);
        this.writePollButton.setBackground(Color.WHITE);
        // Write poll button.
        // Write poll button explanation:
        JLabel writeExplanation = new JLabel ("Create the poll in your own words.");
        writeExplanation.setFont(buttonExplanationFont);
        writeExplanation.setBounds(50 + Constants.MAIN_MENU_BUTTON_WIDTH + 25, writePollButtonYLocation + Constants.MAIN_MENU_BUTTON_HEIGHT - 15, Constants.WINDOW_WIDTH, 15);
        this.add(writeExplanation);
        // Write poll button explanation.
        // GPT poll button:
        this.gptPollButton = new Button("Use chatGPT", 50, gptPollButtonYLocation);
        this.gptPollButton.setBackground(Color.WHITE);
        // GPT poll button.
        // GPT poll button explanation:
        JLabel gptExplanation = new JLabel ("Ask chatGPT for help creating the poll.");
        gptExplanation.setFont(buttonExplanationFont);
        gptExplanation.setBounds(50 + Constants.MAIN_MENU_BUTTON_WIDTH + 25, gptPollButtonYLocation + Constants.MAIN_MENU_BUTTON_HEIGHT - 15, Constants.WINDOW_WIDTH, 15);
        this.add(gptExplanation);
        // GPT poll button explanation.
        // Exit button:
        this.exitButton = new Button("Exit", 50, 425);
        this.exitButton.setBackground(Color.WHITE);
        // Exit button.
        // Setting up the buttons.

        this.add(writePollButton);
        this.add(gptPollButton);
        this.add(exitButton);
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
    public Button getWritePollButton() {
        return writePollButton;
    }

    public Button getGptPollButton() {
        return gptPollButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
