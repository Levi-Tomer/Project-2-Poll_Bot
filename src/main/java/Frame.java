import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

public class Frame extends JFrame {
    private MenuPanel menuPanel;
    private DefaultPollQuestionPanel questionPanelTop;
    private AdditionalPollQuestionPanel questionPanelMiddle;
    private AdditionalPollQuestionPanel questionPanelBottom;
    private BottomWritePollPanel bottomPollCreationPanel;
    private ChatGPTPanel chatGPTPanel;
    private ResultPanel resultPanel;
    private GPTResponseConversion gptResponseConversion;

    private final Map<String, Integer> questionToSlot = new HashMap<>();

    private final Set<String> shownQuestions = new HashSet<>();

    private Timer resultsWatcher;
    private final TelegramBot bot;
    private ChatGPTApi chatGPTApi;

    // Constructor......................................................................................................
    public Frame(TelegramBot bot) {
        super("Polls For Telegram Bot");
        this.bot = bot;
        this.chatGPTApi = new ChatGPTApi();

        // Setting up the window:
        this.setSize(Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);

        // Main menu:
        this.menuPanel = new MenuPanel();
        this.menuPanel.setVisible(true);
        this.add(menuPanel);

        // Write-poll UI:
        this.questionPanelTop = new DefaultPollQuestionPanel();
        this.chatGPTPanel = new ChatGPTPanel();
        this.questionPanelMiddle = new AdditionalPollQuestionPanel(Utils.QUESTION_PANEL_HEIGHT);
        this.questionPanelBottom = new AdditionalPollQuestionPanel(Utils.QUESTION_PANEL_HEIGHT * 2);
        this.bottomPollCreationPanel = new BottomWritePollPanel();
        this.resultPanel = new ResultPanel();

        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.resultPanel.setVisible(false);
        this.chatGPTPanel.setVisible(false);

        this.add(questionPanelTop);
        this.add(questionPanelMiddle);
        this.add(questionPanelBottom);
        this.add(bottomPollCreationPanel);
        this.add(resultPanel);
        this.add(chatGPTPanel);

        // Listeners:
        this.menuPanel.getWritePollButton().addActionListener(e -> moveToWritePollInterface());
        this.menuPanel.getGptPollButton().addActionListener(e -> moveToGptPollInterface());
        this.menuPanel.getExitButton().addActionListener(e -> System.exit(0));

        this.bottomPollCreationPanel.getBackButton().addActionListener(e -> moveBackToMainMenuFromWritePollInterface());
        this.bottomPollCreationPanel.getPublishButton().addActionListener(e -> publishPoll());

        this.chatGPTPanel.getBottomWritePollPanel().getBackButton().addActionListener(e -> moveBackToMainMenuFromGPTPollInterface());
        this.chatGPTPanel.getBottomWritePollPanel().getPublishButton().addActionListener(e -> {
            handleChatGPTPollPublish();
        });

        this.resultPanel.getBottomResultPanel().getBackButton().addActionListener(e -> moveBackToMainMenuFromResultPanel());
    }

    // Methods..........................................................................................................
    private void moveToWritePollInterface() {
        // כאן בתרגיל האמיתי צריך לבדוק מינימום 3 נרשמים
        // if (this.bot.getSubscribers().size() < 3) { ... }
        this.menuPanel.setVisible(false);
        this.questionPanelTop.setVisible(true);
        this.questionPanelMiddle.setVisible(true);
        this.questionPanelBottom.setVisible(true);
        this.bottomPollCreationPanel.setVisible(true);
    }

    private void moveToGptPollInterface() {
        if(false){
//        if (this.bot.getSubscribers().size() < 3) {
            JOptionPane.showMessageDialog(this,
                    "You need to have at least 3 subscribers to create a poll.\nCurrent subscribers: " + this.bot.getSubscribers().size(),
                    "Error: not enough subscribers", JOptionPane.PLAIN_MESSAGE);
        } else {
            this.menuPanel.setVisible(false);
            this.chatGPTPanel.setVisible(true);
            // TODO: Add ChatGPT poll creation interface
        }
    }

    private void moveToResultPanel() {
        this.resultPanel.clearAll();
        this.resultPanel.setVisible(true);
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        startResultsWatcher();
    }

    private void moveBackToMainMenuFromWritePollInterface() {
        this.questionPanelTop.setVisible(false);
        this.questionPanelMiddle.setVisible(false);
        this.questionPanelBottom.setVisible(false);
        this.bottomPollCreationPanel.setVisible(false);
        this.menuPanel.setVisible(true);
    }

    private void moveBackToMainMenuFromGPTPollInterface() {
        this.chatGPTPanel.setVisible(false);
        this.menuPanel.setVisible(true);
    }

    private void moveBackToMainMenuFromResultPanel() {
        this.menuPanel.setVisible(true);
        this.resultPanel.setVisible(false);
        stopResultsWatcher();
        this.resultPanel.clearAll();
    }

    private boolean validatePollInput(String text) {
        return text != null && !text.isEmpty();
    }

    private List<String> validateAnswerOptions(String o1, String o2, String o3, String o4) {
        ArrayList<String> options = new ArrayList<>(4);
        if (validatePollInput(o1)) options.add(o1);
        if (validatePollInput(o2)) options.add(o2);
        if (validatePollInput(o3)) options.add(o3);
        if (validatePollInput(o4)) options.add(o4);
        return options;
    }

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

    private void showResultBackButtonWhenAnswered() {
        this.resultPanel.getBottomResultPanel().getBackButton().setVisible(true);
        this.resultPanel.revalidate();
        this.resultPanel.repaint();
        this.resultPanel.getBottomResultPanel().getInstructions().setVisible(false);
    }

    public void updatePollResults(TelegramBot.PollResult result) {
        Integer slot = questionToSlot.get(result.getQuestion());
        if (slot == null) {
            System.out.println("No slot mapping for question: " + result.getQuestion());
            return;
        }

        SwingUtilities.invokeLater(() -> {
            String key = result.getQuestion(); // מזהה לוגי לפי שאלה
            resultPanel.registerPoll(key, slot);
            resultPanel.setQuestionText(key, result.getQuestion());
            resultPanel.updateResultsFor(
                    key,
                    result.getOptionTexts(),
                    result.getCounts(),
                    result.getTotalVoters()
            );
        });
    }

    private void startResultsWatcher() {
        stopResultsWatcher();
        resultsWatcher = new Timer(2000, e -> pullOnce());
        resultsWatcher.setRepeats(true);
        resultsWatcher.start();
    }

    private void stopResultsWatcher() {
        if (resultsWatcher != null) {
            resultsWatcher.stop();
            resultsWatcher = null;
        }
    }

    private void pullOnce() {
        Map<String, TelegramBot.PollResult> all = bot.getAllPolls();
        if (all == null || all.isEmpty()) return;

        Map<String, AggregatedResult> byQuestion = new HashMap<>();

        for (TelegramBot.PollResult pr : all.values()) {
            if (!pr.isClosed()) {
                continue;
            }

            String question = pr.getQuestion();
            if (question == null) continue;

            List<String> optionTexts = pr.getOptionTexts();
            List<Integer> counts = pr.getCounts();
            int n = Math.min(optionTexts.size(), counts.size());
            if (n == 0) continue;

            AggregatedResult agg = byQuestion.computeIfAbsent(question,
                    q -> new AggregatedResult(optionTexts, n));

            for (int i = 0; i < n; i++) {
                agg.counts[i] += counts.get(i);
            }
            agg.totalVoters += pr.getTotalVoters();
        }

        if (byQuestion.isEmpty()) return;

        for (Map.Entry<String, AggregatedResult> entry : byQuestion.entrySet()) {
            String question = entry.getKey();
            AggregatedResult agg = entry.getValue();

            if (shownQuestions.contains(question)) {
                continue;
            }

            Integer slot = questionToSlot.get(question);
            if (slot == null) {
                continue;
            }

            String key = question;

            SwingUtilities.invokeLater(() -> {
                resultPanel.registerPoll(key, slot);
                resultPanel.setQuestionText(key, question);
                resultPanel.updateResultsFor(
                        key,
                        agg.optionTexts,
                        agg.toList(),
                        agg.totalVoters
                );
            });

            shownQuestions.add(question);
            if (!questionToSlot.isEmpty() && shownQuestions.size() == questionToSlot.size()) {
                SwingUtilities.invokeLater(this::showResultBackButtonWhenAnswered);
            }
        }
    }

    private static class AggregatedResult {
        final List<String> optionTexts;
        final int[] counts;
        int totalVoters;

        AggregatedResult(List<String> optionTexts, int size) {
            this.optionTexts = new ArrayList<>(optionTexts);
            this.counts = new int[size];
            this.totalVoters = 0;
        }

        List<Integer> toList() {
            List<Integer> list = new ArrayList<>(counts.length);
            for (int c : counts) list.add(c);
            return list;
        }
    }

    private void receiveSubject (){
        String subject = this.chatGPTPanel.getSubject().getText();

    }

    private void publishPoll() {
        System.out.println("Reached the method from the Frame class.");

        // Delay (minutes)
        int userDelay = 0;
        String delayMinutesText = this.bottomPollCreationPanel.getDelayPublication().getText();
        if (delayMinutesText != null && !delayMinutesText.isEmpty() && isAllDigits(delayMinutesText)) {
            userDelay = Integer.parseInt(delayMinutesText);
        }
        final int publicationDelayMinutes = Math.max(0, userDelay);

        // Question 1 (mandatory)
        String question1 = this.questionPanelTop.getPollQuestion().getText();
        if (!validatePollInput(question1)) {
            return;
        }

        String q1o1 = this.questionPanelTop.getAnswer1().getText();
        String q1o2 = this.questionPanelTop.getAnswer2().getText();
        String q1o3 = this.questionPanelTop.getAnswer3().getText();
        String q1o4 = this.questionPanelTop.getAnswer4().getText();
        List<String> question1Options = validateAnswerOptions(q1o1, q1o2, q1o3, q1o4);
        if (question1Options.size() < 2) {
            return;
        }

        questionToSlot.clear();
        shownQuestions.clear();

        questionToSlot.put(question1, 1);

        if (this.questionPanelMiddle.isAdditionalQuestion()) {
            String q2 = this.questionPanelMiddle.getPollQuestion().getText();
            if (validatePollInput(q2)) {
                questionToSlot.put(q2, 2);
            }
        }

        if (this.questionPanelBottom.isAdditionalQuestion()) {
            String q3 = this.questionPanelBottom.getPollQuestion().getText();
            if (validatePollInput(q3)) {
                questionToSlot.put(q3, 3);
            }
        }

        moveToResultPanel();

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

    private void publishPollFromChatGPT(GPTResponseConversion pollSet) {
        System.out.println("Reached the publishPollFromChatGPT() method.");

        // בדיקה בסיסית שהתקבלו שאלות
        if (pollSet == null) {
            System.err.println("pollSet is null — cannot send polls.");
            return;
        }

        // ניקוי מצבים קודמים
        questionToSlot.clear();
        shownQuestions.clear();

        // מיפוי של כל שאלה למיקום שלה בתצוגת התוצאות
        if (pollSet.getQuestion1() != null) {
            questionToSlot.put(pollSet.getQuestion1(), 1);
        }
        if (pollSet.getQuestion2() != null) {
            questionToSlot.put(pollSet.getQuestion2(), 2);
        }
        if (pollSet.getQuestion3() != null) {
            questionToSlot.put(pollSet.getQuestion3(), 3);
        }

        // מעבר למסך התוצאות
        moveToResultPanel();

        // === שליחה בפועל של הסקרים ===
        Runnable continueFlow = () -> {
            if (pollSet.getQuestion1() != null && !pollSet.getOptions1().isEmpty()) {
                bot.sendPoll(pollSet.getQuestion1(), pollSet.getOptions1());
            }

            if (pollSet.getQuestion2() != null && !pollSet.getOptions2().isEmpty()) {
                bot.sendPoll(pollSet.getQuestion2(), pollSet.getOptions2());
            }

            if (pollSet.getQuestion3() != null && !pollSet.getOptions3().isEmpty()) {
                bot.sendPoll(pollSet.getQuestion3(), pollSet.getOptions3());
            }

            scheduleShowBackButton(); // להציג כפתור חזרה אחרי פרק זמן
        };

        // כרגע אין עיכוב כמו בפרסום ידני, אבל אפשר להחזיר את זה אם תרצי
        continueFlow.run();
    }

    private void handleChatGPTPollPublish() {
        System.out.println("User clicked Publish poll (ChatGPT mode)");

        // 1️⃣ קבלת הנושא מהפאנל
        String topic = this.chatGPTPanel.getSubject().getText().trim();
        if (topic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a topic first.", "Missing topic", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2️⃣ הצגת הודעת טעינה
        JOptionPane.showMessageDialog(this, "Generating poll from ChatGPT...\nThis may take a few seconds.", "Please wait", JOptionPane.INFORMATION_MESSAGE);

        // 3️⃣ קריאה ל-API
        ChatGPTApi api = new ChatGPTApi();
        boolean success = api.requestPollFromTopic(topic);

        if (!success) {
            JOptionPane.showMessageDialog(this, "Failed to generate poll from ChatGPT.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4️⃣ קבלת התגובה שהגיעה מה-API (ב-JSON)
        String jsonResponse = api.getJsonResponse(); // נוסיף getter לזה (הסבר בשלב הבא)

        // 5️⃣ המרה של ה-JSON לשאלות ואופציות
        GPTResponseConversion pollSet = new GPTResponseConversion(jsonResponse);

        //.......................................................................................................
        // Delay (minutes)

        // Question 1 (mandatory)
        String q1 = pollSet.getQuestion1();
        if (!validatePollInput(q1)) {
            return;
        }

        List<String> q1o = pollSet.getOptions1();

        questionToSlot.clear();
        shownQuestions.clear();

        questionToSlot.put(q1, 1);

        String q2 = pollSet.getQuestion2();
        List<String> q2o = pollSet.getOptions2();

        String q3 = pollSet.getQuestion3();
        List<String> q3o = pollSet.getOptions3();

        moveToResultPanel();

        this.bot.sendPoll(q1, q1o);
        this.bot.sendPoll(q2, q2o);
        this.bot.sendPoll(q3, q3o);

        scheduleShowBackButton();

        // 6️⃣ פרסום הסקרים לכל המנויים
        publishPollFromChatGPT(pollSet);
    }






}