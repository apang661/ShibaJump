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
        setPreferredSize(new Dimension(300, 40));
        setVisible(true);
    }
}
