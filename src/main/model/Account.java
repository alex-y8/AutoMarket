package model;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;

// Represents an account with a cash balance in dollars
public class Account {

    private int balance;

    // Constructs a new account with 0 dollars
    public Account(int initialBalance) {
        if (initialBalance > 0) {
            balance = initialBalance;
        } else {
            balance = 0;
        }
    }

    // REQUIRES: balance >= 0
    // MODIFIES: this
    // EFFECTS: increases the current account balance by 10000
    public void increaseBalance() {
        balance += 10000;
    }

    // MODIFIES: this
    // EFFECTS: if balance is enough to buy car, subtracts the car's price from the account's balance and return true
    // return false otherwise
    public boolean boughtCar(Car c) {
        if (balance >= c.getPrice()) {
            balance -= c.getPrice();
            return true;
        }
        return false;
    }


    // getters
    public int getBalance() {
        return balance;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets the account's balance to the given amount
    public void setBalance(int amount) throws IllegalAccountBalanceException {
        if (amount >= 0) {
            this.balance = amount;
        } else {
            throw new IllegalAccountBalanceException();
        }

    }

}
