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

// Creates the marketplace menu
public class MarketplaceMenu extends AbstractMenu {

    private JButton buyCarButton;
    private JButton sellCarButton;
    private JLabel balanceLabel;
    private JComboBox sortComboBox;
    private JComboBox orderComboBox;

    private String selectedFilter;
    private String selectedOrderFilter;
    private List<Car> sortedList;

    private boolean isAscendingOrder = true;

    // EFFECTS: constructs a new marketplace menu with the list of cars available for purchase
    public MarketplaceMenu(List<Car> cars) {
        super(cars);
        buyCarButtonListener();
        sellCarButtonListener();
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

    // MODIFIES: this
    // EFFECTS: creates the combo box for choosing the sorting order
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

    // MODIFIES: this
    // EFFECTS: creates the combobox for sorting by the car's fields
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

    // MODIFIES: garageList
    // EFFECTS: sorts the list of cars according to the selected sorting filter, in ascending order
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

    // MODIFIES: garageList
    // EFFECTS: sorts the list of cars according to the selected sorting filter, in descending order
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

    // EFFECTS: initializes the list for sorting and creates the first element of the combo boxes
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

    // MODIFIES: garageList
    // EFFECTS: adds the car to the sorted list
    private void addCarToSortedList() {
        for (Car c : sortedList) {
            carDefaultListModel.addElement(c);
        }
    }

    // EFFECTS: chooses how to sort the lists, according to the sort combo box selection
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
            // EFFECTS: buys the car from marketplace, adds it to garage, and subtracts car price from account balance
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Car c : carListMenu.getSelectedCarList()) {
                    marketplace.buyCar(c);
                    balanceLabel.setText("Balance: $" + df.format(marketplace.getUserAccount().getBalance()));
                }
            }
        });
    }

    // EFFECTS: button listener for the sell car button, opens a list car menu when clicked
    private void sellCarButtonListener() {
        sellCarButton.addActionListener(new ActionListener() {
            // EFFECTS: refreshes the sell car menu
            @Override
            public void actionPerformed(ActionEvent e) {
                new SellCarMenu();
                dispose();
            }
        });
    }

}
