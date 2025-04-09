package calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for operation strategies.
 * Ths will provide a mechanism to register, retrieve, and check operations.
 */
public class OperationRegistry {
    private final Map<Operation, OperationStrategy> operations = new HashMap<>();

    /**
     * Registers an operation strategy for the specified operation.
     *
     * @param operation the operation to register
     * @param strategy the strategy implementation for the operation
     */
    public void registerOperation(Operation operation, OperationStrategy strategy) {
        operations.put(operation, strategy);
    }

    /**
     * Gets the strategy for the specified operation.
     *
     * @param operation the operation to get the strategy for
     * @return the strategy for the operation
     * @throws UnsupportedOperationException if the operation is not supported
     */
    public OperationStrategy getStrategy(Operation operation) {
        OperationStrategy strategy = operations.get(operation);
        if (strategy == null) {
            throw new UnsupportedOperationException("Operation " + operation + " is not supported");
        }
        return strategy;
    }

    /**
     * Checks if the specified operation is supported.
     *
     * @param operation the operation to check
     * @return true if the operation is supported, false otherwise
     */
    public boolean supportsOperation(Operation operation) {
        return operations.containsKey(operation);
    }
}