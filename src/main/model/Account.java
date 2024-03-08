package model;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;
import org.json.JSONObject;
import persistence.Writeable;

// Represents an account with a cash balance in dollars ($)
public class Account implements Writeable {

    private double balance;

    // Constructs a new account with an initial balance dollars
    public Account(double initialBalance) {
        if (initialBalance > 0) {
            balance = initialBalance;
        } else {
            balance = 0;
        }
    }

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


    // getter
    public double getBalance() {
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: sets the account's balance to the given amount
    public void setBalance(double amount) throws IllegalAccountBalanceException {
        if (amount >= 0) {
            this.balance = amount;
        } else {
            throw new IllegalAccountBalanceException();
        }

    }

    // EFFECTS: creates a JSON object with the given fields
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        return json;
    }

}
