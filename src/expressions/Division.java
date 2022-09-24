package expressions;

/**
 *  {@code Division} is the class that extends Expression to
 *  provide a quotient of two other expressions.
 *  @version 2022092200
 *  @author David Simmons
 */
public class Division extends Expression
{
    /**
     *  The operator used for {@code Division}.
     */
    public static final char operator = '/';

    /**
     *  The priority used for {@code Division}.
     */
    public static final int priority = '2';

    private Expression dividend;
    private Expression divisor;

    /**
     *  Construct an instance of {@code Division} that evaluates
     *  to the quotient of its parameters.
     *  @param dividend The left-hand operand of the sum.
     *  @param divisor The right-hand operand of the sum.
     */
    public Division(Expression dividend, Expression divisor)
    {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    /**
     *  Return the current quotient of our operands.
     *  @return The current quotient of our operands.
     */
    public int getValue()
    {
        return(dividend.getValue() / divisor.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String toString()
    {
        return(dividend + " " + operator + " " + divisor);
    }

    /**
     *  Unit test our {@code Division} expression.
     *  @param arg Command line arguments
     */
    public static void main(String arg[])
    {
        int         errors;
        Integer     one;
        Integer     two;
        int         getValue;
        String      toString;
        Expression  expression;
        String		expressionToTest;

        one = new Integer("12");
        two = new Integer("6");
        expressionToTest = "12 " + operator + " 6";

        errors = 0;

        expression = new Division(one, two);
        getValue = expression.getValue();
        System.out.println("Dividing 12 & 6: " + getValue);
        if (getValue != 2) {
            System.out.println("*** ERROR *** getValue()" +
                               " returns " + getValue +
                               " should be " + 2);
            ++errors;
        }

        toString = "" + expression;
        if (toString.equals(expressionToTest) == false) {
            System.out.println("*** ERROR *** toString()" +
                               " returns " + toString +
                               " should be " + expressionToTest);
            ++errors;
        }

        if (errors > 0) {
            System.out.println("\nUNIT TEST FAILED with " +
                               errors + " errors!");
            System.exit(1);
        }
    }
}
