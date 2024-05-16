package pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JDialog {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JFrame parentFrame;

    public Login(JFrame parent) {
        super(parent, "Giriş Yap", true);
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
        JButton loginButton = new JButton("Giriş Yap");
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        pack();
        setLocationRelativeTo(parentFrame);
    }

    private void loginUser() {
        String userName = userNameField.getText();
        String password = new String(passwordField.getPassword());
        boolean authenticated = Database.authenticateUser(userName, password);
        if (authenticated) {
            JOptionPane.showMessageDialog(this, "Giriş yapıldı!");
            Main.setCurrentUserName(userName);
            Main.updateGreeting();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Giriş yapılamadı. Kullanıcı adı veya şifre hatalı.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        setVisible(true);
    }
}
