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

public class Main {
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

        // "Üye Ol" butonu
        JButton signUpButton = new JButton("Üye Ol");
        signUpButton.setBounds(frameWidth - 245, 30, 100, 30);
        topPanel.add(signUpButton);

        // "Giriş Yap" butonu
        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setBounds(frameWidth - 130, 30, 100, 30);
        topPanel.add(loginButton);

        frame.add(topPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }
}