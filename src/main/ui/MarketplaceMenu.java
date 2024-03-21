package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MarketplaceMenu extends JFrame {

    private JPanel marketplacePanel;
    private List<Car> cars;
    private CarListMenu carListMenu;
    private JButton buyCarButton;

    public MarketplaceMenu(List<Car> cars) {
        this.cars = cars;
        carListMenu = new CarListMenu();
        createFrame();
        marketplacePanel = new JPanel();
        marketplacePanel.setLayout(new BorderLayout());
        marketplacePanel.add(buttons(), BorderLayout.NORTH);
        marketplacePanel.add(scrollPanel(), BorderLayout.CENTER);
        add(marketplacePanel);

        setVisible(true);
    }

    private void createFrame() {
        setSize(800, 800);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel buttons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buyCarButton = new JButton("Buy car");
        buttonPanel.add(buyCarButton);
        return buttonPanel;
    }

    private JPanel scrollPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(renderCarList(cars));
        centerPanel.add(scrollPane);
        return centerPanel;
    }

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
