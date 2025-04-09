package calculator;

/**
 * Calculator class that performs calculations using registered operations.
 */
public class Calculator {
    private final OperationRegistry operationRegistry;

    /**
     * Creates a new calculator with the specified operation registry.
     *
     * @param operationRegistry the registry containing the operations
     */
    public Calculator(OperationRegistry operationRegistry) {
        this.operationRegistry = operationRegistry;
    }

    /**
     * Performs a calculation with the specified operation and numbers.
     *
     * @param operation the operation to perform
     * @param num1 the first number
     * @param num2 the second number
     * @return the result of the calculation
     * @throws UnsupportedOperationException if the operation is not supported
     */
    public Number calculate(Operation operation, Number num1, Number num2) {
        OperationStrategy strategy = operationRegistry.getStrategy(operation);
        return strategy.execute(num1, num2);
    }

    /**
     * Creates a calculation chain starting with the specified initial value.
     *
     * @param initialValue the initial value for the chain
     * @return a new calculation chain
     */
    public CalculationChain chain(Number initialValue) {
        return new CalculationChain(this, initialValue);
    }
}