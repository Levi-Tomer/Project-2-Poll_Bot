import org.w3c.dom.ls.LSOutput;

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
    Aid method to be used with the question and answers returning from the "write poll" GUI.
    The method makes sure the question or the answers are not null and that the string isn't empty.
    */
    private boolean validatingPollText (String text) {
        return text != null && !text.isEmpty();
    }

    /*
    This method is for identifying the number of questions and possible answers to be published in the poll.
    It arranges the information received from the poll creation GUI panels and calls the sending poll method from the
    TelegramBot class.
    */
    private void publishPoll() {
        System.out.println("Reached the method from the Frame class.");
        // Creating and validating poll question 1 and answers:
        if (validatingPollText(questionPanelTop.getPollQuestion().getText())) {
            String pollQuestion1 = this.questionPanelTop.getPollQuestion().getText();
            List<String> optionsQuestion1 = new ArrayList<>(4);
            if (validatingPollText(this.questionPanelTop.getAnswer1().getText())) {
                optionsQuestion1.add(this.questionPanelTop.getAnswer1().getText());
                System.out.println("question 1 answer 1 added");
            }
            if (validatingPollText(this.questionPanelTop.getAnswer2().getText())) {
                optionsQuestion1.add(this.questionPanelTop.getAnswer2().getText());
                System.out.println("question 1 answer 2 added");
            }
            if (this.questionPanelTop.isPossibleAnswer3()) {
                if (validatingPollText(this.questionPanelTop.getAnswer3().getText())) {
                    optionsQuestion1.add(this.questionPanelTop.getAnswer3().getText());
                    System.out.println("question 1 answer 3 added");
                }
            }
            if (this.questionPanelTop.isPossibleAnswer4()) {
                if (validatingPollText(this.questionPanelTop.getAnswer4().getText())) {
                    optionsQuestion1.add(this.questionPanelTop.getAnswer4().getText());
                    System.out.println("question 1 answer 4 added");
                }
            }
            if (optionsQuestion1.size() >= 2) {
                this.bot.sendPoll(pollQuestion1, optionsQuestion1);
            } else {
                JOptionPane.showMessageDialog(this,
                        "You need to provide at least 2 possible options for question 1.",
                        "Error: not enough options", JOptionPane.PLAIN_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "You left question 1 empty. Please write your question in the textbox.",
                    "Error: question 1 empty", JOptionPane.PLAIN_MESSAGE);
        }
        // Creating and validating poll question 1 and answers.

        // Creating and validating poll question 2 and answers:
        if (this.questionPanelMiddle.isAdditionalQuestion()) {
            if (validatingPollText(questionPanelMiddle.getPollQuestion().getText())) {
                System.out.println("Added the second question.");
                String pollQuestion2 = this.questionPanelMiddle.getPollQuestion().getText();
                List<String> optionsQuestion2 = new ArrayList<>(4);
                if (validatingPollText(this.questionPanelMiddle.getAnswer1().getText())) {
                    optionsQuestion2.add(this.questionPanelMiddle.getAnswer1().getText());
                    System.out.println("question 2 answer 1 added");
                }
                if (validatingPollText(this.questionPanelMiddle.getAnswer2().getText())) {
                    optionsQuestion2.add(this.questionPanelMiddle.getAnswer2().getText());
                    System.out.println("question 2 answer 2 added");
                }
                if (this.questionPanelMiddle.isPossibleAnswer3()) {
                    if (validatingPollText(this.questionPanelMiddle.getAnswer3().getText())) {
                        optionsQuestion2.add(this.questionPanelMiddle.getAnswer3().getText());
                        System.out.println("question 2 answer 3 added");
                    }
                }
                if (this.questionPanelMiddle.isPossibleAnswer4()) {
                    if (validatingPollText(this.questionPanelMiddle.getAnswer4().getText())) {
                        optionsQuestion2.add(this.questionPanelMiddle.getAnswer4().getText());
                        System.out.println("question 2 answer 4 added");
                    }
                }
                if (optionsQuestion2.size() >= 2) {
                    this.bot.sendPoll(pollQuestion2, optionsQuestion2);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "You need to provide at least 2 possible options for question 2.",
                            "Error: not enough options", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "You left question 2 empty. Please write your question in the textbox or click the \"-\" button next to it to remove it.",
                        "Error: question 2 empty", JOptionPane.PLAIN_MESSAGE);
            }
        }
        // Creating and validating poll question 2 and answers.

        // Creating and validating poll question 3 and answers:
        if (this.questionPanelBottom.isAdditionalQuestion()) {
            if (validatingPollText(questionPanelBottom.getPollQuestion().getText())) {
                System.out.println("Added the third question.");
                String pollQuestion3 = this.questionPanelBottom.getPollQuestion().getText();
                List<String> optionsQuestion3 = new ArrayList<>(4);
                if (validatingPollText(this.questionPanelBottom.getAnswer1().getText())) {
                    optionsQuestion3.add(this.questionPanelBottom.getAnswer1().getText());
                    System.out.println("question 3 answer 1 added");
                }
                if (validatingPollText(this.questionPanelBottom.getAnswer2().getText())) {
                    optionsQuestion3.add(this.questionPanelBottom.getAnswer2().getText());
                    System.out.println("question 3 answer 2 added");
                }
                if (this.questionPanelBottom.isPossibleAnswer3()) {
                    if (validatingPollText(this.questionPanelBottom.getAnswer3().getText())) {
                        optionsQuestion3.add(this.questionPanelBottom.getAnswer3().getText());
                        System.out.println("question 3 answer 3 added");
                    }
                }
                if (this.questionPanelBottom.isPossibleAnswer4()) {
                    if (validatingPollText(this.questionPanelBottom.getAnswer4().getText())) {
                        optionsQuestion3.add(this.questionPanelBottom.getAnswer4().getText());
                        System.out.println("question 3 answer 4 added");
                    }
                }
                if (optionsQuestion3.size() >= 2) {
                    this.bot.sendPoll(pollQuestion3, optionsQuestion3);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "You need to provide at least 2 possible options for question 3.",
                            "Error: not enough options", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "You left question 3 empty. Please write your question in the textbox or click the \"-\" button next to it to remove it.",
                        "Error: question 3 empty", JOptionPane.PLAIN_MESSAGE);
            }
        }
        // Creating and validating poll question 3 and answers.
    }

    // Getters & Setters................................................................................................
}
