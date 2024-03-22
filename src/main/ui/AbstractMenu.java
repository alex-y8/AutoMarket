package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public abstract class AbstractMenu extends JFrame {

    private JPanel panel;
    protected CarListMenu carListMenu;
    protected List<Car> carList;
    protected static Marketplace marketplace = new Marketplace();

    protected final DecimalFormat df;

    public AbstractMenu(List<Car> carList) {
        df = new DecimalFormat("#,###.##");
        this.carList = carList;
        carListMenu = new CarListMenu();
        createFrame();

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(createUpperPanel(), BorderLayout.NORTH);
        panel.add(createMainPanel(), BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }

    // EFFECTS: creates the menu window frame
    private void createFrame() {
        setSize(MarketplaceGUI.WIDTH, MarketplaceGUI.HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // EFFECTS: creates the upper panel
    protected abstract JPanel createUpperPanel();

    // EFFECTS: creates the menu scroll panel
    protected abstract JPanel createMainPanel();

    // EFFECTS: creates the list of cars as a JList
    protected JList<Car> createCarsJList(List<Car> carList) {
        DefaultListModel<Car> carDefaultListModel = new DefaultListModel<>();
        for (Car c : carList) {
            carDefaultListModel.addElement(c);
        }
        JList<Car> carJList = new JList<>(carDefaultListModel);
        carJList.setCellRenderer(carListMenu);
        return carJList;
    }

}
