package calculator.operations;

import calculator.OperationStrategy;
import calculator.exception.CalculationException;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Implementation of the addition operation with enhanced precision support.
 * Supports various numeric types including BigDecimal for high-precision calculations.
 */
public class AddOperation implements OperationStrategy {
    private static final Logger logger = Logger.getLogger(AddOperation.class.getName());
    private static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);

    @Override
    public Number execute(Number num1, Number num2) {
        if (num1 == null || num2 == null) {
            logger.severe("Null operand passed to AddOperation: num1={}, num2={}");
            throw new IllegalArgumentException("Operands cannot be null");
        }

        try {
            // Handle BigDecimal for high precision calculations
            if (num1 instanceof BigDecimal || num2 instanceof BigDecimal) {
                BigDecimal bd1 = convertToBigDecimal(num1);
                BigDecimal bd2 = convertToBigDecimal(num2);
                BigDecimal result = bd1.add(bd2, MATH_CONTEXT);
                logger.fine("BigDecimal addition: {} + {} = {}");
                return result;
            }

            // Handle double for floating point calculations
            if (num1 instanceof Double || num2 instanceof Double ||
                    num1 instanceof Float || num2 instanceof Float) {
                double result = num1.doubleValue() + num2.doubleValue();
                logger.fine("Double addition: {} + {} = {}");
                return result;
            }

            // Handle long for integer calculations
            long result = num1.longValue() + num2.longValue();
            logger.fine("Long addition: {} + {} = {}");

            // Check for potential overflow by comparing with original values
            if ((result > 0 && (num1.longValue() < 0 && num2.longValue() < 0)) ||
                    (result < 0 && (num1.longValue() > 0 && num2.longValue() > 0))) {
                logger.warning("Potential integer overflow detected, converting to BigDecimal");
                return convertToBigDecimal(num1).add(convertToBigDecimal(num2));
            }

            return result;
        } catch (Exception e) {
            logger.severe("Error performing addition operation");
            throw new CalculationException("Error performing addition operation", e);
        }
    }

    /**
     * Converts a Number to BigDecimal with appropriate handling for different numeric types.
     *
     * @param number the number to convert
     * @return a BigDecimal representation of the number
     */
    private BigDecimal convertToBigDecimal(Number number) {
        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Double || number instanceof Float) {
            return new BigDecimal(number.toString(), MATH_CONTEXT);
        } else {
            return new BigDecimal(number.longValue());
        }
    }
}