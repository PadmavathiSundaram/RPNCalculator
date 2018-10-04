package com.airwallex;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains all the utility methods
 */
public class Utils {

    public static final Properties prop = new Properties();

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    /**
     * Truncates the deci al value to the specified precision and truncates trailing Zeros
     *
     * @param value
     * @param places
     * @return
     */
    public static BigDecimal truncate(BigDecimal value, int places) {
        return value
                .setScale(places, RoundingMode.DOWN)
                .stripTrailingZeros();
    }

    /**
     * Handles the output display. truncates the stack data based on display precision configuration
     *
     * @param stack
     */
    public static void display(Stack<BigDecimal> stack) {
        System.out.print("Stack:");
        for (BigDecimal result : stack) {
            BigDecimal stackItem = truncate(result, getConfigValueAsInt(Constants.displayDecimalPrecisionUnit));
            System.out.print(stackItem.toPlainString() + Constants.SPACE);
        }
        System.out.println();
    }

    /**
     * Populates the configuration values (at the start of the APP)
     */
    public static void populateProperties() {
        try {
            InputStream input = Utils.class.getClassLoader().getResourceAsStream("config_local.properties");
            // load a properties file
            prop.load(input);
        } catch (IOException e) {
           return;
        }
    }

    /**
     * Reads config values as int
     *
     * @param property
     * @return
     */
    public static int getConfigValueAsInt(String property) {
        return Integer.parseInt(getConfigValueAsString(property));
    }

    /**
     * Reads config value for the specified property name
     *
     * @param property
     * @return
     */
    public static String getConfigValueAsString(String property) {
        return Utils.prop.getProperty(property);
    }

    /**
     * Fetches the Opertaion based on the input String. InvalidDataException is thrown for unSupported Operations
     *
     * @param operationAsText
     * @return
     * @throws InValidDataException
     */
    public static Operations fetchOperationBasedOnInputData(String operationAsText) throws InValidDataException {
        Operations operation = getOperationByValue(operationAsText);
        if (operation == null) {
            throw new InValidDataException("Invalid Input :" + operationAsText);
        }
        return operation;
    }

    /**
     * If present returns the operation based on string value. returns null for no match.
     *
     * @param inputOperation
     * @return
     */
    public static Operations getOperationByValue(String inputOperation) {
        Operations[] values = Operations.values();
        for (Operations value : values) {
            if (value.operation.equalsIgnoreCase(inputOperation)) {
                return value;
            }
        }
        return null;
    }
}
