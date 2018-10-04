package com.airwallex;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Orchestrates the Application flow
 */
public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private CalculatorService calculatorService;

    /**
     * Can Override the Service if need arises
     *
     * @param calculatorService
     */
    public Controller(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public Controller() {
        this.calculatorService = new CalculatorServiceImpl();
    }

    /**
     * Handles Input in a Line by Line manner
     */
    public void serviceInputs(Scanner inLine) {
        try {
            while (inLine.hasNextLine()) {
                Scanner in = new Scanner(inLine.nextLine());
                serviceCurrentInputLine(in);
            }
            inLine.close();
        } catch (InValidDataException e) {
            handleException(e);
        }

    }

    /**
     * Accepts and processes data in a single input line
     * Differentiates operand , operations and invalid Data
     *
     * @param in
     * @throws InValidDataException
     */
    private void serviceCurrentInputLine(Scanner in) throws InValidDataException {
        boolean inputStartsWithAnOperand = false;

        while (in.hasNext()) {
            if (in.hasNextBigDecimal()) {
                inputStartsWithAnOperand = true;
                this.calculatorService.serviceOperands(in.nextBigDecimal());
            } else {
                this.calculatorService.serviceOperation(in.next(), inputStartsWithAnOperand, in.match().start());
            }
        }
        Utils.display(CalculatorService.stack);
        in.close();
    }

    /**
     * Handles exception and Data validations errors
     *
     * @param e
     */
    private void handleException(InValidDataException e) {

        this.calculatorService.serviceError();
        System.out.println(e.getMessage());

        LOGGER.log(Level.SEVERE, e.getMessage());

        return;

    }


}
