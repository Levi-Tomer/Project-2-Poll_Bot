import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private static final int WRITE_BUTTON_Y_LOCATION = 175;
    private static final int GPT_BUTTON_Y_LOCATION = 125 + WRITE_BUTTON_Y_LOCATION;
    private static final int EXIT_BUTTON_Y_LOCATION = 250 + WRITE_BUTTON_Y_LOCATION;
    private Button writePollButton;
    private Button gptPollButton;
    private Button exitButton;

    // Constructor......................................................................................................
    public MenuPanel() {
        // Setting up the panel:
        this.setBounds(0, 0, Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Setting up the headline:
        JLabel menuHeadline1 = new JLabel("Hello Shai.");
        JLabel menuHeadline2 = new JLabel("What poll would you like to create today?");
        Font headLineFont = new Font("Arial", Font.PLAIN, 25);
        menuHeadline1.setFont(headLineFont);
        menuHeadline2.setFont(headLineFont);
        menuHeadline1.setBounds(50, 50, Utils.WINDOW_WIDTH, 25);
        menuHeadline2.setBounds(50, 75, Utils.WINDOW_WIDTH, 25);
        this.add(menuHeadline1);
        this.add(menuHeadline2);
        // Setting up the headline.

        // Setting up the buttons:
        // Creating and adding the "Write poll" button:
        this.writePollButton = new Button("Write your own", 50, WRITE_BUTTON_Y_LOCATION);
        this.writePollButton.setBackground(Color.WHITE);
        this.add(writePollButton);
        // Creating and adding the "Write poll" button.
        // Creating and adding the "GPT poll" button:
        this.gptPollButton = new Button("Use chatGPT", 50, GPT_BUTTON_Y_LOCATION);
        this.gptPollButton.setBackground(Color.WHITE);
        this.add(gptPollButton);
        // Creating and adding the "GPT poll" button.
        // Creating and adding the Exit button:
        this.exitButton = new Button("Exit", 50, EXIT_BUTTON_Y_LOCATION);
        this.exitButton.setBackground(Color.WHITE);
        this.add(exitButton);
        // Creating and adding the Exit button.
        // Setting up the buttons.

        // Setting up buttons explanation:
        Font buttonExplanationFont = new Font("Arial", Font.PLAIN, 15);
        // Creating and adding the "Write poll" button explanation:
        JLabel writeExplanation = new JLabel ("Create the poll in your own words.");
        writeExplanation.setFont(buttonExplanationFont);
        writeExplanation.setBounds(Utils.MAIN_MENU_BUTTON_WIDTH + 75, WRITE_BUTTON_Y_LOCATION + 60, Utils.WINDOW_WIDTH, 15);
        this.add(writeExplanation);
        // Creating and adding the "Write poll" button explanation.
        // Creating and adding the "GPT poll" button explanation:
        JLabel gptExplanation = new JLabel ("Ask chatGPT for help creating the poll.");
        gptExplanation.setFont(buttonExplanationFont);
        gptExplanation.setBounds(Utils.MAIN_MENU_BUTTON_WIDTH + 75, GPT_BUTTON_Y_LOCATION + 60, Utils.WINDOW_WIDTH, 15);
        this.add(gptExplanation);
        // Creating and adding the "GPT poll" button explanation.
        // Setting up buttons explanations.
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
