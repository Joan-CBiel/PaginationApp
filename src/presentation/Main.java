package presentation;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("My Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setContentPane(new HomePage(frame).getPanel());
            frame.setVisible(true);
        });
    }
}
