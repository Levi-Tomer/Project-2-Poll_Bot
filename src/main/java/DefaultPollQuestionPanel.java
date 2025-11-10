import javax.swing.*;
import java.awt.*;

public class DefaultPollQuestionPanel extends JPanel {
    private JTextField pollQuestion;
    private JTextField answer1;
    private JTextField answer2;
    private JTextField answer3;
    private JTextField answer4;
    private boolean possibleAnswer3 = false;
    private boolean possibleAnswer4 = false;

    // Constructor......................................................................................................
    public DefaultPollQuestionPanel() {
        // Setting up the panel:
        this.setBounds(0, 0, Utils.WINDOW_WIDTH, Utils.QUESTION_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating and adding question textbox instructions:
        JLabel questionHeadline = new JLabel("Enter your question below:");
        Font questionHeadLineFont = new Font("Arial", Font.PLAIN, 20);
        questionHeadline.setFont(questionHeadLineFont);
        questionHeadline.setBounds(50, Utils.QUESTION_TEXTBOX_LABEL, Utils.WINDOW_WIDTH, 20);
        questionHeadline.setVisible(true);
        this.add(questionHeadline);
        // Creating and adding question textbox instructions.

        // Creating and adding textbox for the question:
        this.pollQuestion = new JTextField();
        this.pollQuestion.setBounds(50, Utils.QUESTION_TEXTBOX_Y_LOCATION, Utils.QUESTION_TEXTBOX_WIDTH, 30);
        this.add(this.pollQuestion);
        // Creating and adding textbox for the question.

        // Creating and adding textboxes for the possible answers:
        // Answer 1:
        this.answer1 = new JTextField();
        this.answer1.setBounds(Utils.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42, Utils.ANSWER_TEXTBOX_WIDTH, 30);
        this.add(this.answer1);
        // Answer 1.
        // Answer 2:
        this.answer2 = new JTextField();
        this.answer2.setBounds(Utils.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42 + 30, Utils.ANSWER_TEXTBOX_WIDTH, 30);
        this.add(this.answer2);
        // Answer 2.
        // Answer 3:
        this.answer3 = new JTextField();
        this.answer3.setBounds(Utils.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42 + 30 * 2, Utils.ANSWER_TEXTBOX_WIDTH, 30);
        this.answer3.setVisible(false);
        this.add(this.answer3);
        // Answer 3.
        // Answer 4:
        this.answer4 = new JTextField();
        this.answer4 .setBounds(Utils.ANSWER_OPTION_TEXTBOX_X_LOCATION, 42 + 30 * 3, Utils.ANSWER_TEXTBOX_WIDTH, 30);
        this.answer4 .setVisible(false);
        this.add(this.answer4 );
        // Answer 4.
        // Creating and adding textboxes for the possible answers.

        // Creating and adding buttons for additional answers:
        // Add answer 3:
        JButton addAnswer3Button = new JButton("Add additional answer");
        addAnswer3Button.setBounds(Utils.ADDITIONAL_ANSWER_OPTION_BUTTON, 42 + 30 * 2, Utils.ADD_ANSWER_BUTTON_WIDTH, Utils.ADD_ANSWER_BUTTON_HEIGHT);
        addAnswer3Button.setBackground(Color.WHITE);
        addAnswer3Button.setVisible(true);
        this.add(addAnswer3Button);
        // Add answer 3.
        // Remove answer 3:
        JButton removeAnswer3Button = new JButton("-");
        removeAnswer3Button.setBounds(Utils.WINDOW_WIDTH - Utils.ANSWER_TEXTBOX_WIDTH - 50 - 40, 42 + 30 * 2, 40, 30);
        removeAnswer3Button.setBackground(Color.WHITE);
        removeAnswer3Button.setVisible(false);
        this.add(removeAnswer3Button);
        // Remove answer 3.
        // Add answer 4:
        JButton addAnswer4Button = new JButton("Add additional answer");
        addAnswer4Button.setBounds(Utils.ADDITIONAL_ANSWER_OPTION_BUTTON, 42 + 30 * 3, Utils.ADD_ANSWER_BUTTON_WIDTH, Utils.ADD_ANSWER_BUTTON_HEIGHT);
        addAnswer4Button.setBackground(Color.WHITE);
        addAnswer4Button.setVisible(true);
        this.add(addAnswer4Button);
        // Add answer 4.
        // Remove answer 4:
        JButton removeAnswer4Button = new JButton("-");
        removeAnswer4Button.setBounds(Utils.WINDOW_WIDTH - Utils.ANSWER_TEXTBOX_WIDTH - 50 - 40, 42 + 30 * 3, 40, 30);
        removeAnswer4Button.setBackground(Color.WHITE);
        removeAnswer4Button.setVisible(false);
        this.add(removeAnswer4Button);
        // Remove answer 4.
        // Creating and adding buttons for additional answers.

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

    public void setPollQuestion(JTextField pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public void setAnswer1(JTextField answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(JTextField answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(JTextField answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(JTextField answer4) {
        this.answer4 = answer4;
    }

    public void setPossibleAnswer3(boolean possibleAnswer3) {
        this.possibleAnswer3 = possibleAnswer3;
    }

    public void setPossibleAnswer4(boolean possibleAnswer4) {
        this.possibleAnswer4 = possibleAnswer4;
    }
}
