import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
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


        String userName = DatabaseFetch.fetchUserNameById(2);
        JLabel label = new JLabel("Merhaba, " + userName);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.BLACK);
        label.setBounds(10, 10, 400, 30);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(label);

        frame.setVisible(true);
    }
}