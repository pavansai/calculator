package calculator;

import calculator.operations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private Calculator calculator;
    private OperationRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new OperationRegistry();
        registry.registerOperation(Operation.ADD, new AddOperation());
        registry.registerOperation(Operation.SUBTRACT, new SubtractOperation());
        registry.registerOperation(Operation.MULTIPLY, new MultiplyOperation());
        registry.registerOperation(Operation.DIVIDE, new DivideOperation());

        calculator = new Calculator(registry);
    }

    @Test
    void testBasicOperations() {
        assertEquals(5L, calculator.calculate(Operation.ADD, 2, 3));
        assertEquals(-1L, calculator.calculate(Operation.SUBTRACT, 2, 3));
        assertEquals(6L, calculator.calculate(Operation.MULTIPLY, 2, 3));
        assertEquals(0.5, calculator.calculate(Operation.DIVIDE, 1, 2));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculator.calculate(Operation.DIVIDE, 5, 0);
        });
    }

    @Test
    void testUnsupportedOperation() {
        assertThrows(UnsupportedOperationException.class, () -> {
            calculator.calculate(Operation.POWER, 2, 3); // POWER isn't registered yet
        });
    }

    @Test
    void testChaining() {
        Number result = calculator.chain(5)
                .apply(Operation.ADD, 3)
                .apply(Operation.MULTIPLY, 2)
                .result();

        assertEquals(16L, result);
    }

    @Test
    void testExtensibility() {
        // Add a new operation
        registry.registerOperation(Operation.POWER, new PowerOperation());

        assertEquals(8.0, calculator.calculate(Operation.POWER, 2, 3));
    }

    @Test
    void testDifferentNumberTypes() {
        assertEquals(5.5, calculator.calculate(Operation.ADD, 2, 3.5));
        assertEquals(2.5, calculator.calculate(Operation.ADD, 2, 0.5));
    }
}