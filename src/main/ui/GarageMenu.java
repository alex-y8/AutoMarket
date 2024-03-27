package ui;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Creates the GarageMenu
public class GarageMenu extends AbstractMenu {

    private JLabel balanceLabel;
    private JButton refundCarButton;
    private JComboBox sortComboBox;
    private JComboBox orderComboBox;

    private String selectedFilter;
    private String selectedOrderFilter;
    private List<Car> sortedList;

    private boolean isAscendingOrder = true;

    // EFFECTS: constructs a new GarageMenu
    public GarageMenu(List<Car> garageList) {
        super(garageList);
        refundCarButtonListener();
    }

    // MODIFIES: this
    // EFFECTS: creates the upper panel and adds buttons, combobox, labels to it
    @Override
    protected JPanel createUpperPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        refundCarButton = new JButton("Refund car");
        buttonPanel.add(refundCarButton);

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

    // MODIFIES: carList
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

    // MODIFIES: carList
    // EFFECTS: adds the car to the sorted list
    private void addCarToSortedList() {
        for (Car c : sortedList) {
            carDefaultListModel.addElement(c);
        }
    }

    // MODIFIES: carList
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

    // MODIFIES: this
    // EFFECTS: button listener for the Refund car button
    private void refundCarButtonListener() {
        refundCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refundCar();
            }
        });
    }

    // MODIFIES: this, userAccount
    // EFFECTS: removes the selected car from the garage and refunds the car money to the user account
    private void refundCar() {
        double balanceToSet = 0.0;
        double currentBalance = marketplace.getUserAccount().getBalance();;
        for (Car c : carListMenu.getSelectedCarList()) {
            marketplace.getUserGarage().removeCar(c);
            balanceToSet += c.getPrice();
            try {
                marketplace.getUserAccount().setBalance(currentBalance + balanceToSet);
                balanceLabel.setText("Balance: $" + df.format(marketplace.getUserAccount().getBalance()));
            } catch (IllegalAccountBalanceException e) {
                // can't be thrown
            }
        }
        dispose();
        new GarageMenu(marketplace.getUserGarage().getCars());
    }

    // MODIFIES: this
    // EFFECTS: creates the main panel with a scroll pane
    @Override
    protected JPanel createMainPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(createCarsJList(carList));
        centerPanel.add(scrollPane);
        return centerPanel;
    }

}
