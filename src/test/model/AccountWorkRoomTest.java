package model;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;
import model.cars.DriveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountWorkRoomTest {

    private AccountWorkRoom accountWorkRoom;
    private Account a;
    private Car car1;
    private Car car2;

    @BeforeEach
    public void runBefore() {
        accountWorkRoom = new AccountWorkRoom();
        a = new Account(0);
        car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 242000);
        car2 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 39120);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, accountWorkRoom.getBalance());
        assertEquals(1, accountWorkRoom.getAccount().size());
    }

    @Test
    public void testSetBalanceSuccess() {
        try {
            accountWorkRoom.setBalance(500);
            assertEquals(500, accountWorkRoom.getBalance());
            accountWorkRoom.setBalance(5000);
            assertEquals(5000, accountWorkRoom.getBalance());
        } catch (IllegalAccountBalanceException e) {
            fail("IllegalAccountBalanceException should not have been thrown");
        }
    }

    @Test
    public void testSetBalanceFail() {
        try {
            accountWorkRoom.setBalance(-1);
            fail("IllegalAccountBalanceException should have been thrown");
        } catch (IllegalAccountBalanceException e) {
            // pass
        }
        assertEquals(0, accountWorkRoom.getBalance());
    }

    @Test
    public void testAddAccount() {
        accountWorkRoom.addAccount(a);
        assertEquals(a, accountWorkRoom.getAccount().get(0));
    }

    @Test
    public void testBoughtCarOnce() {
        try {
            accountWorkRoom.setBalance(250000);
        } catch (IllegalAccountBalanceException e) {
            fail("Unexpected IllegalAccountBalanceException");
        }
        accountWorkRoom.boughtCar(car1);
        assertEquals(250000-242000, accountWorkRoom.getBalance());
    }

    @Test
    public void testBoughtCarMultiple() {
        try {
            accountWorkRoom.setBalance(1000000);
        } catch (IllegalAccountBalanceException e) {
            fail("Unexpected IllegalAccountBalanceException");
        }
        accountWorkRoom.boughtCar(car1);
        assertEquals(1000000-242000, accountWorkRoom.getBalance());
        accountWorkRoom.boughtCar(car2);
        assertEquals(758000-39120, accountWorkRoom.getBalance());
    }

    @Test
    public void testBoughtCarInsufficientFunds() {
        try {
            accountWorkRoom.setBalance(1);
        } catch (IllegalAccountBalanceException e) {
            fail("Unexpected IllegalAccountBalanceException");
        }
        accountWorkRoom.boughtCar(car2);
        assertFalse(accountWorkRoom.boughtCar(car2));
        assertEquals(1, accountWorkRoom.getBalance());
    }

    @Test
    public void testBoughtCarIllegalAccountBalanceException() {
        try {
            accountWorkRoom.setBalance(-1);
            fail("IllegalAccountBalanceException was not thrown.");
        } catch (IllegalAccountBalanceException e) {
            // pass
        }
        assertEquals(0, accountWorkRoom.getBalance());
    }

}
