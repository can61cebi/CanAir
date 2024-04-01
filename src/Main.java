import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;

public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Boyut ayarlama
        int frameWidth = (int) (width * 0.57);
        int frameHeight = (int) (height * 0.70);
        int panelHeight = (int) (frameHeight * 0.11);

        // Ana pencere
        JFrame frame = new JFrame("CanAir");
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Üst panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(35, 43, 56));
        topPanel.setPreferredSize(new Dimension(frameWidth, panelHeight));

        frame.add(topPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }
}

// Projede hatırlatmalar için gerekli commentler eklenmektedir.