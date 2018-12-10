package com.airwallex;

import java.math.BigDecimal;
import java.util.Stack;

public interface CalculatorService {

    Stack<BigDecimal> stack = new Stack<BigDecimal>();
    Stack<Stack<BigDecimal>> stackHistory = new Stack<Stack<BigDecimal>>();

    void serviceOperands(BigDecimal currentOperand);

    void serviceOperation(String operationAsText, boolean inputStartsWithAnOperand, int operationIndex)
            throws InValidDataException;

    void serviceError();
}
