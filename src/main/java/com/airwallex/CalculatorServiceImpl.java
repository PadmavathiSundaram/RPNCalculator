package com.airwallex;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Handles users inputs
 */
public class CalculatorServiceImpl implements CalculatorService {


    /**
     * Once identified as a operand, the data is passed to this method for further process.
     * Adds the operands to the stack and updates the state of the stack in stackHistory
     *
     * @param currentOperand
     */
    public void serviceOperands(BigDecimal currentOperand) {
        BigDecimal operand = Utils.truncate(currentOperand, Utils.getConfigValueAsInt(Constants.inStackDecimalPrecisionUnit));
        stackHistory.push((Stack<BigDecimal>) stack.clone());
        stack.push(operand);
    }

    /**
     * Process the Identified Operations
     * Executes the requested Operation and updates the result in the stack.
     * Adds the last operand to the stack history.
     *
     * @param operationAsText
     * @param inputStartsWithAnOperand
     * @param operationIndex
     * @throws InValidDataException
     */
    public void serviceOperation(String operationAsText, boolean inputStartsWithAnOperand, int operationIndex) throws InValidDataException {
        Operations operation = Utils.fetchOperationBasedOnInputData(operationAsText);
        if (inputStartsWithAnOperand) {
            stackHistory.push((Stack<BigDecimal>) stack.clone());
        }
        Stack resultStack = (Stack) operation.execute(stack, operationIndex).clone();
        stack.clear();
        stack.addAll(resultStack);
    }

    /**
     * Updates and displays the stack in case of an error scenario
     */
    public void serviceError() {
        if (!stack.isEmpty()) {
            Utils.display(stack);
        } else if (!stackHistory.isEmpty()) {
            Utils.display(stackHistory.peek());
        }
    }


}
