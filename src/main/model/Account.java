package model;

import model.cars.Car;

// Represents an account with a cash balance in dollars
public class Account {

    private int balance;

    // Constructs a new account with 0 dollars
    public Account() {
        this.balance = 0;
    }

    // REQUIRES: balance >= 0
    // MODIFIES: this
    // EFFECTS: increases the current account balance by 10000
    public void increaseBalance() {

    }

    // MODIFIES: this
    // EFFECTS: subtracts the car's price from the account's balance
    public void boughtCar(Car c) {

    }


    // getters
    public int getBalance() {
        return balance;
    }

}
