package ui;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Creates the account menu
public class AccountMenu extends AbstractMenu {

    private static final int TEXT_FIELD_FRAME_HEIGHT = 200;
    private static final int TEXT_FIELD_FRAME_WIDTH = 500;

    private JPanel balancePanel;

    private JButton setBalanceButton;
    private JButton button;

    private JTextField textField;

    private JLabel textLabel;
    private JLabel balanceLabel;

    private JFrame textFieldFrame;

    // EFFECTS: constructs a new AccountMenu
    public AccountMenu(List<Car> carList) {
        super(carList);
        setBalanceButtonListener();
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons for the upper panel
    @Override
    protected JPanel createUpperPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        setBalanceButton = new JButton("Set balance");
        buttonPanel.add(setBalanceButton);
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the main panel for the account menu
    @Override
    protected JPanel createMainPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        balanceLabel = new JLabel("Your account balance is: $"
                + df.format(marketplace.getUserAccount().getBalance()));
        balanceLabel.setFont(new Font("", Font.PLAIN, 25));
        buttonPanel.add(balanceLabel);
        return buttonPanel;
    }

    // EFFECTS: button listener for the set account balance button, sets the account balance when clicked
    private void setBalanceButtonListener() {
        setBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeTextField();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes the input text field in the new window
    private void initializeTextField() {
        textFieldFrame = new JFrame();
        textFieldFrame.setSize(TEXT_FIELD_FRAME_WIDTH, TEXT_FIELD_FRAME_HEIGHT);
        textFieldFrame.setResizable(false);
        textFieldFrame.setLocationRelativeTo(null);
        textFieldFrame.setLayout(new BorderLayout());

        balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout());

        textField = createTextField();
        balancePanel.add(textField);

        textLabel = new JLabel();
        textLabel.setText("Set your account balance to any positive number: ");
        balancePanel.add(textLabel);

        button = new JButton("Set");
        balancePanel.add(button);

        textFieldFrame.add(balancePanel, BorderLayout.CENTER);
        textFieldFrame.setVisible(true);

        buttonListener();

    }

    // MODIFIES: userAccount
    // EFFECTS: sets the account balance to the given user input
    private void buttonListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balanceToSet = 0.0;

                try {
                    String text = textField.getText();
                    balanceToSet = Double.parseDouble(text);
                    marketplace.getUserAccount().setBalance(balanceToSet);
                    textLabel.setText("Your account balance has been set to: $"
                            + df.format(marketplace.getUserAccount().getBalance()));
                    balanceLabel.setText("Your account balance is: $"
                            + df.format(marketplace.getUserAccount().getBalance()));
                } catch (NumberFormatException exception) {
                    textLabel.setText("Please enter a number");
                } catch (IllegalAccountBalanceException ex) {
                    textLabel.setText("Please enter a positive number");
                }
            }
        });
    }

    // EFFECTS: creates the JTextField for the account menu
    private JTextField createTextField() {
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(TEXT_FIELD_FRAME_WIDTH - 200,
                TEXT_FIELD_FRAME_HEIGHT - 150));
        textField.setFont(new Font("", Font.PLAIN, 24));

        return textField;
    }
}
