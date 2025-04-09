package calculator.operations;

import calculator.OperationStrategy;

public class AddOperation implements OperationStrategy {
    @Override
    public Number execute(Number num1, Number num2) {
        if (num1 instanceof Double || num2 instanceof Double) {
            return num1.doubleValue() + num2.doubleValue();
        } else {
            return num1.longValue() + num2.longValue();
        }
    }
}