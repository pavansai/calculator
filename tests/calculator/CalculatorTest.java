package calculator;

import calculator.exception.CalculationException;
import calculator.operations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Tests")
class CalculatorTest {
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
    @DisplayName("Basic operations should produce expected results")
    void testBasicOperations() {
        assertEquals(5L, calculator.calculate(Operation.ADD, 2, 3));
        assertEquals(-1L, calculator.calculate(Operation.SUBTRACT, 2, 3));
        assertEquals(6L, calculator.calculate(Operation.MULTIPLY, 2, 3));
        assertEquals(0.5, calculator.calculate(Operation.DIVIDE, 1, 2));
    }

    @Test
    @DisplayName("Division by zero should throw ArithmeticException")
    void testDivideByZero() {
        assertThrows(CalculationException.class, () -> {
            calculator.calculate(Operation.DIVIDE, 5, 0);
        });
    }

    @Test
    @DisplayName("Unsupported operations should throw CalculationException")
    void testUnsupportedOperation() {
        assertThrows(CalculationException.class, () -> {
            calculator.calculate(Operation.POWER, 2, 3); // POWER isn't registered yet
        });
    }

    @Test
    @DisplayName("Operation chaining should work correctly")
    void testChaining() {
        Number result = calculator.chain(5)
                .apply(Operation.ADD, 3)
                .apply(Operation.MULTIPLY, 2)
                .result();

        assertEquals(16L, result);
    }

    @Test
    @DisplayName("New operations can be added without modifying Calculator code")
    void testExtensibility() {
        // Add a new operation
        registry.registerOperation(Operation.POWER, new PowerOperation());

        assertEquals(8.0, calculator.calculate(Operation.POWER, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("provideNumberTypeTestCases")
    @DisplayName("Operations should handle different number types correctly")
    void testDifferentNumberTypes(Number num1, Number num2, Number expected) {
        assertEquals(expected, calculator.calculate(Operation.ADD, num1, num2));
    }

    static Stream<Arguments> provideNumberTypeTestCases() {
        return Stream.of(
                Arguments.of(2, 3.5, 5.5),
                Arguments.of(2, 0.5, 2.5),
                Arguments.of(new BigDecimal("123.456"), new BigDecimal("876.544"), new BigDecimal("1000.000")),
                Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, (long)Integer.MAX_VALUE + Integer.MAX_VALUE)
        );
    }

    @Test
    @DisplayName("Null inputs should throw IllegalArgumentException")
    void testNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(null, 1, 2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(Operation.ADD, null, 2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(Operation.ADD, 1, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.chain(null);
        });
    }

    @Test
    @DisplayName("Integer overflow should be handled correctly")
    void testIntegerOverflow() {
        long maxValue = Long.MAX_VALUE;
        Number result = calculator.calculate(Operation.ADD, maxValue, 1);
        assertTrue(result instanceof BigDecimal, "Result should be BigDecimal to handle overflow");
    }

    @Test
    @DisplayName("Calculator should propagate exceptions from operations")
    void testExceptionPropagation() {
        // Create a custom implementation that throws an exception
        OperationStrategy exceptionThrowingStrategy = new OperationStrategy() {
            @Override
            public Number execute(Number num1, Number num2) {
                throw new RuntimeException("Test exception");
            }
        };

        // Register the custom implementation
        registry.registerOperation(Operation.POWER, exceptionThrowingStrategy);

        // Test exception propagation
        assertThrows(CalculationException.class, () -> {
            calculator.calculate(Operation.POWER, 2, 3);
        });
    }
    @Test
    @DisplayName("Performance test with many operations")
    void testPerformance() {
        CalculationChain chain = calculator.chain(1);
        long start = System.currentTimeMillis();

        // Perform 10,000 operations
        for (int i = 0; i < 10000; i++) {
            chain.apply(Operation.ADD, 1);
        }

        long end = System.currentTimeMillis();
        Number result = chain.result();

        assertEquals(10001L, result);
        assertTrue((end - start) < 1000, "Performance test should complete within 1 second");
    }
}