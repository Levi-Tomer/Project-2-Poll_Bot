import javax.swing.*;

public class Frame extends JFrame {
    private MenuPanel menuPanel;
    private DefaultPollQuestionPanel questionPanelTop;
    private AdditionalPollQuestionPanel questionPanelMiddle;
    private AdditionalPollQuestionPanel questionPanelBottom;
    private BottomWritePollPanel bottomPollCreationPanel;
    private final TelegramBot bot;


    // Constructor......................................................................................................
    public Frame(TelegramBot bot) {
        super("Polls For Telegram Bot");
        this.bot = bot;

        // Setting up the window:
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        // Setting up the window.

        // Creating and adding the main menu:
        this.menuPanel = new MenuPanel();
        this.menuPanel.setVisible(true);
        this.add(menuPanel);
        // Creating and adding the main menu.

        // Creating and adding the "write your own poll" GUI:
        this.questionPanelTop = new DefaultPollQuestionPanel();
        this.questionPanelMiddle = new AdditionalPollQuestionPanel(Constants.QUESTION_PANEL_HEIGHT);
        this.questionPanelBottom = new AdditionalPollQuestionPanel(Constants.QUESTION_PANEL_HEIGHT * 2);
        this.bottomPollCreationPanel = new BottomWritePollPanel();
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.add(questionPanelTop);
        this.add(questionPanelMiddle);
        this.add(questionPanelBottom);
        this.add(bottomPollCreationPanel);
        // Creating and adding the "write your own poll" GUI.

        // Main menu buttons listeners:
        this.menuPanel.getWritePollButton().addActionListener(e -> moveToWritePollInterface());
        this.menuPanel.getGptPollButton().addActionListener(e -> moveToGptPollInterface());
        this.menuPanel.getExitButton().addActionListener(e -> {System.exit(0);});
        // Main menu buttons listeners.

        // Back to main menu from "Write poll" menu:
        this.bottomPollCreationPanel.getBackButton().addActionListener(e -> moveBackToMainMenuFromWritePollInterface());
        // Back to main menu from "Write poll" menu.
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................
    private void moveToWritePollInterface() {
        if (false) {    // this.bot.getSubscribers().size() < 3
            JOptionPane.showMessageDialog(this,
                    "You need to have at least 3 subscribers to create a poll.\nCurrent subscribers: " + this.bot.getSubscribers().size(),
                    "Error: not enough subscribers", JOptionPane.PLAIN_MESSAGE);
        } else {
            this.menuPanel.setVisible(false);
            this.questionPanelTop.setVisible(true);
            this.questionPanelMiddle.setVisible(true);
            this.questionPanelBottom.setVisible(true);
            this.bottomPollCreationPanel.setVisible(true);
        }
    }

    private void moveToGptPollInterface() {
        if (this.bot.getSubscribers().size() < 3) {    // this.bot.getSubscribers().size() < 3
            JOptionPane.showMessageDialog(this,
                    "You need to have at least 3 subscribers to create a poll.\nCurrent subscribers: " + this.bot.getSubscribers().size(),
                    "Error: not enough subscribers", JOptionPane.PLAIN_MESSAGE);
        } else {
            this.menuPanel.setVisible(false);
            // Here the chatGPT poll creation interface should be.
        }
    }

    private void moveBackToMainMenuFromWritePollInterface() {
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.menuPanel.setVisible(true);
    }

    // Getters & Setters................................................................................................
}
