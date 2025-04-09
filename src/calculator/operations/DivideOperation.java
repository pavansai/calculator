package calculator.operations;

import calculator.OperationStrategy;

/**
 * Implementation of the division operation.
 */
public class DivideOperation implements OperationStrategy {
    @Override
    public Number execute(Number num1, Number num2) {
        if (num2.doubleValue() == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        // Always return double for division to handle non-integer results
        return num1.doubleValue() / num2.doubleValue();
    }
}
