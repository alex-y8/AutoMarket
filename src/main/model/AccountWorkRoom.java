package model;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// Represents a workroom having an account balance
// Accounts list will always be non-empty
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class AccountWorkRoom implements Writeable {

    private double balance;

    private List<Account> account;
    private Account a1;

    private final DecimalFormat df = new DecimalFormat("#,###.##");

    // EFFECTS: constructs workroom with an account with $0
    public AccountWorkRoom() {
        account = new ArrayList<>();
        a1 = new Account(0);
        account.add(a1);
    }

    // MODIFIES: this
    // EFFECTS: sets the account's balance to the given amount
    public void setBalance(double amount) throws IllegalAccountBalanceException {
        if (amount >= 0) {
            account.get(0).setBalance(amount);
            EventLog.getInstance().logEvent(new Event("Set account balance to: "
                    + df.format(account.get(0).getBalance())));
        } else {
            throw new IllegalAccountBalanceException();
        }

    }

    public double getBalance() {
        return account.get(0).getBalance();
    }

    // MODIFIES: this
    // EFFECTS: adds account to this workroom
    public void addAccount(Account a) {
        account.set(0, a);
    }

    // EFFECTS: creates a JSON object with the given fields
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("account", accountToJson());
        return json;
    }

    // EFFECTS: returns account in this workroom as a JSON array
    private JSONArray accountToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a : account) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

//    // MODIFIES: this
//    // EFFECTS: increases the current account balance by 10000
//    public void increaseBalance() {
//        balance += 10000;
//    }

    // MODIFIES: this
    // EFFECTS: if balance is enough to buy car, subtracts the car's price from the account's balance and return true
    // return false otherwise
    public boolean boughtCar(Car c) {
        if (getBalance() >= c.getPrice()) {
            setBalanceBoughtCar(getBalance() - c.getPrice());
            EventLog.getInstance().logEvent(new Event("Bought car and added to garage: " + c.getYear() + " "
                    + c.getManufacturer() + " " + c.getModel()));
            return true;
        }
        return false;
    }

    public void setBalanceBoughtCar(double balance) {
        account.get(0).setBalanceNoException(balance);
    }

    // getter
    public List<Account> getAccount() {
        return account;
    }

}
