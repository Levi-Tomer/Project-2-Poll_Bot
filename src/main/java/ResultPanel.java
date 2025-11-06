import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultPanel extends JPanel {
    private QuestionResultPanel top;
    private QuestionResultPanel middle;
    private QuestionResultPanel bottom;
    private BottomResultPanel bottomResultPanel;

    private final Map<String, QuestionResultPanel> pollIdToPanel = new HashMap<>();

    // Constructor......................................................................................................
    public ResultPanel() {
        // Setting up the panel:
        this.setBounds(0, 0, Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        // Creating and adding the panels:
        this.top = new QuestionResultPanel(0, "1");
        this.middle = new QuestionResultPanel(Utils.QUESTION_PANEL_HEIGHT, "2");
        this.bottom = new QuestionResultPanel(Utils.QUESTION_PANEL_HEIGHT * 2, "3");
        this.top.setVisible(true);
        this.middle.setVisible(true);
        this.bottom.setVisible(true);
        this.add(this.top);
        this.add(this.middle);
        this.add(this.bottom);
        // Creating and adding the panels.

        // Creating and adding the bottom panel:
        this.bottomResultPanel = new BottomResultPanel();
        this.add(bottomResultPanel);
        // Creating and adding the bottom panel.
    }

    // Methods..........................................................................................................
    public void registerPoll(String pollId, int slotIndex) {
        QuestionResultPanel target = switch (slotIndex) {
            case 1 -> top;
            case 2 -> middle;
            case 3 -> bottom;
            default -> throw new IllegalArgumentException("slotIndex must be 1..3");
        };
        pollIdToPanel.put(pollId, target);
    }

    public void setQuestionText(String pollId, String questionText) {
        QuestionResultPanel target = pollIdToPanel.get(pollId);
        if (target == null) return;
        target.setQuestionText(questionText);
    }

    public void updateResultsFor(String pollId, List<String> optionTexts, List<Integer> counts, int totalVoters) {
        QuestionResultPanel target = pollIdToPanel.get(pollId);
        if (target == null) return;
        target.setResults(optionTexts, counts, totalVoters);
        target.revalidate();
        target.repaint();
        revalidate();
        repaint();
    }

    // Getters & Setters................................................................................................
    public QuestionResultPanel getTop() {
        return top;
    }

    public QuestionResultPanel getMiddle() {
        return middle;
    }

    public QuestionResultPanel getBottom() {
        return bottom;
    }

    public BottomResultPanel getBottomResultPanel() {
        return bottomResultPanel;
    }
}