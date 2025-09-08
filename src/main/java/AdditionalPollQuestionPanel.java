import javax.swing.*;
import java.awt.*;

public class AdditionalPollQuestionPanel extends JPanel {
    private boolean additionalQuestion = false;
    private JTextField pollQuestion;
    private JTextField answer1;
    private JTextField answer2;
    private JTextField answer3;
    private JTextField answer4;
    private boolean possibleAnswer3 = false;
    private boolean possibleAnswer4 = false;
    // Constructor......................................................................................................
    public AdditionalPollQuestionPanel(int y) {
        // Setting up the panel:
        this.setBounds(0, y, Constants.WINDOW_WIDTH, Constants.QUESTION_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating and adding the "Add Question" button:
        JButton addQuestion2Button = new JButton("Add additional question");
        addQuestion2Button.setBounds(50, Constants.QUESTION_PANEL_HEIGHT / 2 - 35, Constants.QUESTION_TEXTBOX_WIDTH, 50);
        addQuestion2Button.setBackground(Color.WHITE);
        addQuestion2Button.setVisible(true);
        this.add(addQuestion2Button);
        // Creating and adding the "Add Question" button.

        // Creating and adding the "Remove Question" button:
        JButton removeQuestion2Button = new JButton("-");
        removeQuestion2Button.setBounds(50 - 40, Constants.QUESTION_PANEL_HEIGHT / 2 - 15, 40, 30);
        removeQuestion2Button.setBackground(Color.WHITE);
        removeQuestion2Button.setVisible(false);
        this.add(removeQuestion2Button);
        // Creating and adding the "Remove Question" button.

        // Creating and adding question textbox instructions:
        JLabel questionHeadline = new JLabel("Enter your question below:");
        Font questionHeadLineFont = new Font("Arial", Font.PLAIN, 20);
        questionHeadline.setFont(questionHeadLineFont);
        questionHeadline.setBounds(50, Constants.QUESTION_TEXTBOX_LABEL, Constants.WINDOW_WIDTH, 20);
        questionHeadline.setVisible(false);
        this.add(questionHeadline);
        // Creating and adding question textbox instructions.

        // Creating and adding textbox for the question:
        this.pollQuestion = new JTextField();
        this.pollQuestion.setBounds(50, Constants.QUESTION_TEXTBOX_Y_LOCATION, Constants.QUESTION_TEXTBOX_WIDTH, 30);
        this.pollQuestion.setVisible(false);
        this.add(this.pollQuestion);
        // Creating and adding textbox for the question.

        // creating and adding textboxes for the possible answers:
        // Answer 1:
        this.answer1 = new JTextField();
        this.answer1.setBounds(Constants.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        this.answer1.setVisible(false);
        this.add(this.answer1);
        // Answer 1.
        // Answer 2:
        this.answer2 = new JTextField();
        this.answer2.setBounds(Constants.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42 + 30, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        this.answer2.setVisible(false);
        this.add(this.answer2);
        // Answer 2.
        // Answer 3:
        this.answer3 = new JTextField();
        this.answer3.setBounds(Constants.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42 + 30 * 2, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        this.answer3.setVisible(false);
        this.add(this.answer3);
        // Answer 3.
        // Answer 4:
        this.answer4 = new JTextField();
        this.answer4.setBounds(Constants.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42 + 30 * 3, Constants.ANSWER_TEXTBOX_WIDTH, 30);
        this.answer4.setVisible(false);
        this.add(this.answer4);
        // Answer 4.
        // Creating and adding textboxes for the possible answers.

        // Creating and adding buttons for additional answers:
        // Add answer 3:
        JButton addAnswer3Button = new JButton("Add additional answer");
        addAnswer3Button.setBounds(Constants.ADDITIONAL_ANSWER_OPTION_BUTTON, 42 + 30 * 2, Constants.ADD_ANSWER_BUTTON_WIDTH, Constants.ADD_ANSWER_BUTTON_HEIGHT);
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
        addAnswer4Button.setBounds(Constants.ADDITIONAL_ANSWER_OPTION_BUTTON, 42 + 30 * 3, Constants.ADD_ANSWER_BUTTON_WIDTH, Constants.ADD_ANSWER_BUTTON_HEIGHT);
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
        // Creating and adding buttons for additional answers.

        addQuestion2Button.addActionListener(e -> {
            addQuestion2Button.setVisible(false);
            questionHeadline.setVisible(true);
            this.pollQuestion.setVisible(true);
            removeQuestion2Button.setVisible(true);
            this.answer1.setVisible(true);
            this.answer2.setVisible(true);
            addAnswer3Button.setVisible(true);
            addAnswer4Button.setVisible(true);
            this.additionalQuestion = true;
        });

        removeQuestion2Button.addActionListener(e -> {
            removeQuestion2Button.setVisible(false);
            questionHeadline.setVisible(false);
            this.pollQuestion.setVisible(false);
            this.answer1.setVisible(false);
            this.answer2.setVisible(false);
            this.answer3.setVisible(false);
            this.answer4.setVisible(false);
            addAnswer3Button.setVisible(false);
            removeAnswer3Button.setVisible(false);
            addAnswer4Button.setVisible(false);
            removeAnswer4Button.setVisible(false);
            addQuestion2Button.setVisible(true);
            this.additionalQuestion = false;
        });

        addAnswer3Button.addActionListener(e -> {
            addAnswer3Button.setVisible(false);
            removeAnswer3Button.setVisible(true);
            this.answer3.setVisible(true);
            this.possibleAnswer3 = true;
        });

        removeAnswer3Button.addActionListener(e -> {
            removeAnswer3Button.setVisible(false);
            this.answer3.setVisible(false);
            addAnswer3Button.setVisible(true);
            this.possibleAnswer3 = false;
        });

        addAnswer4Button.addActionListener(e -> {
            addAnswer4Button.setVisible(false);
            removeAnswer4Button.setVisible(true);
            this.answer4.setVisible(true);
            this.possibleAnswer4 = true;
        });

        removeAnswer4Button.addActionListener(e -> {
            removeAnswer4Button.setVisible(false);
            this.answer4.setVisible(false);
            addAnswer4Button.setVisible(true);
            this.possibleAnswer4 = false;
        });
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
    public boolean isAdditionalQuestion() {
        return additionalQuestion;
    }

    public JTextField getPollQuestion() {
        return pollQuestion;
    }

    public JTextField getAnswer1() {
        return answer1;
    }

    public JTextField getAnswer2() {
        return answer2;
    }

    public JTextField getAnswer3() {
        return answer3;
    }

    public JTextField getAnswer4() {
        return answer4;
    }

    public boolean isPossibleAnswer3() {
        return possibleAnswer3;
    }

    public boolean isPossibleAnswer4() {
        return possibleAnswer4;
    }
}
