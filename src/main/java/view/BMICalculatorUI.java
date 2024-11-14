// view/BMICalculatorUI.java
package view;

import controller.BMICalculatorController;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;

public class BMICalculatorUI extends JFrame {
    private BMICalculatorController controller;
    
    private JComboBox<String> userDropdownInResultPane;
    private JComboBox<String> userDropdownInUsersPane;

    // Constructor
    public BMICalculatorUI(BMICalculatorController controller) {
        this.controller = controller;
        setTitle("BMI Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // First Tab: BMI
        JPanel bmiPanel = createBMIPanel();
        tabbedPane.addTab("BMI", bmiPanel);

        // Second Tab: Result
        JPanel resultPanel = createResultPanel();
        tabbedPane.addTab("Result", resultPanel);

        // Third Tab: Users
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("Users", usersPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBMIPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextArea statusBox = new JTextArea(5, 20);
        statusBox.setEditable(false);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Perform validation and add user
            // Update status box with success or error messages
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Height (m):"));
        panel.add(heightField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(submitButton);
        panel.add(new JLabel("Status:"));
        panel.add(new JScrollPane(statusBox));
        submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Input validation
        String name = nameField.getText();
        String ageText = ageField.getText();
        String heightText = heightField.getText();
        String weightText = weightField.getText();

        if (name.isEmpty() || ageText.isEmpty() || heightText.isEmpty() || weightText.isEmpty()) {
            statusBox.setText("All fields are required.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            double height = Double.parseDouble(heightText);
            double weight = Double.parseDouble(weightText);

            // Create a new User object
            User newUser = new User(name, age, height, weight);

            // Add the user to the controller
            controller.addUser(newUser);

            // Update the status box with success message and user count
            int userCount = controller.getUserCount();
            statusBox.setText("User added successfully! Total users: " + userCount);

            // Clear input fields after successful submission
            nameField.setText("");
            ageField.setText("");
            heightField.setText("");
            weightField.setText("");
            updateUserDropdown(userDropdownInResultPane);
            updateUserDropdown(userDropdownInUsersPane);

        } catch (NumberFormatException ex) {
            statusBox.setText("Please enter valid numbers for Age, Height, and Weight.");
        }
    }
});
        
        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Title and font styling
        JLabel titleLabel = new JLabel("User BMI Result");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        userDropdownInResultPane = new JComboBox<>();
        updateUserDropdown(userDropdownInResultPane);
        JTextArea resultBox = new JTextArea(5, 20);
        resultBox.setEditable(false);
        resultBox.setBorder(new TitledBorder("Result")); // Add a titled border to the result box
        resultBox.setFont(new Font("Arial", Font.PLAIN, 14));

        

        // Action listener to display selected user's BMI and health status
        userDropdownInResultPane.addActionListener(e -> {
        int selectedIndex = userDropdownInResultPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            User selectedUser = controller.getUser(selectedIndex);
            double bmi = selectedUser.calculateBMI();
            String healthStatus = selectedUser.getHealthStatus();
            resultBox.setText("BMI: " + String.format("%.2f", bmi) + "\nHealth Status: " + healthStatus);
        }
        });
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select User:"));
        topPanel.add(userDropdownInResultPane);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(resultBox), BorderLayout.SOUTH);

        return panel;
    }
    
    private void updateUserDropdown(JComboBox<String> userDropdown) {
        if (userDropdown == null) {
        return; // Exit if the dropdown hasn't been initialized
        }
        userDropdown.removeAllItems();
        for (User user : controller.getUsers()) {
            userDropdown.addItem(user.getName());
        }
    }

    private JPanel createUsersPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the panel

    JLabel titleLabel = new JLabel("Manage Users");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the title

    userDropdownInUsersPane = new JComboBox<>();
    updateUserDropdown(userDropdownInUsersPane);

    JTextField nameField = new JTextField(15);
    JTextField ageField = new JTextField(15);
    JTextField heightField = new JTextField(15);
    JTextField weightField = new JTextField(15);
    JTextArea statusBox = new JTextArea(3, 20);
    statusBox.setEditable(false);
    statusBox.setBorder(new TitledBorder("Status"));
    statusBox.setFont(new Font("Arial", Font.PLAIN, 14));

    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");

    // Set action listeners for dropdown, update, and delete
    userDropdownInUsersPane.addActionListener(e -> {
        int selectedIndex = userDropdownInUsersPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            User selectedUser = controller.getUser(selectedIndex);
            nameField.setText(selectedUser.getName());
            ageField.setText(String.valueOf(selectedUser.getAge()));
            heightField.setText(String.valueOf(selectedUser.getHeight()));
            weightField.setText(String.valueOf(selectedUser.getWeight()));
        }
    });

    updateButton.addActionListener(e -> {
        int selectedIndex = userDropdownInUsersPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double height = Double.parseDouble(heightField.getText());
                double weight = Double.parseDouble(weightField.getText());

                User updatedUser = new User(name, age, height, weight);
                controller.updateUser(selectedIndex, updatedUser);
                updateUserDropdown(userDropdownInResultPane);
                updateUserDropdown(userDropdownInUsersPane);
                statusBox.setText("User updated successfully.");
            } catch (NumberFormatException ex) {
                statusBox.setText("Please enter valid numbers for Age, Height, and Weight.");
            }
        }
    });

    deleteButton.addActionListener(e -> {
        int selectedIndex = userDropdownInUsersPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            controller.deleteUser(selectedIndex);
            updateUserDropdown(userDropdownInResultPane);
            updateUserDropdown(userDropdownInUsersPane);
            nameField.setText("");
            ageField.setText("");
            heightField.setText("");
            weightField.setText("");
            statusBox.setText("User deleted successfully.");
        }
    });

    // Form panel for user data fields
    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    formPanel.setBorder(new TitledBorder("Edit User Details"));

    formPanel.add(new JLabel("Select User:"));
    formPanel.add(userDropdownInUsersPane);
    formPanel.add(new JLabel("Name:"));
    formPanel.add(nameField);
    formPanel.add(new JLabel("Age:"));
    formPanel.add(ageField);
    formPanel.add(new JLabel("Height (m):"));
    formPanel.add(heightField);
    formPanel.add(new JLabel("Weight (kg):"));
    formPanel.add(weightField);

    // Panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.add(updateButton);
    buttonPanel.add(deleteButton);

    // Add components to the main panel in order
    panel.add(titleLabel);
    panel.add(Box.createVerticalStrut(10)); // Spacer
    panel.add(formPanel);
    panel.add(Box.createVerticalStrut(10)); // Spacer
    panel.add(buttonPanel);
    panel.add(Box.createVerticalStrut(10)); // Spacer
    panel.add(new JScrollPane(statusBox));

    return panel;
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BMICalculatorController controller = new BMICalculatorController();
            BMICalculatorUI ui = new BMICalculatorUI(controller);
            ui.setVisible(true);
        });
    }
    
    
}
