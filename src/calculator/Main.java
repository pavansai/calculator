package calculator;

import calculator.operations.*;

public class Main {
    public static void main(String[] args) {

        OperationRegistry registry = new OperationRegistry();
        registry.registerOperation(Operation.ADD, new AddOperation());
        registry.registerOperation(Operation.SUBTRACT, new SubtractOperation());
        registry.registerOperation(Operation.MULTIPLY, new MultiplyOperation());
        registry.registerOperation(Operation.DIVIDE, new DivideOperation());
        registry.registerOperation(Operation.POWER, new PowerOperation());


        Calculator calculator = new Calculator(registry);


        System.out.println("2 + 3 = " + calculator.calculate(Operation.ADD, 2, 3));
        System.out.println("5 - 2 = " + calculator.calculate(Operation.SUBTRACT, 5, 2));
        System.out.println("4 * 3 = " + calculator.calculate(Operation.MULTIPLY, 4, 3));
        System.out.println("10 / 2 = " + calculator.calculate(Operation.DIVIDE, 10, 2));
        System.out.println("2^3 = " + calculator.calculate(Operation.POWER, 2, 3));


        Number result = calculator.chain(5)
                .apply(Operation.ADD, 3)      // 5 + 3 = 8
                .apply(Operation.MULTIPLY, 2) // 8 * 2 = 16
                .result();

        System.out.println("5 + 3 * 2 = " + result);
    }
}