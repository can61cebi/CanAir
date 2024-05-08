package pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpDialog extends JDialog {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JFrame parentFrame;

    public SignUpDialog(JFrame parent) {
        super(parent, "Üye Ol", true);
        this.parentFrame = parent;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.gridx = 0;
        gbc.gridy = 0;

        contentPanel.add(new JLabel("Kullanıcı Adı:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        userNameField = new JTextField(20);
        contentPanel.add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        contentPanel.add(new JLabel("Şifre:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(20);
        contentPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Kayıt Ol");
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        pack();
        setLocationRelativeTo(parentFrame);
    }

    private void registerUser() {
        String userName = userNameField.getText();
        String password = new String(passwordField.getPassword());
        boolean success = DatabaseOperations.registerUser(userName, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Kayıt başarılı!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Kayıt sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        setVisible(true);
    }
}