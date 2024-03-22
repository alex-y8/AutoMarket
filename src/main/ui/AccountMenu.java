package ui;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccountMenu extends AbstractMenu {

    private static final int TEXT_FIELD_FRAME_HEIGHT = 200;
    private static final int TEXT_FIELD_FRAME_WIDTH = 600;

    private JPanel balancePanel;

    private JButton setBalanceButton;
    private JButton button;

    private JTextField textField;

    private JLabel textLabel;

    private JFrame textFieldFrame;

    public AccountMenu(List<Car> carList) {
        super(carList);
        setBalanceButtonListener();
    }

    // EFFECTS: don't need upper panel for account menu
    @Override
    protected JPanel createUpperPanel() {
        return new JPanel();
    }

    @Override
    // EFFECTS: creates the buttons for the account menu
    protected JPanel createMainPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        setBalanceButton = new JButton("Set balance");
        buttonPanel.add(setBalanceButton);
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

    // EFFECTS: initializes the input text field
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
                } catch (NumberFormatException exception) {
                    textLabel.setText("Please enter a number");
                } catch (IllegalAccountBalanceException ex) {
                    textLabel.setText("Please enter a positive number");
                }

            }
        });
    }

    private JTextField createTextField() {
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(TEXT_FIELD_FRAME_WIDTH - 30,
                TEXT_FIELD_FRAME_HEIGHT - 150));
        textField.setFont(new Font("", Font.PLAIN, 24));
        return textField;
    }
}
