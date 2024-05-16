package pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Second {
    private JFrame frame;
    private JFrame mainFrame;
    private int flightId;
    private JPanel topPanel;
    private ButtonGroup singleGroup;

    public Second(JFrame mainFrame, int flightId) {
        this.mainFrame = mainFrame;
        this.flightId = flightId;
        initializeFrame();
        displayFlightDetails();
        addRadioButtonPanel();
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
        frame.setLayout(new BorderLayout());

        mainFrame.setVisible(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame.setVisible(true);
            }
        });
    }

    private void displayFlightDetails() {
        JPanel detailsPanel = new JPanel();
        String flightDetails = Database.getFlightDetails(flightId);
        JLabel detailsLabel = new JLabel(flightDetails);
        detailsPanel.add(detailsLabel);
        frame.add(detailsPanel, BorderLayout.NORTH);
    }

    private void addRadioButtonPanel() {
        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        singleGroup = new ButtonGroup();

        int row = 0;
        for (int i = 1; i <= 20; i += 2) {
            JRadioButton radioButton1 = new JRadioButton();
            JRadioButton radioButton2 = new JRadioButton();
            JLabel label1 = new JLabel("Koltuk " + i);
            JLabel label2 = new JLabel("Koltuk " + (i + 1));

            singleGroup.add(radioButton1);
            singleGroup.add(radioButton2);

            gbc.gridx = 0;
            gbc.gridy = row;
            topPanel.add(label1, gbc);

            gbc.gridx = 1;
            topPanel.add(radioButton1, gbc);

            gbc.gridx = 2;
            topPanel.add(label2, gbc);

            gbc.gridx = 3;
            topPanel.add(radioButton2, gbc);

            row++;
        }

        frame.add(topPanel, BorderLayout.CENTER);
    }

    public void display() {
        frame.setVisible(true);
    }
}
