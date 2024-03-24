package ui;

import model.cars.Car;
import model.cars.DriveType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SellCarMenu extends JFrame {

    private JPanel panel;
    private JPanel bottomPanel;
    private JLabel manufacturerLabel;
    private JLabel modelLabel;
    private JLabel yearLabel;
    private JLabel speedLabel;
    private JLabel handlingLabel;
    private JLabel accelerationLabel;
    private JLabel brakingLabel;
    private JLabel driveTypeLabel;
    private JLabel priceLabel;
    private JLabel imageLabel;

    private JTextField manufacturerTextField;
    private JTextField modelTextField;
    private JTextField yearTextField;
    private JTextField speedTextField;
    private JTextField handlingTextField;
    private JTextField accelerationTextField;
    private JTextField brakingTextField;
    private JTextField driveTypeTextField;
    private JTextField priceTextField;

    private JButton listCarButton;
    private JButton imageButton;

    private Dimension textFieldDimension;

    private Car car;
    private String imageFile;
    private static boolean hasListedCar = false;

    private GridBagConstraints gbc;

    public SellCarMenu() {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        textFieldDimension = new Dimension(100, 30);
        imageFile = "null-car.png";

        initializeFrame();
        closeButtonListener();

        panel = createPanel();
        add(panel);
        setVisible(true);

    }


    private void initializeFrame() {
        setSize(MarketplaceGUI.WIDTH, MarketplaceGUI.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void closeButtonListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (MarketplaceGUI.getIsOriginalMarket()) {
                    new MarketplaceMenu(MarketplaceGUI.getOriginalMarket().getCars());
                } else {
                    new MarketplaceMenu(MarketplaceGUI.getMarketplace().getCars());
                }
            }
        });
    }

    private JPanel createPanel() {
        //panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.WEST;

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        manufacturerLabel = new JLabel("Manufacturer");
        modelLabel = new JLabel("Model");
        yearLabel = new JLabel("Year");
        speedLabel = new JLabel("Speed");
        handlingLabel = new JLabel("Handling");
        accelerationLabel = new JLabel("Acceleration");
        brakingLabel = new JLabel("Braking");
        driveTypeLabel = new JLabel("Drive Type");
        priceLabel = new JLabel("Price");
        imageLabel = new JLabel("Image");

        createTextField();

        addToPanel();

        return panel;
    }

    private void addToPanel() {
//        panel.add(manufacturerLabel);
//        panel.add(manufacturerTextField);
//        panel.add(modelLabel);
//        panel.add(yearLabel);
//        panel.add(speedLabel);
//        panel.add(handlingLabel);
//        panel.add(accelerationLabel);
//        panel.add(brakingLabel);
//        panel.add(driveTypeLabel);
//        panel.add(priceLabel);
//        panel.add(imageLabel);


        manufacturerTextField = addLabelAndTextField(panel, gbc, "Manufacturer: ", manufacturerTextField);
        modelTextField = addLabelAndTextField(panel, gbc, "Model: ", modelTextField);
        yearTextField = addLabelAndTextField(panel, gbc, "Year: ", yearTextField);
        speedTextField = addLabelAndTextField(panel, gbc, "Speed: ", speedTextField);
        handlingTextField = addLabelAndTextField(panel, gbc, "Handling: ", handlingTextField);
        accelerationTextField = addLabelAndTextField(panel, gbc, "Acceleration: ", accelerationTextField);
        brakingTextField = addLabelAndTextField(panel, gbc, "Braking: ", brakingTextField);
        driveTypeTextField = addLabelAndTextField(panel, gbc,
                "Drive Type: (AWD, FWD, or RWD)", driveTypeTextField);
        priceTextField = addLabelAndTextField(panel, gbc, "Price: ", priceTextField);
        createImagePanel(panel, gbc);
        createBottomPanel(panel, gbc);

    }

    private JTextField addLabelAndTextField(JPanel panel, GridBagConstraints gbc,
                                            String labelText, JTextField textField) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        textField = new JTextField(20);
        textField.setPreferredSize(textFieldDimension);

        gbc.gridx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);

        gbc.gridy++;

        return textField;
    }

    private void createImagePanel(JPanel panel, GridBagConstraints gbc) {
        JLabel label = new JLabel("Image: ");
        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        imageButton = new JButton("Upload image");

        gbc.gridx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(imageButton, gbc);

        gbc.gridy++;
    }

    private void createBottomPanel(JPanel panel, GridBagConstraints gbc) {
        //bottomPanel = new JPanel(new GridLayout());
        //bottomPanel.add(createListCarButton());
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(createListCarButton(), gbc);

        gbc.gridy++;
    }

    private JButton createListCarButton() {
        listCarButton = new JButton("List car");
        listCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car = new Car(manufacturerTextField.getText(), modelTextField.getText(),
                        Integer.parseInt(yearTextField.getText()), Double.parseDouble(speedTextField.getText()),
                        Double.parseDouble(handlingTextField.getText()),
                        Double.parseDouble(accelerationTextField.getText()),
                        Double.parseDouble(brakingTextField.getText()), DriveType.valueOf(driveTypeTextField.getText()),
                        Integer.parseInt(priceTextField.getText()), imageFile);

                if (MarketplaceGUI.getIsOriginalMarket()) {
                    MarketplaceGUI.getOriginalMarket().addCar(car);
                    dispose();
                    new MarketplaceMenu(MarketplaceGUI.getOriginalMarket().getCars());
                } else {
                    MarketplaceGUI.getMarketplace().addCar(car);
                    dispose();
                    new MarketplaceMenu(MarketplaceGUI.getMarketplace().getCars());
                }
                hasListedCar = true;

            }
        });
        return listCarButton;
    }

    private void createTextField() {
        manufacturerTextField = new JTextField();
        modelTextField = new JTextField();
        yearTextField = new JTextField();
        speedTextField = new JTextField();
        handlingTextField = new JTextField();
        accelerationTextField = new JTextField();
        brakingTextField = new JTextField();
        driveTypeTextField = new JTextField();
        priceTextField = new JTextField();
    }

    public static boolean getHasListedCar() {
        return hasListedCar;
    }


}
