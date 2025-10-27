import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    private QuestionResultPanel top;
    private QuestionResultPanel middle;
    private QuestionResultPanel bottom;
    private BottomResultPanel bottomResultPanel;

    // Constructor......................................................................................................
    public ResultPanel() {
        // Setting up the panel:
        this.setBounds(0, 0, Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Setting up the panel.

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

    // toString.........................................................................................................

    // compareTo........................................................................................................

    // Methods..........................................................................................................

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
