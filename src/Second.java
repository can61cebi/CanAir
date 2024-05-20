package pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        for (int i = 1; i <= 20; i++) {
            JRadioButton radioButton = new JRadioButton();
            JLabel label = new JLabel("Koltuk " + i);
            
            if (occupiedSeats.contains(String.valueOf(i))) {
                radioButton.setEnabled(false);
            }

            singleGroup.add(radioButton);
            gbc.gridx = (i - 1) % 2 * 2;
            gbc.gridy = (i - 1) / 2;
            
            topPanel.add(label, gbc);
            
            gbc.gridx++;
            topPanel.add(radioButton, gbc);
        }
        
        JButton saveButton = new JButton("Kaydet");

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 4;
        topPanel.add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeat = getSelectedSeat();
                if (selectedSeat != null) {
                    boolean updated = Database.updateUserSeat(Main.getCurrentUserId(), flightId, selectedSeat);
                    if (updated) {
                        JOptionPane.showMessageDialog(frame, "Koltuk başarıyla güncellendi!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Koltuk güncellenirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Lütfen bir koltuk seçiniz.", "Koltuk Seçimi Eksik", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        frame.add(topPanel, BorderLayout.CENTER);
    }

    private String getSelectedSeat() {
        Enumeration<AbstractButton> buttons = singleGroup.getElements();
        int seatNumber = 1;
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return String.valueOf(seatNumber);
            }
            seatNumber++;
        }
        return null;
    }
    
    public void display() {
        frame.setVisible(true);
    }
}
