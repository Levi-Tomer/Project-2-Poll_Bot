import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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

        // "Publish poll" button from "BottomWritePollPanel" class:
        this.bottomPollCreationPanel.getPublishButton().addActionListener(e -> publishPoll());
        // "Publish poll" button from "BottomWritePollPanel" class.
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

    /*
    This method is for identifying the number of questions and possible answers to be published in the poll.
    It arranges the information received from the poll creation GUI panels and calls the sending poll method from the
    TelegramBot class.
    */
    private void publishPoll() {
        System.out.println("Reached the method from the Frame class.");
        // Creating poll question 1:
        String pollQuestion1 = this.questionPanelTop.getPollQuestion();
        List<String> optionsQuestion1 = new ArrayList<>(4);
        optionsQuestion1.add(this.questionPanelTop.getAnswer1());
        optionsQuestion1.add(this.questionPanelTop.getAnswer2());
        System.out.println("Created the first question.");
        if (this.questionPanelTop.isPossibleAnswer3()) {
            optionsQuestion1.add(this.questionPanelTop.getAnswer3());
            System.out.println("Added third option to question one.");
        }
        if (this.questionPanelTop.isPossibleAnswer4()) {
            optionsQuestion1.add(this.questionPanelTop.getAnswer4());
            System.out.println("Added fourth option to question one.");
        }
        this.bot.sendPoll(pollQuestion1, optionsQuestion1);
        // Creating poll question 1.

        // Creating poll question 2:
        if (this.questionPanelMiddle.isAdditionalQuestion()) {
            System.out.println("Added the second question.");
            String pollQuestion2 = this.questionPanelMiddle.getPollQuestion();
            List<String> optionsQuestion2 = new ArrayList<>(4);
            optionsQuestion2.add(this.questionPanelMiddle.getAnswer1());
            optionsQuestion2.add(this.questionPanelMiddle.getAnswer2());
            if (this.questionPanelMiddle.isPossibleAnswer3()) {
                optionsQuestion2.add(this.questionPanelMiddle.getAnswer3());
                System.out.println("Added third option to question two.");
            }
            if (this.questionPanelMiddle.isPossibleAnswer4()) {
                optionsQuestion2.add(this.questionPanelMiddle.getAnswer4());
                System.out.println("Added fourth option to question two.");
            }
            this.bot.sendPoll(pollQuestion2, optionsQuestion2);
        }
        // Creating poll question 2.

        // Creating poll question 3:
        if (this.questionPanelBottom.isAdditionalQuestion()) {
            System.out.println("Added the third question.");
            String pollQuestion3 = this.questionPanelBottom.getPollQuestion();
            List<String> optionsQuestion3 = new ArrayList<>(4);
            optionsQuestion3.add(this.questionPanelBottom.getAnswer1());
            optionsQuestion3.add(this.questionPanelBottom.getAnswer2());
            if (this.questionPanelBottom.isPossibleAnswer3()) {
                optionsQuestion3.add(this.questionPanelBottom.getAnswer3());
                System.out.println("Added third option to question three.");
            }
            if (this.questionPanelBottom.isPossibleAnswer4()) {
                optionsQuestion3.add(this.questionPanelBottom.getAnswer4());
                System.out.println("Added fourth option to question two.");
            }
            this.bot.sendPoll(pollQuestion3, optionsQuestion3);
        }
        // Creating poll question 3.
    }

    // Getters & Setters................................................................................................
}
