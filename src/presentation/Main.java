package presentation;
import javax.swing.*;

/**
 * Main function of the program, displays the frame
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Paginate Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame= new HomePage(frame).getFrame();
            frame.setVisible(true);
        });
    }
}
