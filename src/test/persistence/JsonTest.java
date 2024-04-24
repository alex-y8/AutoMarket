package persistence;

import model.Account;
import model.cars.Car;
import model.cars.DriveType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCar(String manufacturer, String model, int year, double speed, double handling,
                            double acceleration, double braking, DriveType driveType, int price, String image, Car car) {
        assertEquals(manufacturer, car.getManufacturer());
        assertEquals(model, car.getModel());
        assertEquals(year, car.getYear());
        assertEquals(speed, car.getSpeed());
        assertEquals(handling, car.getHandling());
        assertEquals(acceleration, car.getAcceleration());
        assertEquals(braking, car.getBraking());
        assertEquals(driveType, car.getDriveType());
        assertEquals(price, car.getPrice());
        assertEquals(image, car.getImage());
    }

    protected void checkAccount(double balance, Account a) {
        assertEquals(balance, a.getBalance());
    }
}
