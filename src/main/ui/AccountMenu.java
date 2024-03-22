package ui;

import exceptions.IllegalAccountBalanceException;
import model.cars.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class AccountMenu extends JFrame {

    private JPanel accountPanel;
    private JPanel balancePanel;

    private JButton setBalanceButton;
    private JButton button;

    private JTextField textField;

    private JLabel textLabel;

    private JFrame textFieldFrame;

    private static final int TEXT_FIELD_FRAME_HEIGHT = 200;
    private static final int TEXT_FIELD_FRAME_WIDTH = 600;

    private Marketplace marketplace = new Marketplace();
    private final DecimalFormat df = new DecimalFormat("#,###.##");

    public AccountMenu() {
        accountPanel = new JPanel();
        createFrame();
        accountPanel.setLayout(new BorderLayout());
        accountPanel.add(buttons(), BorderLayout.CENTER);
        add(accountPanel);

        setBalanceButtonListener();


        setVisible(true);
    }

    // EFFECTS: creates the account menu window frame
    private void createFrame() {
        setSize(MarketplaceGUI.WIDTH, MarketplaceGUI.HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // EFFECTS: creates the buttons for the account menu
    private JPanel buttons() {
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
                balanceTextField();
                //userAccount.setBalance(input.nextDouble());
            }
        });
    }

    private void balanceTextField() {
        initializeTextField();


    }

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
                    //marketplace.getUserAccount().getAccount().get(0).setBalance(balanceToSet);
//                    textLabel.setText("Your account balance has been set to: $"
//                            + df.format(marketplace.getUserAccount().getAccount().get(0).getBalance()));
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
