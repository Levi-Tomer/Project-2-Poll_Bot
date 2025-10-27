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

        // Back to main menu from "Result panel" menu:
        this.resultPanel.getBottomResultPanel().getBackButton().addActionListener(e -> moveBackToMainMenuFromResultPanel());
        // Back to main menu from "Result panel" menu.
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................
    private void moveToWritePollInterface() {
        if (false) { // !!!!!!!!!!!Attention! This statement should be: "if (this.bot.getSubscribers().size() < 3)" but it's not for testing purposes.
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

    private void moveToResultPanel() {
        this.resultPanel.setVisible(true);
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
    }

    private void moveBackToMainMenuFromWritePollInterface() {
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.menuPanel.setVisible(true);
    }

    private void moveBackToMainMenuFromResultPanel() {
        this.menuPanel.setVisible(true);
        this.resultPanel.setVisible(false);
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
    This function is used to validate the delay poll publication time entered by the user (JTextField in the BottomWritePollPanel).
    */
    public static boolean isAllDigits(String text) {
        if (text == null || text.isEmpty()) return false;
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private void trySendFromPanel(AdditionalPollQuestionPanel panel) {
        if (!panel.isAdditionalQuestion()) return;
        String q = panel.getPollQuestion().getText();
        if (!validatePollInput(q)) return;
        String a1 = panel.getAnswer1().getText();
        String a2 = panel.getAnswer2().getText();
        String a3 = panel.getAnswer3().getText();
        String a4 = panel.getAnswer4().getText();
        List<String> opts = validateAnswerOptions(a1, a2, a3, a4);
        if (opts.size() >= 2) {
            this.bot.sendPoll(q, opts);
        }
    }

    private void scheduleShowBackButton() {
        int ms = 5 * 60_000;
        javax.swing.Timer t = new javax.swing.Timer(ms, e -> {
            this.resultPanel.getBottomResultPanel().getBackButton().setVisible(true);
            this.resultPanel.revalidate();
            this.resultPanel.repaint();
            this.resultPanel.getBottomResultPanel().getInstructions().setVisible(false);
        });
        t.setRepeats(false);
        t.start();
    }

    /*
    This method is identifying the number of questions and possible options from the "write your own"
    to be published in the poll.
    It arranges the information received from the poll creation GUI panel, validate it, aware user in case of a problem
    and in case everything is ok - calls the sendPoll method from the TelegramBot class.
    */
    private void publishPoll() {
        System.out.println("Reached the method from the Frame class.");

        // Delay (minutes)
        int userDelay = 0;
        String delayMinutesText = this.bottomPollCreationPanel.getDelayPublication().getText();
        if (delayMinutesText != null && !delayMinutesText.isEmpty() && isAllDigits(delayMinutesText)) {
            userDelay = Integer.parseInt(delayMinutesText.trim());
        }
        final int publicationDelayMinutes = Math.max(0, userDelay);

        // Question 1 (must be valid to proceed)
        String question1 = this.questionPanelTop.getPollQuestion().getText();
        if (!validatePollInput(question1)) {
            return; // stop – Q1 is mandatory
        }

        String q1o1 = this.questionPanelTop.getAnswer1().getText();
        String q1o2 = this.questionPanelTop.getAnswer2().getText();
        String q1o3 = this.questionPanelTop.getAnswer3().getText();
        String q1o4 = this.questionPanelTop.getAnswer4().getText();
        List<String> question1Options = validateAnswerOptions(q1o1, q1o2, q1o3, q1o4);
        if (question1Options == null || question1Options.size() < 2) {
            return; // stop – need at least two options
        }

        // move to results panel once Q1 is confirmed valid
        moveToResultPanel();

        // what to do after delay (or immediately): send Q1, then optional Q2/Q3
        Runnable continueFlow = () -> {
            this.bot.sendPoll(question1, question1Options);
            scheduleShowBackButton();
            trySendFromPanel(this.questionPanelMiddle);
            trySendFromPanel(this.questionPanelBottom);
        };

        if (publicationDelayMinutes > 0) {
            int delayMs = publicationDelayMinutes * 60_000;
            javax.swing.Timer timer = new javax.swing.Timer(delayMs, e -> continueFlow.run());
            timer.setRepeats(false);
            timer.start();
        } else {
            continueFlow.run();
        }
    }
}
    // Getters & Setters................................................................................................
