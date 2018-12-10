package com.airwallex;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enum of supported Operations
 */
public enum Operations {
    PLUS("+") {
        @Override
        BigDecimal calculate(BigDecimal last, BigDecimal lastButOne) {
            return last.add(lastButOne);
        }
    },
    MINUS("-") {
        @Override
        BigDecimal calculate(BigDecimal last, BigDecimal lastButOne) {
            return lastButOne.subtract(last);
        }
    },
    MULTIPLY("*") {
        @Override
        BigDecimal calculate(BigDecimal last, BigDecimal lastButOne) {
            return last.multiply(lastButOne);
        }
    },
    DIVIDE("/") {
        @Override
        BigDecimal calculate(BigDecimal last, BigDecimal lastButOne) {
            return lastButOne.divide(last);
        }
    },
    SQRT("sqrt") {
        @Override
        public Stack execute(Stack stack, int start) {
            BigDecimal last = (BigDecimal) stack.pop();
            BigDecimal sqrt = Utils.truncate(new BigDecimal(Math.sqrt(last.doubleValue())), Utils.getConfigValueAsInt(Constants.inStackDecimalPrecisionUnit));
            stack.push(sqrt);
            return stack;
        }
    },
    UNDO("undo") {
        /**
         * Removes and returns the last action performed from the history stack.
         * @param stack
         * @param start
         * @return
         */
        @Override
        Stack execute(Stack stack, int start) {
            if (CalculatorService.stackHistory.isEmpty()) {
                LOGGER.log(Level.INFO, "Nothing to Undo - ignored");
                return CalculatorService.stackHistory;
            }
            return CalculatorService.stackHistory.pop();
        }
    },
    CLEAR("clear") {
        /**
         * Returns a new stack.
         * @param stack
         * @param start
         * @return
         */
        @Override
        Stack execute(Stack stack, int start) {
            CalculatorService.stackHistory.push((Stack<BigDecimal>) CalculatorService.stack.clone());
            return new Stack();
        }
    };

    private static final Logger LOGGER = Logger.getLogger(Operations.class.getName());

    public String operation;

    Operations(String operation) {
        this.operation = operation;
    }

    /**
     * Default Execute for Operations
     *
     * @param stack
     * @param start
     * @return
     * @throws InValidDataException
     */
    Stack execute(Stack stack, int start) throws InValidDataException {
        return handleOperation(stack, ++start);
    }

    /**
     * Default calculate for operations
     *
     * @param last
     * @param lastButOne
     * @return
     */

    BigDecimal calculate(BigDecimal last, BigDecimal lastButOne) {
        return last;
    }

    /**
     * Adds the current state of stack to history prior to modifying the stack with the operation result.
     * Removes the last and last but one parameters from the stacks and lets the operation override the way calculations happen and add the result to the stack.
     *
     * @param stack
     * @param operatorIndex
     * @return
     * @throws InValidDataException
     */
    private Stack handleOperation(Stack stack, int operatorIndex) throws InValidDataException {
        CalculatorService.stackHistory.push((Stack<BigDecimal>) CalculatorService.stack.clone());

        validateStackParams(stack, operatorIndex);
        BigDecimal last = (BigDecimal) stack.pop();

        validateStackParams(stack, operatorIndex);
        BigDecimal lastButOne = (BigDecimal) stack.pop();

        BigDecimal result = this.calculate(last, lastButOne);
        result = Utils.truncate(result, Utils.getConfigValueAsInt(Constants.inStackDecimalPrecisionUnit));
        stack.push(result);

        return stack;
    }

    /**
     * Validates if there are enough operands on the stack to perform the Operation
     *
     * @param stack
     * @param operatorIndex
     * @throws InValidDataException
     */
    private void validateStackParams(Stack stack, int operatorIndex) throws InValidDataException {
        if (stack.isEmpty()) {
            throw new InValidDataException("Operator " + this.operation.toString() + " (position: )" + operatorIndex + " insufficient parameters");
        }
    }


}
