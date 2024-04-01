package ui;

import model.cars.Car;
import model.cars.DriveType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

// Menu for listing a car onto the marketplace
public class SellCarMenu extends JFrame {

    private JPanel panel;

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

    // EFFECTS: constructs the menu for when the user wants to list their own car
    public SellCarMenu() {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        textFieldDimension = new Dimension(100, 30);
        imageFile = "null-car.png";

        initializeFrame();

        panel = createPanel();
        add(panel);

        closeButtonListener();
        imageButtonListener();
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: initializes the window
    private void initializeFrame() {
        setSize(MarketplaceGUI.WIDTH, MarketplaceGUI.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // EFFECTS: button listener for the "X" button in the top right corner of the window, refreshes the menu
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

    // MODIFIES: this
    // EFFECTS: creates the panel and adds all the labels to the panel
    private JPanel createPanel() {
        panel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.WEST;

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createTextField();

        addToPanel();

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: adds the text fields to the panel
    private void addToPanel() {
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

    // MODIFIES: this
    // EFFECTS: sets up the gridbaglayout constraints and adds the components to the panel
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

    // MODIFIES: this
    // EFFECTS: sets up the grid bag layout constraints and adds the components to the image panel
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

    // MODIFIES: this
    // EFFECTS: creates the lower panel and adds the components to it
    private void createBottomPanel(JPanel panel, GridBagConstraints gbc) {
        //bottomPanel = new JPanel(new GridLayout());
        //bottomPanel.add(createListCarButton());
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(createListCarButton(), gbc);
        JLabel messageLabel = new JLabel();

        //panel.add()

        gbc.gridy++;
    }

    // MODIFIES: this
    // EFFECTS: initializes the JTextFields
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

    // MODIFIES: this
    // EFFECTS: creates the list car button
    private JButton createListCarButton() {
        listCarButton = new JButton("List car");
        listCarButton.addActionListener(new ActionListener() {
            // EFFECTS: adds the listed car to the market
            @Override
            public void actionPerformed(ActionEvent e) {
                createCar();

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

    // EFFECTS: creates the car with the given user inputs
    private void createCar() {
        car = new Car(manufacturerTextField.getText(), modelTextField.getText(),
                Integer.parseInt(yearTextField.getText()), Double.parseDouble(speedTextField.getText()),
                Double.parseDouble(handlingTextField.getText()),
                Double.parseDouble(accelerationTextField.getText()),
                Double.parseDouble(brakingTextField.getText()),
                DriveType.valueOf(driveTypeTextField.getText().toUpperCase()),
                Integer.parseInt(priceTextField.getText()), imageFile);
    }

    // EFFECTS: button listener for the upload image button
    private void imageButtonListener() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files (jpg, jpeg, or png)",
                "jpg", "jpeg", "png");
        imageButton.addActionListener(new ActionListener() {
            // EFFECTS: opens a file chooser and allows the user to upload their own image
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imageFile = selectedFile.getAbsolutePath();
                }
            }
        });
    }

    // EFFECTS: returns true if the user has listed a car onto the market
    public static boolean getHasListedCar() {
        return hasListedCar;
    }


}
