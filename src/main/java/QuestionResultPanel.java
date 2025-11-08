import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestionResultPanel extends JPanel {
    private JLabel questionHeadline;
    private JLabel questionValue;
    private JLabel[] optionHeadlines = new JLabel[4];
    private JLabel[] optionValues = new JLabel[4];

    // Constructor......................................................................................................
    public QuestionResultPanel(int y, String questionNumber) {
        // Setting up the panel:
        this.setBounds(0, y, Utils.WINDOW_WIDTH, Utils.QUESTION_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Question headline (static label "Question X:")
        questionHeadline = new JLabel("Question " + questionNumber + ":");
        questionHeadline.setFont(new Font("Arial", Font.PLAIN, 20));
        questionHeadline.setBounds(10, 10, 200, 20);
        this.add(questionHeadline);

        // Question value (actual question text – updated later)
        questionValue = new JLabel("");
        questionValue.setFont(new Font("Arial", Font.PLAIN, 18));
        questionValue.setBounds(220, 10, Utils.WINDOW_WIDTH - 240, 20);
        this.add(questionValue);

        // Options
        Font optionsHeadLineFont = new Font("Arial", Font.PLAIN, 15);
        for (int i = 0; i < 4; i++) {
            JLabel optionHeadline = new JLabel("Option " + (i + 1) + ":");
            optionHeadline.setFont(optionsHeadLineFont);
            optionHeadline.setBounds(10, 35 + i * 20, 100, 15);
            this.add(optionHeadline);
            optionHeadlines[i] = optionHeadline;

            JLabel value = new JLabel("—"); // will be "text — N voters (P%)"
            value.setFont(new Font("Arial", Font.PLAIN, 15));
            value.setBounds(120, 35 + i * 20, Utils.WINDOW_WIDTH - 140, 15);
            this.add(value);
            optionValues[i] = value;
        }
    }

    // Methods..........................................................................................................
    public void setQuestionText(String text) {
        this.questionValue.setText(text != null ? text : "");
    }

    public void setResults(List<String> optionTexts, List<Integer> counts, int totalVoters) {
        for (int i = 0; i < 4; i++) {
            String text = (optionTexts != null && optionTexts.size() > i) ? optionTexts.get(i) : "—";
            int count   = (counts != null && counts.size() > i) ? counts.get(i) : 0;
            double pct  = (totalVoters > 0) ? (count * 100.0 / totalVoters) : 0.0;
            optionValues[i].setText(String.format("%s — %d מצביעים (%.1f%%)", text, count, pct));
        }
    }

    public void clear() {
        setQuestionText("");
        for (int i = 0; i < 4; i++) {
            optionValues[i].setText("—");
        }
    }

    // Getters & Setters................................................................................................
    public JLabel getQuestionHeadline() {
        return questionHeadline;
    }

    public JLabel getQuestionValue() {
        return questionValue;
    }

    public JLabel[] getOptionHeadlines() {
        return optionHeadlines;
    }

    public JLabel[] getOptionValues() {
        return optionValues;
    }
}