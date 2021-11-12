package ui;

import javax.swing.*;
import java.awt.*;

public class HomeButton extends JButton {
    public HomeButton(Action action, String text) {
        super(action);
        setText(text);
        setBackground(new Color(200, 200, 200));
        setForeground(Color.BLACK);
        setFont(new Font("Courier", Font.PLAIN, 20));
        setMargin(new Insets(7, 30, 7, 30));
        setVisible(true);
    }
}
