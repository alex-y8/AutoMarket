package model;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account1;
    private Account account2;
    private Car car1;
    private Car car2;
    private Car car3;

    @BeforeEach
    public void runBefore() {
        account1 = new Account(0);
        account2 = new Account(10000);
        car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 242000);
        car2 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 39120);
        car3 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 584923);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, account1.getBalance());
    }

    @Test
    public void testIncreaseBalance() {
        assertEquals(0, account1.getBalance());
        account1.increaseBalance();
        assertEquals(10000, account1.getBalance());
        account1.increaseBalance();
        assertEquals(20000, account1.getBalance());
        account1.increaseBalance();
        assertEquals(30000, account1.getBalance());
    }

    @Test
    public void testBoughtCarOnce() {
        try {
            account1.setBalance(250000);
        } catch (IllegalAccountBalanceException e) {
            fail("Unexpected IllegalAccountBalanceException");
        }
        account1.boughtCar(car1);
        assertEquals(250000-242000, account1.getBalance());
    }

    @Test
    public void testBoughtCarMultiple() {
        try {
            account1.setBalance(1000000);
        } catch (IllegalAccountBalanceException e) {
            fail("Unexpected IllegalAccountBalanceException");
        }
        account1.boughtCar(car1);
        assertEquals(1000000-242000, account1.getBalance());
        account1.boughtCar(car2);
        assertEquals(758000-39120, account1.getBalance());
        account1.boughtCar(car3);
        assertEquals(718880-584923, account1.getBalance());
    }

    @Test
    public void testBoughtCarInsufficientFunds() {
        account2.boughtCar(car2);
        assertFalse(account2.boughtCar(car2));
        assertEquals(10000, account2.getBalance());
    }

    @Test
    public void testBoughtCarIllegalAccountBalanceException() {
        try {
            account1.setBalance(-1);
            fail("IllegalAccountBalanceException was not thrown.");
        } catch (IllegalAccountBalanceException e) {

        }
        assertEquals(0, account1.getBalance());
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
