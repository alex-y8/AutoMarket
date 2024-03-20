package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Graphical interface for the marketplace
public class MarketplaceGUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 1000;

    private JPanel mainMenu;
    private JPanel marketplaceMenu;

    // EFFECTS: constructs the marketplace gui
    public MarketplaceGUI() {
        initializeGraphics();

    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this Marketplace will operate, creates the menu buttons
    private void initializeGraphics() {
        initializeMainMenu();
        initializeMarketplaceMenu();


        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);





        add(mainMenu);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu's borders and buttons
    private void initializeMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new BorderLayout());
        mainMenu.add(createMarketplaceButton());
        mainMenu.add(createGarageButton());
        mainMenu.add(createAccountButton());
        mainMenu.add(quitButton());
        mainMenu.setBounds(50, 100, 300, 300);
        mainMenu.setLayout(new GridLayout(0, 1, 0, 10));
        mainMenu.setBorder(BorderFactory.createEmptyBorder(700, 20, 20, 20));
    }

    // MODIFIES: this
    // EFFECTS: sets up the marketplace's borders and buttons
    private void initializeMarketplaceMenu() {
        marketplaceMenu = new JPanel();
        marketplaceMenu.setLayout(new BorderLayout());

        marketplaceMenu.setBounds(50, 100, 300, 300);
        marketplaceMenu.setLayout(new GridLayout(0, 1, 0, 10));
        marketplaceMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        marketplaceMenu.add(scroll);

        marketplaceMenu.setVisible(false);
        loadCars();
    }

    // MODIFIES: this
    // EFFECTS:  creates the marketplace button for the menu
    private JButton createMarketplaceButton() {
        JButton marketplaceButton = new JButton("Marketplace");
        marketplaceButton.setFocusable(false);
        marketplaceButton.setFont(new Font("/", Font.PLAIN, 24));

        marketplaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bring up marketplace window with cars
                displayMarketplaceCars();
            }
        });

        return marketplaceButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the garage button for the menu
    private JButton createGarageButton() {
        JButton garageButton = new JButton("Garage");
        garageButton.setFocusable(false);
        garageButton.setFont(new Font("p", Font.PLAIN, 24));
        //garageButton.setBounds(200, 200, 10, 10);

        garageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bring up marketplace window with garage
            }
        });

        return garageButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the account button for the menu
    private JButton createAccountButton() {
        JButton accountButton = new JButton("Account");
        accountButton.setFocusable(false);
        accountButton.setFont(new Font("p", Font.PLAIN, 24));

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bring up marketplace window with account info
            }
        });

        return accountButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the quit button for the menu
    private JButton quitButton() {
        JButton accountButton = new JButton("Quit");
        accountButton.setFocusable(false);
        accountButton.setFont(new Font("p", Font.PLAIN, 24));

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processQuit();
            }
        });

        return accountButton;

    }

    // MODIFIES: this
    // EFFECTS: loads the cars listed for sale onto the marketplace menu
    private void loadCars() {

    }

    // MODIFIES: this
    // EFFECTS: displays the cars listed for sale on the marketplace
    private void displayMarketplaceCars() {
        add(marketplaceMenu);
        marketplaceMenu.setVisible(true);
        mainMenu.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: prompt user to save data, then quits the program
    private void processQuit() {
        String[] options = {"Save and quit", "Quit without saving"};

        int saveData = JOptionPane.showOptionDialog(getContentPane(), "Data is unsaved", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (saveData == 1) {
            //save data
            System.exit(0);
        } else {
            System.exit(0);
        }
    }


}
