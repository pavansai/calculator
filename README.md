# Flexible Calculator

created a production-ready, extensible calculator implementation in Java that follows object-oriented principles and best practices.

## Project Overview

This project implements a flexible calculator that supports multiple operations and adheres to key object-oriented principles, especially the Open-Closed Principle. The calculator is designed with maintainability and extensibility in mind.

### Key Features

- Basic arithmetic operations (ADD, SUBTRACT, MULTIPLY, DIVIDE)
- Extensible architecture for adding new operations (e.g., POWER)
- Chaining functionality for sequential operations
- Robust error handling
- Comprehensive test suite
- Support for various number types (Integer, Long, Double, BigDecimal)

## Design

The calculator is implemented following several design patterns:

1. **Strategy Pattern**: Each operation implements the `OperationStrategy` interface, allowing for interchangeable operation implementations.

2. **Registry Pattern**: The `OperationRegistry` manages the available operations, enabling dynamic registration of new operations.

3. **IoC Compatibility**: The calculator depends on abstractions, not concrete implementations, making it suitable for Inversion of Control environments.

4. **Open-Closed Principle**: New operations can be added without modifying existing code.

## Project Structure

- `calculator`: Core package with main calculator classes
  - `Calculator`: Main calculator class
  - `Operation`: Enum for supported operations
  - `OperationStrategy`: Interface for operation implementations
  - `OperationRegistry`: Registry for maintaining operations
  - `CalculationChain`: Supports chained operations

- `calculator.operations`: Concrete operation implementations
  - `AddOperation`, `SubtractOperation`, `MultiplyOperation`, `DivideOperation`, `PowerOperation`

- `calculator.exception`: Custom exceptions
  - `CalculationException`: Wrapper for calculation errors

## Usage Examples

### Basic Calculations

```java
// Set up the calculator
OperationRegistry registry = new OperationRegistry();
registry.registerOperation(Operation.ADD, new AddOperation());
registry.registerOperation(Operation.SUBTRACT, new SubtractOperation());
registry.registerOperation(Operation.MULTIPLY, new MultiplyOperation());
registry.registerOperation(Operation.DIVIDE, new DivideOperation());

Calculator calculator = new Calculator(registry);

// Perform basic calculations
Number result1 = calculator.calculate(Operation.ADD, 2, 3);      // 5
Number result2 = calculator.calculate(Operation.SUBTRACT, 5, 2); // 3
Number result3 = calculator.calculate(Operation.MULTIPLY, 4, 3); // 12
Number result4 = calculator.calculate(Operation.DIVIDE, 10, 2);  // 5
```

### Chaining Operations

```java
Number result = calculator.chain(5)
        .apply(Operation.ADD, 3)      // 5 + 3 = 8
        .apply(Operation.MULTIPLY, 2) // 8 * 2 = 16
        .result();                    // result = 16
```

### Adding New Operations

```java
// Define new operation in the Operation enum (Operation.POWER is included)
// Implement the operation
public class PowerOperation implements OperationStrategy {
    @Override
    public Number execute(Number num1, Number num2) {
        return Math.pow(num1.doubleValue(), num2.doubleValue());
    }
}

// Register the new operation
registry.registerOperation(Operation.POWER, new PowerOperation());

// Use the new operation
Number result = calculator.calculate(Operation.POWER, 2, 3); // 8.0
```

## Docker Support

This project includes Docker support for easy setup and execution.

### Building the Docker Image

```bash
# Build the Docker image
docker build -t flexible-calculator .
```

### Running the Calculator in Docker

```bash
# Run the calculator application
docker run flexible-calculator
```

This will execute the main class which demonstrates various calculator operations.

### Using Docker Compose

A `docker-compose.yml` file is also included for easy development:

```bash
# Start the calculator using Docker Compose
docker-compose up

# Run in detached mode
docker-compose up -d

# Stop the container
docker-compose down
```

## Assumptions and Design Decisions

1. **Number Type Handling**: The calculator automatically determines the appropriate return type based on the input types.
   - Integer operations return Long
   - Operations involving floating-point numbers return Double
   - High-precision operations use BigDecimal

2. **Error Handling**: Operations that might fail (like division by zero) throw appropriate exceptions that are wrapped in a `CalculationException`.

3. **Extensibility**: New operations can be added by:
   - Adding the operation to the `Operation` enum
   - Implementing the `OperationStrategy` interface
   - Registering the implementation with the `OperationRegistry`

4. **IoC Compatibility**: The calculator follows dependency injection principles, making it suitable for use in IoC containers.
