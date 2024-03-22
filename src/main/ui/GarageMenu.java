package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GarageMenu extends AbstractMenu {

    public GarageMenu(List<Car> garageList) {
        super(garageList);

    }

    @Override
    protected JPanel createUpperPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createMainPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(createCarsJList(carList));
        centerPanel.add(scrollPane);
        return centerPanel;
    }

}
