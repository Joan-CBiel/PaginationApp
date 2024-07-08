package presentation;

import javax.swing.*;
import java.awt.*;

public class HomePage {
    private JPanel panel;
    private JButton importButton;
    private JFrame frame;

    public HomePage(JFrame frame) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Home Page", SwingConstants.CENTER);
        importButton = new JButton("Go to Import Page");
        //importButton.addActionListener(e -> frame.setContentPane(new ImportPage(frame).getPanel()));
        panel.add(label, BorderLayout.CENTER);
        panel.add(importButton, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }
}
