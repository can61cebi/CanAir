package pkg;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Second {
    private JFrame frame;
    private JFrame mainFrame;
    private int flightId;

    public Second(JFrame mainFrame, int flightId) {
        this.mainFrame = mainFrame;
        this.flightId = flightId;
        initializeFrame();
        displayFlightDetails();
    }

    private void initializeFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int frameWidth = (int) (width * 0.57);
        int frameHeight = (int) (height * 0.70);

        frame = new JFrame("Uçuş Detayları");
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        mainFrame.setVisible(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame.setVisible(true);
            }
        });
    }
    
    private void displayFlightDetails() {
        String flightDetails = Database.getFlightDetails(flightId);
        JLabel detailsLabel = new JLabel(flightDetails);
        detailsLabel.setBounds(10, 10, 350, 200);
        frame.add(detailsLabel);
    }

    public void display() {
        frame.setVisible(true);
    }
}
