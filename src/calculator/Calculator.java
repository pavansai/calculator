package calculator;

import calculator.exception.CalculationException;

public class Calculator {
    private final OperationRegistry operationRegistry;

    /**
     * Creates a new calculator with the specified operation registry.
     *
     * @param operationRegistry the registry containing the operations
     * @throws IllegalArgumentException if operationRegistry is null
     */
    public Calculator(OperationRegistry operationRegistry) {
        if (operationRegistry == null) {
            throw new IllegalArgumentException("Operation registry cannot be null");
        }
        this.operationRegistry = operationRegistry;
    }

    /**
     * Performs a calculation with the specified operation and numbers.
     *
     * @param operation the operation to perform
     * @param num1 the first number
     * @param num2 the second number
     * @return the result of the calculation
     * @throws CalculationException if the operation is not supported or if calculation fails
     * @throws IllegalArgumentException if any input is null
     */
    public Number calculate(Operation operation, Number num1, Number num2) {
        // Validate inputs
        validateInputs(operation, num1, num2);

        try {
            OperationStrategy strategy = operationRegistry.getStrategy(operation);
            return strategy.execute(num1, num2);
        } catch (UnsupportedOperationException e) {
            throw new CalculationException("Unsupported operation: " + operation, e);
        } catch (ArithmeticException e) {
            throw new CalculationException("Arithmetic error during calculation", e);
        } catch (Exception e) {
            throw new CalculationException("Unexpected error during calculation", e);
        }
    }

    /**
     * Validates the input parameters for calculation.
     *
     * @param operation the operation to validate
     * @param num1 the first number to validate
     * @param num2 the second number to validate
     * @throws IllegalArgumentException if any input is null
     */
    private void validateInputs(Operation operation, Number num1, Number num2) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        if (num1 == null) {
            throw new IllegalArgumentException("First operand cannot be null");
        }
        if (num2 == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }
    }

    /**
     * Creates a calculation chain starting with the specified initial value.
     *
     * @param initialValue the initial value for the chain
     * @return a new calculation chain
     * @throws IllegalArgumentException if initialValue is null
     */
    public CalculationChain chain(Number initialValue) {
        if (initialValue == null) {
            throw new IllegalArgumentException("Initial value cannot be null");
        }
        return new CalculationChain(this, initialValue);
    }
}