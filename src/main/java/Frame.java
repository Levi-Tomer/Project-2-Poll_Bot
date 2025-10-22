import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Frame extends JFrame {
    private MenuPanel menuPanel;
    private DefaultPollQuestionPanel questionPanelTop;
    private AdditionalPollQuestionPanel questionPanelMiddle;
    private AdditionalPollQuestionPanel questionPanelBottom;
    private BottomWritePollPanel bottomPollCreationPanel;
    private ResultPanel resultPanel;
    private final TelegramBot bot;


    // Constructor......................................................................................................
    public Frame(TelegramBot bot) {
        super("Polls For Telegram Bot");
        this.bot = bot;

        // Setting up the window:
        this.setSize(Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
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
        this.questionPanelMiddle = new AdditionalPollQuestionPanel(Utils.QUESTION_PANEL_HEIGHT);
        this.questionPanelBottom = new AdditionalPollQuestionPanel(Utils.QUESTION_PANEL_HEIGHT * 2);
        this.bottomPollCreationPanel = new BottomWritePollPanel();
        this.resultPanel = new ResultPanel();
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.resultPanel.setVisible(false);
        this.add(questionPanelTop);
        this.add(questionPanelMiddle);
        this.add(questionPanelBottom);
        this.add(bottomPollCreationPanel);
        this.add(resultPanel);
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
        if (false) { // !!!!!!!!!!!Attention! This statement should be: "if (this.bot.getSubscribers().size() < 3)" but it's not set for testing purposes.
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
    The method makes sure the questions and the answers are not null and that the string isn't empty.
    */
    private boolean validatePollInput(String text) {
        return text != null && !text.isEmpty();
    }

    /*
    Aid method that receives the options for a question to be published in the poll, and creating an ArrayList that
    will be returned and validated in the "publishPoll" method before publishing.
    */
    private ArrayList validateAnswerOptions(String o1, String o2, String o3, String o4) {
        ArrayList<String> options = new ArrayList<>(4);
        if (validatePollInput(o1)) {
            options.add(o1);
        }
        if (validatePollInput(o2)) {
            options.add(o2);
        }
        if (validatePollInput(o3)) {
            options.add(o3);
        }
        if (validatePollInput(o4)) {
            options.add(o4);
        }
        return options;
    }

    /*
    This method is identifying the number of questions and possible options from the "write your own"
    to be published in the poll.
    It arranges the information received from the poll creation GUI panel, validate it, aware user in case of a problem
    and in case everything is ok - calls the sendPoll method from the TelegramBot class.
    */
    private void publishPoll() {
        System.out.println("Reached the method from the Frame class.");


        // Creating and validating poll question 1 and answers:
        String question1 = this.questionPanelTop.getPollQuestion().getText();
        if (validatePollInput(question1)) {
            String q1Option1 = this.questionPanelTop.getAnswer1().getText();
            String q1Option2 = this.questionPanelTop.getAnswer2().getText();
            String q1Option3 = this.questionPanelTop.getAnswer3().getText();
            String q1Option4 = this.questionPanelTop.getAnswer4().getText();
            List<String> question1Options = validateAnswerOptions(q1Option1, q1Option2, q1Option3, q1Option4);
            if (question1Options.size() >= 2) {
                this.bot.sendPoll(question1, question1Options);
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
            String question2 = this.questionPanelMiddle.getPollQuestion().getText();
            if (validatePollInput(question2)) {
                String q2Option1 = this.questionPanelMiddle.getAnswer1().getText();
                String q2Option2 = this.questionPanelMiddle.getAnswer2().getText();
                String q2Option3 = this.questionPanelMiddle.getAnswer3().getText();
                String q2Option4 = this.questionPanelMiddle.getAnswer4().getText();
                List<String> question2Options = validateAnswerOptions(q2Option1, q2Option2, q2Option3, q2Option4);
                if (question2Options.size() >= 2) {
                    this.bot.sendPoll(question2, question2Options);
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
            String question3 = this.questionPanelBottom.getPollQuestion().getText();
            if (validatePollInput(question3)) {
                String q3Option1 = this.questionPanelBottom.getAnswer1().getText();
                String q3Option2 = this.questionPanelBottom.getAnswer2().getText();
                String q3Option3 = this.questionPanelBottom.getAnswer3().getText();
                String q3Option4 = this.questionPanelBottom.getAnswer4().getText();
                List<String> question3Options = validateAnswerOptions(q3Option1, q3Option2, q3Option3, q3Option4);
                if (question3Options.size() >= 2) {
                    this.bot.sendPoll(question3, question3Options);
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

    /*
    This function is used to validate the delay poll publication time entered by the user (JTextField in the BottomWritePollPanel).
    */
    public static boolean isAllDigits(String text) {
        if (text == null || text.isEmpty()) return false;
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    // Getters & Setters................................................................................................
}
