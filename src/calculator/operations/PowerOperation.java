package calculator.operations;

import calculator.OperationStrategy;

public class PowerOperation implements OperationStrategy {
    @Override
    public Number execute(Number num1, Number num2) {
        return Math.pow(num1.doubleValue(), num2.doubleValue());
    }
}