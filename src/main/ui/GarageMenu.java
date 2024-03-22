package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GarageMenu extends JFrame {

    private Marketplace marketplace = new Marketplace();

    private JPanel garagePanel;
    private List<Car> garageList;
    private CarListMenu carListMenu;

    public GarageMenu(List<Car> garageList) {
        this.garageList = garageList;
        carListMenu = new CarListMenu();
        createFrame();
        garagePanel = new JPanel();
        garagePanel.setLayout(new BorderLayout());
        garagePanel.add(scrollPanel(), BorderLayout.CENTER);
        add(garagePanel);

        setVisible(true);
    }


    // EFFECTS: creates the garage menu window frame
    private void createFrame() {
        setSize(MarketplaceGUI.WIDTH, MarketplaceGUI.HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // EFFECTS: creates the garage menu scroll panel
    private JPanel scrollPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(renderCarList(garageList));
        centerPanel.add(scrollPane);
        return centerPanel;
    }

    // EFFECTS: creates the list of cars in the garage
    private JList<Car> renderCarList(List<Car> cars) {
        DefaultListModel<Car> carDefaultListModel = new DefaultListModel<>();
        for (Car c : cars) {
            carDefaultListModel.addElement(c);
        }
        JList<Car> carJList = new JList<>(carDefaultListModel);
        carJList.setCellRenderer(carListMenu);
        return carJList;
    }
}
