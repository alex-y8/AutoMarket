package ui;

import javax.swing.*;

// Graphical interface for the marketplace
public class MarketplaceGUI extends JFrame {

    JFrame frame;

    // EFFECTS: constructs the marketplace gui
    public MarketplaceGUI() {
        frame = new JFrame();
        initialize();
    }

    private void initialize() {
        frame.setSize(600, 600);
        frame.setVisible(true);
    }


}
