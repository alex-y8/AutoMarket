package model;

import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account account;
    private Car car1;
    private Car car2;
    private Car car3;

    @BeforeEach
    public void runBefore() {
        account = new Account();
        car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 242000);
        car2 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 39120);
        car3 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 584923);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, account.getBalance());
    }

    @Test
    public void testIncreaseBalance() {
        assertEquals(0, account.getBalance());
        account.increaseBalance();
        assertEquals(10000, account.getBalance());
        account.increaseBalance();
        assertEquals(20000, account.getBalance());
        account.increaseBalance();
        assertEquals(30000, account.getBalance());
    }

    @Test
    public void testBoughtCarOnce() {
        account.setBalance(250000);
        account.boughtCar(car1);
        assertEquals(250000-242000, account.getBalance());
    }

    @Test
    public void testBoughtCarMultiple() {
        account.setBalance(1000000);
        account.boughtCar(car1);
        assertEquals(1000000-242000, account.getBalance());
        account.boughtCar(car2);
        assertEquals(758000-39120, account.getBalance());
        account.boughtCar(car3);
        assertEquals(718790-584923, account.getBalance());
    }

    /* don't need to test getter and setter methods
    @Test
    public void testGetBalance() {
        assertEquals(0, account.getBalance());
        account.setBalance(1000000);
        assertEquals(1000000, account.getBalance());
    }

    @Test
    public void testSetBalance() {
        assertEquals(0, account.getBalance());
        account.setBalance(1000000);
        assertEquals(1000000, account.getBalance());
    }
     */

}
