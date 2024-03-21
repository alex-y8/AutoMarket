package ui;

import model.cars.Car;

import javax.swing.*;
import java.awt.*;
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

    private final DecimalFormat df = new DecimalFormat("#,###.##");


    // EFFECTS: construct the menu for the car list
    public CarListMenu() {
        carListPanel = new JPanel();
        //carListPanel.setLayout(new BorderLayout());
        carListPanel.setLayout(new GridLayout(0, 2, 0, 0));
        carListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
            System.out.println("selected");
        } else {
            unselect();
            System.out.println("unselected");
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
        carSpeed.setText(String.valueOf(car.getSpeed()));
        carHandling.setText(String.valueOf(car.getHandling()));
        carAcceleration.setText(String.valueOf(car.getAcceleration()));
        carBraking.setText(String.valueOf(car.getBraking()));
        carDriveType.setText(String.valueOf(car.getDriveType()));
        carImage.setPreferredSize(new Dimension(150, 150));
        String imageFile = "src/images/" + car.getImage();
        ImageIcon image = new ImageIcon(imageFile);
        carImage.setIcon(image);
    }

}
