package pkg;

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
import com.toedter.calendar.JDateChooser;
import java.util.Date;

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

        ImageIcon imageIcon = new ImageIcon("background.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(255, 255, 255));
        centerPanel.setPreferredSize(new Dimension(1151, 400));
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        container.add(centerPanel, gbc);
        frame.add(container, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 255));
        bottomPanel.setPreferredSize(new Dimension(815, 185));
        bottomPanel.setLayout(new GridBagLayout());

        JPanel container_bottom = new JPanel(new GridBagLayout());
        GridBagConstraints gbc_bottom = new GridBagConstraints();
        gbc_bottom.anchor = GridBagConstraints.SOUTH;
        gbc_bottom.insets = new Insets(0, 0, 285, 0);

        container_bottom.add(bottomPanel, gbc_bottom);
        frame.add(container_bottom, BorderLayout.SOUTH);

        String[] options1 = {"İstanbul", "Ankara", "İzmir"};
        String[] options2 = {"Trabzon", "Adana", "Malatya"};

        JComboBox<String> comboBox1 = new JComboBox<>(options1);
        JComboBox<String> comboBox2 = new JComboBox<>(options2);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");

        JButton searchFlightButton = new JButton("Uçuş Ara");

        JLabel label1 = new JLabel("Nereden");
        JLabel label2 = new JLabel("Nereye");
        JLabel label3 = new JLabel("Tarih");
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);

        GridBagConstraints gbc_buttons = new GridBagConstraints();
        gbc_buttons.fill = GridBagConstraints.HORIZONTAL;
        gbc_buttons.anchor = GridBagConstraints.CENTER;
        gbc_buttons.insets = new Insets(10, 20, 5, 20);

        gbc_buttons.gridx = 0; gbc_buttons.gridy = 0;
        bottomPanel.add(label1, gbc_buttons);
        gbc_buttons.gridy = 1;
        bottomPanel.add(separator1, gbc_buttons);

        gbc_buttons.gridy = 2;
        bottomPanel.add(comboBox1, gbc_buttons);

        gbc_buttons.gridx = 1; gbc_buttons.gridy = 0;
        bottomPanel.add(label2, gbc_buttons);
        gbc_buttons.gridy = 1;
        bottomPanel.add(separator2, gbc_buttons);

        gbc_buttons.gridy = 2;
        bottomPanel.add(comboBox2, gbc_buttons);

        gbc_buttons.gridx = 2; gbc_buttons.gridy = 0;
        bottomPanel.add(label3, gbc_buttons);
        gbc_buttons.gridy = 1;
        bottomPanel.add(separator3, gbc_buttons);

        gbc_buttons.gridy = 2;
        bottomPanel.add(dateChooser, gbc_buttons);

        gbc_buttons.gridx = 3; gbc_buttons.gridy = 2;
        gbc_buttons.insets = new Insets(10, 20, 10, 20);
        bottomPanel.add(searchFlightButton, gbc_buttons);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpDialog signUpDialog = new SignUpDialog(frame);
                signUpDialog.display();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginDialog = new LoginDialog(frame);
                loginDialog.display();
            }
        });
        
        int userId = getCurrentUserId();
        
        searchFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = comboBox1.getSelectedItem().toString();
                String to = comboBox2.getSelectedItem().toString();
                Date date = dateChooser.getDate();

                if (date == null) {
                    JOptionPane.showMessageDialog(frame, "Lütfen bir tarih seçiniz.", "Tarih Eksik", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int userId = getCurrentUserId();
                if (userId == -1) {
                    JOptionPane.showMessageDialog(frame, "Lütfen önce giriş yapınız.", "Kullanıcı Girişi Gerekli", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int flightId = DatabaseOperations.findOrCreateFlight(from, to, date);
                if (flightId != -1) {
                    boolean assigned = DatabaseOperations.assignFlightToUser(userId, flightId);
                    if (assigned) {
                        JOptionPane.showMessageDialog(frame, "Uçuş başarıyla bulundu ve kullanıcıya atandı!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Uçuş kullanıcıya atanamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Uçuş bulunamadı veya oluşturulamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
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
    
    public static void setCurrentUserName(String userName) {
        currentUserName = userName;
        updateGreeting();
    }
    
    public static int getCurrentUserId() {
        if (currentUserName == null) {
            return -1;
        }

        return DatabaseOperations.getUserId(currentUserName);
    }
}
        
