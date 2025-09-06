import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    // Constructor......................................................................................................
    public Button(String buttonTitle, int x, int y) {
        this.setText(buttonTitle);
        this.setBounds(x, y, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        Font font = new Font ("Arial", Font.PLAIN, 20);
        this.setFont(font);
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................

    // Getters & Setters................................................................................................
}
