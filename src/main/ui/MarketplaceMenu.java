package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MarketplaceMenu extends AbstractMenu {

    private JButton buyCarButton;
    private JButton sellCarButton;
    private JButton filterCarButton;
    private JLabel balanceLabel;
    private JComboBox sortComboBox;
    private JComboBox orderComboBox;

    private String selectedFilter;
    private String selectedOrderFilter;
    private List<Car> sortedList;

    private boolean isAscendingOrder = true;

    public MarketplaceMenu(List<Car> cars) {
        super(cars);
        buyCarButtonListener();
    }

    @Override
    // EFFECTS: creates the top panel for the marketplace menu
    protected JPanel createUpperPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buyCarButton = new JButton("Buy car");
        buttonPanel.add(buyCarButton);
        sellCarButton = new JButton("List/Sell car");
        buttonPanel.add(sellCarButton);

        createSortComboBox();
        buttonPanel.add(sortComboBox);

        createOrderComboBox();
        buttonPanel.add(orderComboBox);

        balanceLabel = new JLabel();
        balanceLabel.setText("Balance: $" + df.format(marketplace.getUserAccount().getBalance()));
        buttonPanel.add(balanceLabel);

        return buttonPanel;
    }

    private void createOrderComboBox() {
        String[] order = {"Ascending order", "Descending order"};
        orderComboBox = new JComboBox(order);
        orderComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseSortOrder();
            }
        });
    }

    private void createSortComboBox() {
        String[] filters = {"Sort by:", "Year", "Speed", "Handling", "Acceleration", "Braking", "Drive Type", "Price"};
        sortComboBox = new JComboBox(filters);
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAscendingOrder) {
                    updateCarListAscending();
                } else {
                    updateCarListDescending();
                }
            }
        });
    }

    private void updateCarListAscending() {
        setupLists();

        if (selectedFilter.equals("Year")) {
            sortedList.sort(Comparator.comparingInt(Car::getYear));
            addCarToSortedList();
        } else if (selectedFilter.equals("Speed")) {
            sortedList.sort(Comparator.comparingDouble(Car::getSpeed));
            addCarToSortedList();
        } else if (selectedFilter.equals("Handling")) {
            sortedList.sort(Comparator.comparingDouble(Car::getHandling));
            addCarToSortedList();
        } else if (selectedFilter.equals("Acceleration")) {
            sortedList.sort(Comparator.comparingDouble(Car::getAcceleration));
            addCarToSortedList();
        } else if (selectedFilter.equals("Braking")) {
            sortedList.sort(Comparator.comparingDouble(Car::getBraking));
            addCarToSortedList();
        } else if (selectedFilter.equals("Drive Type")) {
            sortedList.sort(Comparator.comparing(Car::getDriveType));
            addCarToSortedList();
        } else if (selectedFilter.equals("Price")) {
            sortedList.sort(Comparator.comparingDouble(Car::getPrice));
            addCarToSortedList();
        }
    }

    private void updateCarListDescending() {
        setupLists();

        if (selectedFilter.equals("Year")) {
            sortedList.sort(Comparator.comparingInt(Car::getYear).reversed());
            addCarToSortedList();
        } else if (selectedFilter.equals("Speed")) {
            sortedList.sort(Comparator.comparingDouble(Car::getSpeed).reversed());
            addCarToSortedList();
        } else if (selectedFilter.equals("Handling")) {
            sortedList.sort(Comparator.comparingDouble(Car::getHandling).reversed());
            addCarToSortedList();
        } else if (selectedFilter.equals("Acceleration")) {
            sortedList.sort(Comparator.comparingDouble(Car::getAcceleration).reversed());
            addCarToSortedList();
        } else if (selectedFilter.equals("Braking")) {
            sortedList.sort(Comparator.comparingDouble(Car::getBraking).reversed());
            addCarToSortedList();
        } else if (selectedFilter.equals("Drive Type")) {
            sortedList.sort(Comparator.comparing(Car::getDriveType).reversed());
            addCarToSortedList();
        } else if (selectedFilter.equals("Price")) {
            sortedList.sort(Comparator.comparingDouble(Car::getPrice).reversed());
            addCarToSortedList();
        }
    }

    private void setupLists() {
        carDefaultListModel.clear();
        sortedList = new ArrayList<>(carList);
        selectedFilter = (String) sortComboBox.getSelectedItem();

        if (selectedFilter.equals("Sort by:")) {
            for (Car c : carList) {
                carDefaultListModel.addElement(c);
            }
        }
    }


    private void addCarToSortedList() {
        for (Car c : sortedList) {
            carDefaultListModel.addElement(c);
        }
    }

    private void chooseSortOrder() {
        selectedOrderFilter = (String) orderComboBox.getSelectedItem();
        isAscendingOrder = selectedOrderFilter.equals("Ascending order");
        if (isAscendingOrder) {
            updateCarListAscending();
        } else {
            updateCarListDescending();
        }
    }

    // EFFECTS: creates the menu scroll panel
    @Override
    protected JPanel createMainPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(createCarsJList(carList));
        centerPanel.add(scrollPane);
        return centerPanel;
    }

    // EFFECTS: button listener for the buy car button, buys the selected car when clicked
    private void buyCarButtonListener() {
        buyCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Car c : carListMenu.getSelectedCarList()) {
                    marketplace.buyCar(c);
                    balanceLabel.setText("Balance: $" + df.format(marketplace.getUserAccount().getBalance()));
                }
            }
        });
    }

}
