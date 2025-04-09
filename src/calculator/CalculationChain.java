package calculator;

/**
 * Chain of calculations starting with an initial value.
 * Allows sequential application of operations to a running result.
 */
public class CalculationChain {
    private final Calculator calculator;
    private Number currentValue;

    /**
     * Creates a new calculation chain with the specified calculator and initial value.
     *
     * @param calculator the calculator to use for calculations
     * @param initialValue the initial value for the chain
     */
    public CalculationChain(Calculator calculator, Number initialValue) {
        this.calculator = calculator;
        this.currentValue = initialValue;
    }

    /**
     * Applies an operation to the current value and the specified operand.
     *
     * @param operation the operation to apply
     * @param operand the operand to use
     * @return this chain for method chaining
     */
    public CalculationChain apply(Operation operation, Number operand) {
        currentValue = calculator.calculate(operation, currentValue, operand);
        return this;
    }

    /**
     * Gets the current result of the calculation chain.
     *
     * @return the current result
     */
    public Number result() {
        return currentValue;
    }
}