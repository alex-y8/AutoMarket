package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

// Creates the list of cars to be displayed in the marketplace and garage
public class CarListMenu implements ListCellRenderer<Car> {

    private JPanel carListPanel;
    private JPanel textPanel;

    private JLabel carManufacturer;
    private JLabel carModel;
    private JLabel carYear;
    private JLabel carPrice;
    private JLabel carSpeed;
    private JLabel carHandling;
    private JLabel carAcceleration;
    private JLabel carBraking;
    private JLabel carDriveType;
    private JLabel carImage;

    private JList<? extends Car> carList;
    private ArrayList<Car> selectedCars;
    private Car car;

    private static final double IMAGE_WIDTH = 500;
    private static final double IMAGE_HEIGHT = IMAGE_WIDTH / 1.7;

    private final DecimalFormat df = new DecimalFormat("#,###.##");

    // EFFECTS: construct the menu for the car list
    public CarListMenu() {
        selectedCars = new ArrayList<>();
        carListPanel = new JPanel();
        carListPanel.setLayout(new BorderLayout());
        carListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        carListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setUpLabels();
        setUpPanels();

        carListPanel.add(textPanel, BorderLayout.CENTER);
        carListPanel.add(carImage, BorderLayout.WEST);
    }

    public ArrayList<Car> getSelectedCarList() {
        return selectedCars;
    }

    // MODIFIES: this
    // EFFECTS: renders the list of cars and overrides behaviour when a car is selected/unselected
    @Override
    public Component getListCellRendererComponent(JList<? extends Car> carList, Car car,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        this.carList = carList;
        this.car = car;
        setUpInfo();

        if (isSelected) {
            select();
        } else {
            unselect();
        }

        return carListPanel;
    }

    // MODIFIES: this
    // EFFECTS: highlights the background of the selected car, adds selected car(s) to the selected list
    private void select() {
        carListPanel.setBackground(carList.getSelectionBackground());
        textPanel.setBackground(carList.getSelectionBackground());
        carManufacturer.setBackground(carList.getSelectionBackground());
        carModel.setBackground(carList.getSelectionBackground());
        carYear.setBackground(carList.getSelectionBackground());
        carPrice.setBackground(carList.getSelectionBackground());
        carSpeed.setBackground(carList.getSelectionBackground());
        carHandling.setBackground(carList.getSelectionBackground());
        carAcceleration.setBackground(carList.getSelectionBackground());
        carBraking.setBackground(carList.getSelectionBackground());
        carDriveType.setBackground(carList.getSelectionBackground());
        carImage.setBackground(carList.getSelectionBackground());
        if (!selectedCars.contains(car)) {
            selectedCars.add(car);
        }

    }

    // MODIFIES: this
    // EFFECTS: unhighlights the background of the selected car, removes selected car(s) from the selected list
    private void unselect() {
        carListPanel.setBackground(carList.getBackground());
        textPanel.setBackground(carList.getBackground());
        carManufacturer.setBackground(carList.getBackground());
        carModel.setBackground(carList.getBackground());
        carYear.setBackground(carList.getBackground());
        carPrice.setBackground(carList.getBackground());
        carSpeed.setBackground(carList.getBackground());
        carHandling.setBackground(carList.getBackground());
        carAcceleration.setBackground(carList.getBackground());
        carBraking.setBackground(carList.getBackground());
        carDriveType.setBackground(carList.getBackground());
        carImage.setBackground(carList.getBackground());
        selectedCars.remove(car);

    }

    // MODIFIES: this
    // EFFECTS: initializes the JLabels and sets the font size
    private void setUpLabels() {
        carManufacturer = new JLabel();
        carModel = new JLabel();
        carPrice = new JLabel();
        carYear = new JLabel();
        carSpeed = new JLabel();
        carHandling = new JLabel();
        carAcceleration = new JLabel();
        carBraking = new JLabel();
        carDriveType = new JLabel();
        carImage = new JLabel();

        carManufacturer.setFont(new Font("Urbanist", Font.BOLD, 30));
        carModel.setFont(new Font("Urbanist", Font.BOLD, 25));
        carYear.setFont(new Font("", Font.PLAIN, 18));
        carPrice.setFont(new Font("", Font.PLAIN, 18));
        carSpeed.setFont(new Font("", Font.PLAIN, 18));
        carHandling.setFont(new Font("", Font.PLAIN, 18));
        carAcceleration.setFont(new Font("", Font.PLAIN, 18));
        carBraking.setFont(new Font("", Font.PLAIN, 18));
        carDriveType.setFont(new Font("", Font.PLAIN, 18));
    }

    // MODIFIES: this
    // EFFECTS: initializes the textPanel and adds all the JLabels to itself
    private void setUpPanels() {
        textPanel = new JPanel(new GridLayout(0, 1, 0, -10));
        textPanel.add(carManufacturer);
        textPanel.add(carModel);
        textPanel.add(carYear);
        textPanel.add(carPrice);
        textPanel.add(carSpeed);
        textPanel.add(carHandling);
        textPanel.add(carAcceleration);
        textPanel.add(carBraking);
        textPanel.add(carDriveType);
    }

    // MODIFIES: this
    // EFFECTS: sets the JLabel text to the fields of the car and handles image selection
    private void setUpInfo() {
        carManufacturer.setText(car.getManufacturer());
        carModel.setText(car.getModel());
        carYear.setText("Year: " + car.getYear());
        carPrice.setText("Price: $" + df.format(car.getPrice()));
        carSpeed.setText("Speed: " + (car.getSpeed()));
        carHandling.setText("Handling: " + (car.getHandling()));
        carAcceleration.setText("Acceleration: " + (car.getAcceleration()));
        carBraking.setText("Braking: " + (car.getBraking()));
        carDriveType.setText("Drive Type: " + (car.getDriveType()));
        carImage.setPreferredSize(new Dimension((int) IMAGE_WIDTH, (int) IMAGE_HEIGHT));
        String imageFile;
        if (car.getImage() == null) {
            imageFile = "src/images/null-car.png";
        } else if (car.getImage().length() > 22) {
            imageFile = car.getImage();
        } else {
            imageFile = "src/images/" + car.getImage();
        }
        ImageIcon image = new ImageIcon(imageFile);
        Image scaled = image.getImage().getScaledInstance((int) IMAGE_WIDTH, (int) IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(scaled);
        carImage.setIcon(scaledImage);
    }

}
