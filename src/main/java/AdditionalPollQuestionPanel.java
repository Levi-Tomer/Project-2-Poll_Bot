import javax.swing.*;
import java.awt.*;

public class AdditionalPollQuestionPanel extends JPanel {
    private int additionalQuestion = 0;
    private int totalAnswers = 0;

    // Constructor......................................................................................................
    public AdditionalPollQuestionPanel(int y) {
        // Setting up the panel:
        this.setBounds(0, y, Constants.WINDOW_WIDTH, Constants.QUESTION_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating the "Add Question" button:
        JButton addQuestion2Button = new JButton("Add additional question");
        addQuestion2Button.setBounds(50, Constants.QUESTION_PANEL_HEIGHT / 2 - 35, Constants.QUESTION_TEXTBOX_WIDTH, 50);
        addQuestion2Button.setBackground(Color.WHITE);
        addQuestion2Button.setVisible(true);
        this.add(addQuestion2Button);
        // Creating the "Add Question" button.

        // Creating the "Remove Question" button:
        JButton removeQuestion2Button = new JButton("-");
        removeQuestion2Button.setBounds(50 - 40, Constants.QUESTION_PANEL_HEIGHT / 2 - 15, 40, 30);
        removeQuestion2Button.setBackground(Color.WHITE);
        removeQuestion2Button.setVisible(false);
        this.add(removeQuestion2Button);
        // Creating the "Remove Question" button.

        // Adding headline:
        JLabel questionHeadline = new JLabel("Enter your question below:");
        Font questionHeadLineFont = new Font("Arial", Font.PLAIN, 20);
        questionHeadline.setFont(questionHeadLineFont);
        questionHeadline.setBounds(50, Constants.QUESTION_PANEL_HEIGHT / 2 - 35, Constants.WINDOW_WIDTH, 20);
        questionHeadline.setVisible(false);
        this.add(questionHeadline);
        // Adding headline.

        // Adding textbox for the question:
        JTextField question2Text = new JTextField();
        question2Text.setBounds(50, Constants.QUESTION_PANEL_HEIGHT / 2 - 15, Constants.QUESTION_TEXTBOX_WIDTH, 30);
        question2Text.setVisible(false);
        this.add(question2Text);
        String userQuestion2 = question2Text.getText();
        // Adding textbox for the question.

        // Adding textboxes for the possible answers:
        // Answer 1:
        JTextField answer1Text = new JTextField();
        answer1Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        answer1Text.setVisible(false);
        this.add(answer1Text);
        String userAnswer1 = answer1Text.getText();
        // Answer 1.
        // Answer 2:
        JTextField answer2Text = new JTextField();
        answer2Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        answer2Text.setVisible(false);
        this.add(answer2Text);
        String userAnswer2 = answer2Text.getText();
        // Answer 2.
        // Answer 3:
        JTextField answer3Text = new JTextField();
        answer3Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 2, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        answer3Text.setVisible(false);
        this.add(answer3Text);
        String userAnswer3 = answer3Text.getText();
        // Answer 3.
        // Answer 4:
        JTextField answer4Text = new JTextField();
        answer4Text.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 3, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        answer4Text.setVisible(false);
        this.add(answer4Text);
        String userAnswer4 = answer4Text.getText();
        // Answer 4.
        // Adding textboxes for the possible answers.

        // Adding buttons for additional answers:
        // Add answer 3:
        JButton addAnswer3Button = new JButton("Add additional answer");
        addAnswer3Button.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50, 42 + 30 * 2, Constants.ADD_ANSWER_BUTTON_WIDTH, Constants.ADD_ANSWER_BUTTON_HEIGHT);
        addAnswer3Button.setBackground(Color.WHITE);
        addAnswer3Button.setVisible(false);
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
        addAnswer4Button.setVisible(false);
        this.add(addAnswer4Button);
        // Add answer 4.
        // Remove answer 4:
        JButton removeAnswer4Button = new JButton("-");
        removeAnswer4Button.setBounds(Constants.WINDOW_WIDTH - Constants.ANSWER_TEXTBOX_WIDTH - 50 - 40, 42 + 30 * 3, 40, 30);
        removeAnswer4Button.setBackground(Color.WHITE);
        removeAnswer4Button.setVisible(false);
        this.add(removeAnswer4Button);
        // Remove answer 4.
        // Adding buttons for additional answers.

        addQuestion2Button.addActionListener(e -> {
            addQuestion2Button.setVisible(false);
            questionHeadline.setVisible(true);
            question2Text.setVisible(true);
            removeQuestion2Button.setVisible(true);
            answer1Text.setVisible(true);
            answer2Text.setVisible(true);
            addAnswer3Button.setVisible(true);
            addAnswer4Button.setVisible(true);
            this.additionalQuestion++;
            this.totalAnswers += 2;
        });

        removeQuestion2Button.addActionListener(e -> {
            removeQuestion2Button.setVisible(false);
            questionHeadline.setVisible(false);
            question2Text.setVisible(false);
            answer1Text.setVisible(false);
            answer2Text.setVisible(false);
            answer3Text.setVisible(false);
            answer4Text.setVisible(false);
            addAnswer3Button.setVisible(false);
            removeAnswer3Button.setVisible(false);
            addAnswer4Button.setVisible(false);
            removeAnswer4Button.setVisible(false);
            addQuestion2Button.setVisible(true);
            this.additionalQuestion--;
            this.totalAnswers -= 2;
        });

        addAnswer3Button.addActionListener(e -> {
            addAnswer3Button.setVisible(false);
            removeAnswer3Button.setVisible(true);
            answer3Text.setVisible(true);
            this.totalAnswers++;
        });

        removeAnswer3Button.addActionListener(e -> {
            removeAnswer3Button.setVisible(false);
            answer3Text.setVisible(false);
            addAnswer3Button.setVisible(true);
            this.totalAnswers--;
        });

        addAnswer4Button.addActionListener(e -> {
            addAnswer4Button.setVisible(false);
            removeAnswer4Button.setVisible(true);
            answer4Text.setVisible(true);
            this.totalAnswers++;
        });

        removeAnswer4Button.addActionListener(e -> {
            removeAnswer4Button.setVisible(false);
            answer4Text.setVisible(false);
            addAnswer4Button.setVisible(true);
            this.totalAnswers--;
        });
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
}
