import javax.swing.*;
import java.awt.*;

public class ChatGPTPanel extends JPanel {
    private BottomWritePollPanel bottomWritePollPanel;
    private JTextField subject;

    public ChatGPTPanel(){
        // Setting up the panel:
        this.setBounds(0,0,Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setVisible(true);
        // Setting up the panel.

        JPanel upper = new JPanel();
        upper.setBounds(0, 0, Utils.WINDOW_WIDTH, Utils.QUESTION_PANEL_HEIGHT*3);
        upper.setBackground(Color.WHITE);
        upper.setLayout(null);
        this.add(upper);

        this.bottomWritePollPanel = new BottomWritePollPanel();
        this.bottomWritePollPanel.getDelayPublication().setVisible(true);
        this.bottomWritePollPanel.setVisible(true);
        this.add(bottomWritePollPanel);

        JLabel instructions = new JLabel("write a subject to chatGPT");
        instructions.setBounds(Utils.WINDOW_WIDTH / 2 - 150, Utils.QUESTION_PANEL_HEIGHT*3/2-45, 300, 30);
        instructions.setVisible(true);
        upper.add(instructions);

        this.subject = new JTextField();
        subject.setBounds(Utils.WINDOW_WIDTH / 2 - 150, Utils.QUESTION_PANEL_HEIGHT*3/2-15, 300, 30);
        this.add(subject);
    }

    public JTextField getSubject() {
        return this.subject;
    }

    public BottomWritePollPanel getBottomWritePollPanel() {
        return bottomWritePollPanel;
    }
}
