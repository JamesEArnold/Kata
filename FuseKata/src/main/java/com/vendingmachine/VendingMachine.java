package com.vendingmachine;

import java.util.Scanner;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class VendingMachine {
    private int nickels = 0;
    private int dimes = 0;
    private int quarters = 0;
    private static Scanner userInput = new Scanner(System.in);
    private static boolean menuExit = false;

    static final Logger LOGGER = LoggerFactory.getLogger(VendingMachine.class);

    public void userSelection(String userSelection) {
        switch (userSelection) {
            case "dime":
                dimes++;
                break;
            case "nickel":
                nickels++;
                break;
            case "quarter":
                quarters++;
                break;
            case "cola":
                selectProductHelper(1.00f);
                break;
            case "chips":
                selectProductHelper(0.50f);
                break;
            case "candy":
                selectProductHelper(0.65f);
                break;
            default:
                LOGGER.info("INVALID SELECTION");
                System.out.println("That selection is invalid.");
                break;
        }
    }

    public int getNickels() {
        return nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public int getQuarters() {
        return quarters;
    }

    public float currentBalance() {
        float currentBalance = 0;
        currentBalance += getNickels() * 5;
        currentBalance += getDimes() * 10;
        currentBalance += getQuarters() * 25;
        return currentBalance / 100;
    }

    public void menuPrompts(int promptNumber) {
        switch (promptNumber) {
            case 0:
                System.out.println("Products: Cola $1.00, Candy $0.65, Chips $0.50");
                break;
            case 1:
                System.out.println("INSERT COIN");
                break;
        }
    }

    public void selectProductHelper(float productPrice) {
        if (currentBalance() >= productPrice) {
            String formattedRemainingBalance = String.format("%.2f", (float) (currentBalance() - productPrice));
            System.out.println("THANK YOU. $" + formattedRemainingBalance + " RETURNED");
            coinResetHelper();
            menuExit = true;
        } else {
            LOGGER.info("INSUFFICIENT FUNDS");
            System.out.println("PRICE: $" + productPrice);
        }
    }

    public void coinResetHelper() {
        nickels = 0;
        dimes = 0;
        quarters = 0;
    }


    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.menuPrompts(0);
        vendingMachine.menuPrompts(1);
        while (menuExit == false) {
            vendingMachine.userSelection(userInput.nextLine());
            System.out.println("Current Balance: $" + vendingMachine.currentBalance());
        }
    }
}
