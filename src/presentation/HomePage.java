package presentation;

import domain.Pagination;
import exceptions.NotOneLineTextException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


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
        gbc.gridy = 1;
        panel.add(importLabel, gbc);

        JButton importButton = new JButton("Input Document");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(importButton, gbc);

        // Create the second label and button
        exportLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(exportLabel, gbc);

        JButton exportButton = new JButton("Output Document");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(exportButton, gbc);

        JButton paginateButton = new JButton("Paginate");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        panel.add(paginateButton, gbc);

        importButton.addActionListener(e-> chooseInputDocument());
        exportButton.addActionListener(e-> chooseOutputDocument());
        paginateButton.addActionListener(e-> paginateDocument());
        // Add the panel to the south part of the frame
        frame.add(panel, BorderLayout.SOUTH);
    }

    private void chooseInputDocument() {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
        fileChooser.setFileFilter(filter);
        boolean validFileChosen = false;
        while (!validFileChosen) {
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (selectedFile.exists()) {
                    if (!path.endsWith(".txt"))
                        JOptionPane.showMessageDialog(frame, "File must have a .txt extension", "Invalid File", JOptionPane.ERROR_MESSAGE);
                    else {
                        validFileChosen = true;

                        importLabel.setText(path);
                    }
                } else
                    JOptionPane.showMessageDialog(frame, "File must exists", "Invalid File", JOptionPane.ERROR_MESSAGE);

            } else
                break; // User cancelled the dialog
        }
    }
    private void chooseOutputDocument() {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF and TXT Files", "pdf", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Save As");
        fileChooser.setApproveButtonText("Save");
        boolean validFileChosen = false;
        while (!validFileChosen) {
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (!path.endsWith(".pdf") && !path.endsWith(".txt"))
                    JOptionPane.showMessageDialog(frame, "File must have a .pdf or .txt extension", "Invalid File", JOptionPane.ERROR_MESSAGE);
                else {
                    validFileChosen = true;
                    exportLabel.setText(path);
                }

            } else {
                break; // User cancelled the dialog
            }
        }

    }

    private void paginateDocument(){
        if(Objects.equals(importLabel.getText(), "") || Objects.equals(exportLabel.getText(), ""))
            JOptionPane.showMessageDialog(frame, "Make sure to select the Input and Output files", "Files not selected", JOptionPane.ERROR_MESSAGE);

        else{
            try {
                new Pagination().paginateDoc(importLabel.getText(), exportLabel.getText());
                JOptionPane.showMessageDialog(frame, "File: " + exportLabel.getText() + "\nCreated Successfully", "Files created", JOptionPane.PLAIN_MESSAGE);
            } catch (NotOneLineTextException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Error saving PDF file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }



    }

    public JFrame getFrame() {
        return frame;
    }
}
