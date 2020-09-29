package com.vendingmachine;

import com.vendingmachine.VendingMachine;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import java.util.List;

public class VendingMachineTest {
    static VendingMachine vendingMachine = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void insert_coin() throws Exception{
        insertCoinHelper("dime", 1);
        insertCoinHelper("nickel", 1);
        insertCoinHelper("quarter", 1);
    }

    @Test
    public void select_product() throws Exception{
        select_product_helper("quarter", 4, "chips");
        select_product_helper("quarter", 4, "cola");
        select_product_helper("quarter", 4, "candy");
    }

    @Test
    public void insert_invalid_selection() throws Exception {
        logger_helper("silver dollar", "INVALID SELECTION");
    }

    @Test
    public void insufficient_funds_available() throws Exception {
        logger_helper("chips", "INSUFFICIENT FUNDS");
    }

    @Test
    public void coin_reset() throws Exception {
        vendingMachine.userSelection("nickel");
        vendingMachine.coinResetHelper();
        Assert.assertEquals(0, vendingMachine.getNickels());
    }

    public void insertCoinHelper(String coinName, int coinCount) {
        vendingMachine.userSelection(coinName);

        if (coinName == "dime") {
            Assert.assertEquals(coinCount, vendingMachine.getDimes());
        } else if (coinName == "nickel") {
            Assert.assertEquals(coinCount, vendingMachine.getNickels());
        } else {
            Assert.assertEquals(coinCount, vendingMachine.getQuarters());
        }
    }

    public void select_product_helper(String coinName, int coinCount, String product) {
        for (int i = 0; i < coinCount; i++) {
            vendingMachine.userSelection(coinName);
        }
        vendingMachine.userSelection(product);
        Assert.assertEquals(0.00, vendingMachine.currentBalance(), 0);
    }

    public void logger_helper(String testValue, String expectedLog) {
        // Grab the Logger from our VendingMachine
        Logger testLogger = (Logger) LoggerFactory.getLogger(VendingMachine.class);

        // Create a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // We add the Appender to the logger
        testLogger.addAppender(listAppender);

        // Pass in our invalid coin
        vendingMachine.userSelection(testValue);

        List<ILoggingEvent> logsList = listAppender.list;
        Assert.assertEquals(expectedLog, logsList.get(0).getMessage());
        Assert.assertEquals(Level.INFO, logsList.get(0).getLevel());
    }


}