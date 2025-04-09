package calculator;

/**
 * Strategy interface for calculator operations.
 * Each operation implements this interface to provide its specific behavior.
 */
public interface OperationStrategy {
    /**
     * Executes the operation on two numbers.
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return the result of the operation
     */
    Number execute(Number num1, Number num2);
}