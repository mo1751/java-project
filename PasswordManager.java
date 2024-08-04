import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordManager extends JFrame implements ActionListener {
    private JTextField websiteField, usernameField, passwordField;
    private JList<String> passwordList;
    private DefaultListModel<String> listModel;
    private JButton addButton, removeButton, saveButton, loadButton;
    private List<String> passwords = new ArrayList<>();
    private final String FILENAME = "passwords.txt";

    public PasswordManager() {
        setTitle("Password Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        websiteField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JTextField(20);
        passwordList = new JList<>();
        listModel = new DefaultListModel<>();
        passwordList.setModel(listModel);

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Website:"));
        inputPanel.add(websiteField);
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(passwordList), BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String website = websiteField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!website.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                String entry = website + ": " + username + " - " + password;
                listModel.addElement(entry);
                passwords.add(entry);
                websiteField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            }
        } else if (e.getSource() == removeButton) {
            int selectedIndex = passwordList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                passwords.remove(selectedIndex);
            }
        } else if (e.getSource() == saveButton) {
            savePasswordsToFile();
        } else if (e.getSource() == loadButton) {
            loadPasswordsFromFile();
        }
    }

    private void savePasswordsToFile() {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            for (String password : passwords) {
                writer.write(password + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving passwords.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPasswordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
                passwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading passwords.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        PasswordManager manager = new PasswordManager();
        manager.setVisible(true);
    }
}
