package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class CarListMenu implements ListCellRenderer<Car> {

    private JPanel carListPanel;
    private JPanel textPanel;

    private JLabel carManufacturer;
    private JLabel carModel;
    private JLabel carPrice;
    private JLabel carSpeed;
    private JLabel carHandling;
    private JLabel carAcceleration;
    private JLabel carBraking;
    private JLabel carDriveType;
    private JLabel carImage;

    private JList<? extends Car> carList;
    private Car car;

    private static final double IMAGE_WIDTH = 500;
    private static final double IMAGE_HEIGHT = IMAGE_WIDTH / 1.7;
    private Dimension fontSize;

    private final DecimalFormat df = new DecimalFormat("#,###.##");


    // EFFECTS: construct the menu for the car list
    public CarListMenu() {
        carListPanel = new JPanel();
        carListPanel.setLayout(new BorderLayout());
        //carListPanel.setLayout(new GridLayout(0, 2, 0, 0));
        carListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setUpLabels();
        setUpPanels();

        carListPanel.add(textPanel, BorderLayout.CENTER);
        carListPanel.add(carImage, BorderLayout.WEST);
    }

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

    private void select() {
        carListPanel.setBackground(carList.getSelectionBackground());
        textPanel.setBackground(carList.getSelectionBackground());
        carManufacturer.setBackground(carList.getSelectionBackground());
        carModel.setBackground(carList.getSelectionBackground());
        carPrice.setBackground(carList.getSelectionBackground());
        carSpeed.setBackground(carList.getSelectionBackground());
        carHandling.setBackground(carList.getSelectionBackground());
        carAcceleration.setBackground(carList.getSelectionBackground());
        carBraking.setBackground(carList.getSelectionBackground());
        carDriveType.setBackground(carList.getSelectionBackground());
        carImage.setBackground(carList.getSelectionBackground());
    }

    private void unselect() {
        carListPanel.setBackground(carList.getBackground());
        textPanel.setBackground(carList.getBackground());
        carManufacturer.setBackground(carList.getBackground());
        carModel.setBackground(carList.getBackground());
        carPrice.setBackground(carList.getBackground());
        carSpeed.setBackground(carList.getBackground());
        carHandling.setBackground(carList.getBackground());
        carAcceleration.setBackground(carList.getBackground());
        carBraking.setBackground(carList.getBackground());
        carDriveType.setBackground(carList.getBackground());
        carImage.setBackground(carList.getBackground());
    }

    private void setUpLabels() {
        carManufacturer = new JLabel();
        carModel = new JLabel();
        carPrice = new JLabel();
        carSpeed = new JLabel();
        carHandling = new JLabel();
        carAcceleration = new JLabel();
        carBraking = new JLabel();
        carDriveType = new JLabel();
        carImage = new JLabel();

        carManufacturer.setFont(new Font("Urbanist", Font.BOLD, 30));
        carModel.setFont(new Font("Urbanist", Font.BOLD, 25));
        carPrice.setFont(new Font("", Font.PLAIN, 18));
        carSpeed.setFont(new Font("", Font.PLAIN, 18));
        carHandling.setFont(new Font("", Font.PLAIN, 18));
        carAcceleration.setFont(new Font("", Font.PLAIN, 18));
        carBraking.setFont(new Font("", Font.PLAIN, 18));
        carDriveType.setFont(new Font("", Font.PLAIN, 18));
    }

    private void setUpPanels() {
        textPanel = new JPanel(new GridLayout(0, 1));
        textPanel.add(carManufacturer);
        textPanel.add(carModel);
        textPanel.add(carPrice);
        textPanel.add(carSpeed);
        textPanel.add(carHandling);
        textPanel.add(carAcceleration);
        textPanel.add(carBraking);
        textPanel.add(carDriveType);
    }

    private void setUpInfo() {
        carManufacturer.setText(car.getManufacturer());
        carModel.setText(car.getModel());
        carPrice.setText("Price: $" + df.format(car.getPrice()));
        carSpeed.setText("Speed: " + (car.getSpeed()));
        carHandling.setText("Handling: " + (car.getHandling()));
        carAcceleration.setText("Acceleration: " + (car.getAcceleration()));
        carBraking.setText("Braking: " + (car.getBraking()));
        carDriveType.setText("Drive Type: " + (car.getDriveType()));
        carImage.setPreferredSize(new Dimension((int) IMAGE_WIDTH, (int) IMAGE_HEIGHT));
        String imageFile = "src/images/" + car.getImage();
        ImageIcon image = new ImageIcon(imageFile);
        Image scaled = image.getImage().getScaledInstance((int) IMAGE_WIDTH, (int) IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(scaled);
        carImage.setIcon(scaledImage);
    }

}
