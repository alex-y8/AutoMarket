package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class MarketplaceMenu extends AbstractMenu {

    private JButton buyCarButton;
    private JButton sellCarButton;
    private JButton filterCarButton;
    private JLabel balanceLabel;

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
        filterCarButton = new JButton("Filter cars");
        buttonPanel.add(filterCarButton);

        balanceLabel = new JLabel();
        balanceLabel.setText("Balance: $" + df.format(marketplace.getUserAccount().getBalance()));
        buttonPanel.add(balanceLabel);

        return buttonPanel;
    }

    // EFFECTS: creates the menu scroll panel
    @Override
    protected JPanel createMainPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(super.createCarsJList(carList));
        centerPanel.add(scrollPane);
        return centerPanel;
    }

    // EFFECTS: button listener for the buy car button, buys the selected car when clicked
    private void buyCarButtonListener() {
        buyCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Car c : carListMenu.getSelectedCarList()) {
                    //if ()
                    //marketplace.getUserGarage().addCar(c);
                    marketplace.buyCar(c);
                    balanceLabel.setText("Balance: $" + df.format(marketplace.getUserAccount().getBalance()));
                }
            }
        });
    }

}
