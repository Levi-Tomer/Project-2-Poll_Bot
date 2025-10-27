import javax.swing.*;
import java.awt.*;

public class QuestionResultPanel extends JPanel {
    private JLabel question;
    private JLabel option1;
    private JLabel option2;
    private JLabel option3;
    private JLabel option4;

    // Constructor......................................................................................................
    public QuestionResultPanel(int y, String questionNumber) {
        // Setting up the panel:
        this.setBounds(0, y, Utils.WINDOW_WIDTH, Utils.QUESTION_PANEL_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

        // Creating and adding the question text:
        JLabel questionHeadline = new JLabel("Question" + " " + questionNumber);
        Font questionHeadLineFont = new Font("Arial", Font.PLAIN, 20);
        questionHeadline.setFont(questionHeadLineFont);
        questionHeadline.setBounds(10, 10, Utils.WINDOW_WIDTH, 20);
        questionHeadline.setVisible(true);
        this.add(questionHeadline);
        // Creating and adding the question text.

        // Creating and adding the options text:
        Font optionsHeadLineFont = new Font("Arial", Font.PLAIN, 15);
        for(int i = 0; i < 4; i++) {
            JLabel optionHeadline = new JLabel("Option " + (i + 1) + ":");
            optionHeadline.setFont(optionsHeadLineFont);
            optionHeadline.setBounds(10, 35 + i * 20, Utils.WINDOW_WIDTH, 15);
            optionHeadline.setVisible(true);
            this.add(optionHeadline);
        }
        // Creating and adding the options text.
    }

    // toString.........................................................................................................

    // compareTo........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
}
