package presentation;
import domain.Pagination;
import javax.swing.*;
import java.awt.*;
import java.io.File;
public class HomePage {
    private JPanel panel;
    private JButton importButton;
    private JFrame frame;

    public HomePage(JFrame frame) {
        this.frame = frame;
        panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome, this application is used for paginating one line documents.", SwingConstants.CENTER);
        importButton = new JButton("Paginate Document");
        importButton.addActionListener(e-> paginateDocument());
        panel.add(label, BorderLayout.CENTER);
        panel.add(importButton, BorderLayout.SOUTH);
    }

    private void paginateDocument() {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Handle the file
            String docpath = selectedFile.getAbsolutePath(); 
            JOptionPane.showMessageDialog(null, docpath);
            System.out.println("Selected file: " + docpath);
            
            Pagination pagination = new Pagination();
            pagination.paginateDoc(docpath);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
