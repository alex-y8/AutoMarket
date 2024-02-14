package model;

import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GarageTest {
    private Garage garage;
    private Car car1;
    private Car car2;
    private Car car3;

    @BeforeEach
    public void runBefore() {
        garage = new Garage();
        car1 = new Car("Nissan", "GT-R", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 242000);
        car2 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 39120);
        car3 = new Car("Toyota", "Corolla", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 584923);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, garage.getGarageSize());
    }

    @Test
    public void testCarsInGarageOneCar() {
        garage.addCar(car1);
        assertEquals("Nissan GT-R" + "\n", garage.carsInGarage());
    }

    @Test
    public void testCarsInGarageMultipleCars() {
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        assertEquals("Nissan GT-R" + "\n"
                + "Audi R8" + "\n" +
                "Toyota Corolla" + "\n", garage.carsInGarage());
    }

    @Test
    public void testAddCarOne() {
        garage.addCar(car1);
        assertTrue(garage.containsCar(car1));
        assertEquals(car1, garage.getGarage().get(0));
    }

    @Test
    public void testAddCarMultiple() {
        garage.addCar(car3);
        garage.addCar(car1);
        garage.addCar(car2);
        assertTrue(garage.containsCar(car1));
        assertTrue(garage.containsCar(car2));
        assertTrue(garage.containsCar(car3));
        assertEquals(car3, garage.getGarage().get(0));
        assertEquals(car1, garage.getGarage().get(1));
        assertEquals(car2, garage.getGarage().get(2));
    }

    @Test
    public void testAddCarDuplicate() {
        garage.addCar(car1);
        garage.addCar(car1);
        assertTrue(garage.containsCar(car1));
        assertEquals(car1, garage.getGarage().get(0));
        assertEquals(car1, garage.getGarage().get(1));
    }

    @Test
    public void testRemoveCarOne() {
        garage.addCar(car1);
        assertTrue(garage.containsCar(car1));
        garage.removeCar(car1);
        assertFalse(garage.containsCar(car1));
    }

    @Test
    public void testRemoveCarMultiple() {
        garage.addCar(car1);
        assertTrue(garage.containsCar(car1));
        garage.addCar(car3);
        assertTrue(garage.containsCar(car3));
        garage.addCar(car2);
        assertTrue(garage.containsCar(car2));
        assertEquals(car1, garage.getGarage().get(0));
        assertEquals(car3, garage.getGarage().get(1));
        assertEquals(car2, garage.getGarage().get(2));
        garage.removeCar(car1);
        assertFalse(garage.containsCar(car1));
        assertEquals(car3, garage.getGarage().get(0));
        assertEquals(car2, garage.getGarage().get(1));
        garage.removeCar(car2);
        assertFalse(garage.containsCar(car2));
        assertEquals(car3, garage.getGarage().get(0));
        garage.removeCar(car3);
        assertFalse(garage.containsCar(car3));
    }

    @Test
    public void testRemoveCarDuplicate() {
        garage.addCar(car1);
        garage.addCar(car1);
        assertTrue(garage.containsCar(car1));
        assertEquals(car1, garage.getGarage().get(1));
        assertEquals(car1, garage.getGarage().get(1));
        garage.removeCar(car1);
        assertTrue(garage.containsCar(car1));
        assertEquals(car1, garage.getGarage().get(0));
        garage.removeCar(car1);
        assertFalse(garage.containsCar(car1));
    }

    @Test
    public void testGetGarageSize() {
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        assertEquals(3, garage.getGarageSize());
    }
}
