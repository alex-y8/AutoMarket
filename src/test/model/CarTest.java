package model;

import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {
    private Car car1;
    private Car car2;
    private Car car3;

    @BeforeEach
    public void runBefore() {
        car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 242000, null);
        car2 = new Car("Nissan", "GT-R", 2017, 7.9,
                7.2, 9.6, 7.6, DriveType.AWD, 132000, null);
        car3 = new Car("Nissan", "GT-R", 2017, 7.9,
                7.2, 9.6, 7.6, DriveType.FWD, 132000, null);
    }

    @Test
    public void testConstructor() {
        assertEquals("Audi", car1.getManufacturer());
        assertEquals("R8", car1.getModel());
        assertEquals(2016, car1.getYear());
        assertEquals(8.2, car1.getSpeed());
        assertEquals(7.6, car1.getHandling());
        assertEquals(9.0, car1.getAcceleration());
        assertEquals(9.2, car1.getBraking());
        assertEquals(DriveType.RWD, car1.getDriveType());
        assertEquals(242000, car1.getPrice());
        assertEquals(null, car1.getImage());

        assertEquals(DriveType.AWD, car2.getDriveType());
        assertEquals(DriveType.FWD, car3.getDriveType());
    }

    @Test
    public void testToString() {
        assertEquals( "2016" + " "
                + "Audi"
                + " " + "R8" + " $242,000"
                + "\n" + "Speed: "
                + 8.2 + "\n" + "Handling: "
                + 7.6 + "\n" + "Acceleration: "
                + 9.0 + "\n" + "Braking: "
                + 9.2 + "\n" + "Drive type: "
                + "RWD" + "\n", car1.toString());
    }
}
