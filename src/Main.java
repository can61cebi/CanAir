import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static String currentUserName = null;
    private static JLabel greetingLabel;

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int frameWidth = (int) (width * 0.57);
        int frameHeight = (int) (height * 0.70);
        int panelHeight = (int) (frameHeight * 0.11);

        JFrame frame = new JFrame("CanAir");
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(35, 43, 56));
        topPanel.setPreferredSize(new Dimension(frameWidth, panelHeight));
        topPanel.setLayout(null);

        ImageIcon originalIcon = new ImageIcon("logo.png");
        int logoHeight = panelHeight - 10;
        int logoWidth = originalIcon.getIconWidth() * logoHeight / originalIcon.getIconHeight();

        Image scaledImage = originalIcon.getImage().getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setBounds(10, 5, logoWidth, logoHeight);
        topPanel.add(logoLabel);

        JButton signUpButton = new JButton("Üye Ol");
        signUpButton.setBounds(frameWidth - 245, 30, 100, 30);
        topPanel.add(signUpButton);

        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setBounds(frameWidth - 130, 30, 100, 30);
        topPanel.add(loginButton);

        frame.add(topPanel, BorderLayout.NORTH);
        greetingLabel = new JLabel("CanAir'e Hoşgeldiniz.", SwingConstants.CENTER);
        updateGreeting();
        greetingLabel.setBounds((frameWidth - 300) / 2, (panelHeight - 30) / 2, 300, 30);
        topPanel.add(greetingLabel);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog signUpDialog = new JDialog(frame, "Üye Ol", true);
                signUpDialog.setLayout(new BorderLayout());

                JPanel contentPanel = new JPanel(new GridBagLayout());
                signUpDialog.add(contentPanel, BorderLayout.CENTER);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(4, 4, 4, 4);
                gbc.gridx = 0;
                gbc.gridy = 0;

                contentPanel.add(new JLabel("Kullanıcı Adı:"), gbc);

                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                JTextField userNameField = new JTextField(20);
                contentPanel.add(userNameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.fill = GridBagConstraints.NONE;
                gbc.weightx = 0;
                contentPanel.add(new JLabel("Şifre:"), gbc);

                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                JPasswordField passwordField = new JPasswordField(20);
                contentPanel.add(passwordField, gbc);

                JPanel buttonPanel = new JPanel();
                JButton registerButton = new JButton("Kayıt Ol");
                buttonPanel.add(registerButton);
                signUpDialog.add(buttonPanel, BorderLayout.SOUTH);

                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String userName = userNameField.getText();
                        String password = new String(passwordField.getPassword());

                        boolean success = DatabaseOperations.registerUser(userName, password);
                        if (success) {
                            JOptionPane.showMessageDialog(signUpDialog, "Kayıt başarılı!");
                        } else {
                            JOptionPane.showMessageDialog(signUpDialog, "Kayıt sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                signUpDialog.pack();
                signUpDialog.setLocationRelativeTo(frame);
                signUpDialog.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog loginDialog = new JDialog(frame, "Giriş Yap", true);
                loginDialog.setLayout(new BorderLayout());

                JPanel contentPanel = new JPanel(new GridBagLayout());
                loginDialog.add(contentPanel, BorderLayout.CENTER);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(4, 4, 4, 4);
                gbc.gridx = 0;
                gbc.gridy = 0;

                contentPanel.add(new JLabel("Kullanıcı Adı:"), gbc);

                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                JTextField userNameField = new JTextField(20);
                contentPanel.add(userNameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                contentPanel.add(new JLabel("Şifre:"), gbc);

                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                JPasswordField passwordField = new JPasswordField(20);
                contentPanel.add(passwordField, gbc);

                JPanel buttonPanel = new JPanel();
                JButton loginButton = new JButton("Giriş Yap");
                buttonPanel.add(loginButton);
                loginDialog.add(buttonPanel, BorderLayout.SOUTH);

                loginButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String userName = userNameField.getText();
                        String password = new String(passwordField.getPassword());

                        boolean authenticated = DatabaseOperations.authenticateUser(userName, password);
                        if (authenticated) {
                            JOptionPane.showMessageDialog(loginDialog, "Giriş yapıldı!");
                            currentUserName = userName;
                            updateGreeting();
                            loginDialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(loginDialog, "Giriş yapılamadı. Kullanıcı adı veya şifre hatalı.", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                loginDialog.pack();
                loginDialog.setLocationRelativeTo(frame);
                loginDialog.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

    public static void updateGreeting() {
        if (currentUserName != null) {
            greetingLabel.setText("Merhaba, " + currentUserName);
        } else {
            greetingLabel.setText("CanAir'e Hoşgeldiniz.");
        }
        greetingLabel.setForeground(Color.WHITE);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 25));
    }
}