import javax.swing.*;

public class Frame extends JFrame {
    private MenuPanel menuPanel;
    private QuestionPanelTop questionPanelTop;
    private QuestionPanelMiddle questionPanelMiddle;
    private QuestionPanelBottom questionPanelBottom;
    private BottomPollCreationPanel bottomPollCreationPanel;
    private final TelegramBot bot;


    // Constructor......................................................................................................
    public Frame(TelegramBot bot) {
        super("Polls For Telegram Bot");
        this.bot = bot;

        // Setting up window:
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        // Setting up window.

        // Adding the main menu:
        this.menuPanel = new MenuPanel();
        this.menuPanel.setVisible(true);
        this.add(menuPanel);
        // Adding the main menu.

        // Adding the "write your own poll" interface:
        this.questionPanelTop = new QuestionPanelTop();
        this.questionPanelMiddle = new QuestionPanelMiddle();
        this.questionPanelBottom = new QuestionPanelBottom();
        this.bottomPollCreationPanel = new BottomPollCreationPanel();
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.add(questionPanelTop);
        this.add(questionPanelMiddle);
        this.add(questionPanelBottom);
        this.add(bottomPollCreationPanel);
        // Adding the "write your own poll" interface.

        // Main menu buttons listeners:
        this.menuPanel.getWritePollButton().addActionListener(e -> moveToWritePollPanel());
        this.menuPanel.getGptPollButton().addActionListener(e -> moveToGptPollPanel());
        this.menuPanel.getExitButton().addActionListener(e -> {System.exit(0);});
        // Main menu buttons listeners.

        // Back to main menu from "Write poll" menu:
        this.bottomPollCreationPanel.getBackButton().addActionListener(e -> moveBackToMainMenuFromWritePollPanel());
        // Back to main menu from "Write poll" menu.
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................
    private void moveToWritePollPanel() {
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

    private void moveToGptPollPanel() {
        if (this.bot.getSubscribers().size() < 3) {    // this.bot.getSubscribers().size() < 3
            JOptionPane.showMessageDialog(this,
                    "You need to have at least 3 subscribers to create a poll.\nCurrent subscribers: " + this.bot.getSubscribers().size(),
                    "Error: not enough subscribers", JOptionPane.PLAIN_MESSAGE);
        } else {
            this.menuPanel.setVisible(false);
            // Here the chatGPT poll creating panel should be.
        }
    }

    private void moveBackToMainMenuFromWritePollPanel() {
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.menuPanel.setVisible(true);
    }

    // Getters & Setters................................................................................................
}
