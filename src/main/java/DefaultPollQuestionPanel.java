import javax.swing.*;
import java.awt.*;

public class DefaultPollQuestionPanel extends JPanel {
    private String pollQuestion;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private boolean possibleAnswer3 = false;
    private boolean possibleAnswer4 = false;

    // Constructor......................................................................................................
    public DefaultPollQuestionPanel() {
        // Setting up the panel:
        this.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.QUESTION_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating and adding headline:
        JLabel questionHeadline = new JLabel("Enter your question below:");
        Font questionHeadLineFont = new Font("Arial", Font.PLAIN, 20);
        questionHeadline.setFont(questionHeadLineFont);
        questionHeadline.setBounds(50, Constants.QUESTION_PANEL_HEIGHT / 2 - 35, Constants.WINDOW_WIDTH, 20);
        questionHeadline.setVisible(true);
        this.add(questionHeadline);
        // Creating and adding headline.

        // Creating and adding textbox for the question:
        JTextField question = new JTextField();
        question.setBounds(50, Constants.QUESTION_PANEL_HEIGHT / 2 - 15, Constants.QUESTION_TEXTBOX_WIDTH, 30);
        this.add(question);
        this.pollQuestion = question.getText();
        // Creating and adding textbox for the question.

        // Creating and adding textboxes for the possible answers:
        // Answer 1:
        JTextField answer1Text = new JTextField();
        answer1Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        this.add(answer1Text);
        this.answer1 = answer1Text.getText();
        // Answer 1.
        // Answer 2:
        JTextField answer2Text = new JTextField();
        answer2Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        this.add(answer2Text);
        this.answer2 = answer2Text.getText();
        // Answer 2.
        // Answer 3:
        JTextField answer3Text = new JTextField();
        answer3Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 2, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        answer3Text.setVisible(false);
        this.add(answer3Text);
        this.answer3 = answer3Text.getText();
        // Answer 3.
        // Answer 4:
        JTextField answer4Text = new JTextField();
        answer4Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 3, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        answer4Text.setVisible(false);
        this.add(answer4Text);
        this.answer4 = answer4Text.getText();
        // Answer 4.
        // Creating and adding textboxes for the possible answers.

        // Creating and adding buttons for additional answers:
        // Add answer 3:
        JButton addAnswer3Button = new JButton("Add additional answer");
        addAnswer3Button.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 2, Constants.ADD_ANSWER_BUTTON_WIDTH, Constants.ADD_ANSWER_BUTTON_HEIGHT);
        addAnswer3Button.setBackground(Color.WHITE);
        addAnswer3Button.setVisible(true);
        this.add(addAnswer3Button);
        // Add answer 3.
        // Remove answer 3:
        JButton removeAnswer3Button = new JButton("-");
        removeAnswer3Button.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50 - 40, 42 + 30 * 2, 40, 30);
        removeAnswer3Button.setBackground(Color.WHITE);
        removeAnswer3Button.setVisible(false);
        this.add(removeAnswer3Button);
        // Remove answer 3.
        // Add answer 4:
        JButton addAnswer4Button = new JButton("Add additional answer");
        addAnswer4Button.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 3, Constants.ADD_ANSWER_BUTTON_WIDTH, Constants.ADD_ANSWER_BUTTON_HEIGHT);
        addAnswer4Button.setBackground(Color.WHITE);
        addAnswer4Button.setVisible(true);
        this.add(addAnswer4Button);
        // Add answer 4.
        // Remove answer 4:
        JButton removeAnswer4Button = new JButton("-");
        removeAnswer4Button.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50 - 40, 42 + 30 * 3, 40, 30);
        removeAnswer4Button.setBackground(Color.WHITE);
        removeAnswer4Button.setVisible(false);
        this.add(removeAnswer4Button);
        // Remove answer 4.
        // Creating and adding buttons for additional answers.

        addAnswer3Button.addActionListener(e -> {
            addAnswer3Button.setVisible(false);
            removeAnswer3Button.setVisible(true);
            answer3Text.setVisible(true);
            this.possibleAnswer3 = true;
        });

        removeAnswer3Button.addActionListener(e -> {
            removeAnswer3Button.setVisible(false);
            answer3Text.setVisible(false);
            addAnswer3Button.setVisible(true);
            this.possibleAnswer3 = false;
        });

        addAnswer4Button.addActionListener(e -> {
            addAnswer4Button.setVisible(false);
            removeAnswer4Button.setVisible(true);
            answer4Text.setVisible(true);
            this.possibleAnswer4 = true;
        });

        removeAnswer4Button.addActionListener(e -> {
            removeAnswer4Button.setVisible(false);
            answer4Text.setVisible(false);
            addAnswer4Button.setVisible(true);
            this.possibleAnswer4 = false;
        });
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
    public String getPollQuestion() {
        return pollQuestion;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public boolean isPossibleAnswer3() {
        return possibleAnswer3;
    }

    public boolean isPossibleAnswer4() {
        return possibleAnswer4;
    }
}
