package pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

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

    private void displayFlightDetails() {
        JPanel detailsPanel = new JPanel();
        String flightDetails = Database.getFlightDetails(flightId);
        JLabel detailsLabel = new JLabel(flightDetails);
        detailsPanel.add(detailsLabel);
        frame.add(detailsPanel, BorderLayout.NORTH);
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

    private void addRadioButtonPanel() {
        List<String> occupiedSeats = Database.getOccupiedSeats(flightId);

        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        singleGroup = new ButtonGroup();

        // 20 koltuğu iki sütunda düzenleme
        for (int i = 1; i <= 20; i++) {
            JRadioButton radioButton = new JRadioButton();
            JLabel label = new JLabel("Koltuk " + i);
            
            // Doluluk kontrolü
            if (occupiedSeats.contains(String.valueOf(i))) {
                radioButton.setEnabled(false);
            }

            singleGroup.add(radioButton);
            // Sütun hesaplaması (0 veya 1 değerini alacak)
            gbc.gridx = (i - 1) % 2 * 2; // Çift sıra için x konumu (label için 0 veya 2, radioButton için 1 veya 3)
            // Satır hesaplaması (0'dan başlayarak her iki koltukta bir artacak)
            gbc.gridy = (i - 1) / 2;     // Her iki koltuktan sonra bir alt satıra geç
            
            // Label'ı panele ekle
            topPanel.add(label, gbc);
            
            // RadioButton'ı panele ekle
            gbc.gridx++;  // RadioButton'ın konumunu label'ın hemen yanına ayarla
            topPanel.add(radioButton, gbc);
        }

        frame.add(topPanel, BorderLayout.CENTER);
    }
    
    public void display() {
        frame.setVisible(true);
    }
}
