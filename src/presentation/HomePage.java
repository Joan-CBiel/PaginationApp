package presentation;
import domain.Pagination;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomePage {
    private JPanel panel;
    private JFrame frame;

    private JLabel importLabel;
    private JLabel exportLabel;
    public HomePage(JFrame frame) {
        this.frame = frame;
        JLabel label = new JLabel("Welcome, this application is used for paginating one line documents.", SwingConstants.CENTER);

        frame.add(label, BorderLayout.CENTER);



        // Create a panel for the buttons and labels
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create the first label and button
        importLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(importLabel, gbc);

        JButton importButton = new JButton("Input Document");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(importButton, gbc);

        // Create the second label and button
        exportLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(exportLabel, gbc);

        JButton exportButton = new JButton("Output Document");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(exportButton, gbc);

        JButton paginateButton = new JButton("Paginate");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(paginateButton, gbc);

        importButton.addActionListener(e-> chooseInputDocument());
        exportButton.addActionListener(e-> chooseOutputDocument());

        // Add the panel to the south part of the frame
        frame.add(panel, BorderLayout.SOUTH);
    }

    private void chooseInputDocument() {
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

            importLabel.setText(docpath);
            Pagination pagination = new Pagination();
            pagination.paginateDoc(docpath);
        }
    }
    private void chooseOutputDocument() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a directory to save your file:");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            String fileName = JOptionPane.showInputDialog("Enter the file name:");
            exportLabel.setText(selectedDirectory+File.pathSeparator+fileName);


        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
