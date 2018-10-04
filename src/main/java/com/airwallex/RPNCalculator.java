package com.airwallex;

import java.util.Scanner;

/**
 * RPN Calculator
 */
public class RPNCalculator {

    static {
        Utils.populateProperties();
    }

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        System.out.println("----RPN CALCULATOR : Please Enter the values----\n");
        controller.serviceInputs(new Scanner(System.in));
    }
}
