package expressions;

/**
 *  {@code Multiplication} is the class that extends Expression to
 *  provide a product of two other expressions.
 *  @version 2022092200
 *  @author David Simmons
 */
public class Multiplication extends Expression
{
    /**
     *  The operator used for {@code Multiplication}.
     */
    public static final char	operator	= '*';

    /**
     *  The priority used for {@code Multiplication}.
     */
    public static final int     priority    = '2';

    private Expression  multiplicand;
    private Expression  multiplier;

    /**
     *  Construct an instance of {@code Multiplication} that evaluates
     *  to the product of its parameters.
     *  @param multiplicand The left-hand operand of the sum.
     *  @param multiplier The right-hand operand of the sum.
     */
    public Multiplication(Expression multiplicand, Expression multiplier)
    {
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
    }

    /**
     *  Return the current product of our operands.
     *  @return The current product of our operands.
     */
    public int getValue()
    {
        return(multiplicand.getValue() * multiplier.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String toString()
    {
        return(multiplicand + " " + operator + " " + multiplier);
    }

    /**
     *  Unit test our {@code Multiplication} expression.
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

        one = new Integer("9");
        two = new Integer("8");
        expressionToTest = "9 " + operator + " 8";

        errors = 0;

        expression = new Multiplication(one, two);
        getValue = expression.getValue();
        System.out.println("Multiplying 9 & 8: " + getValue);
        if (getValue != 72) {
            System.out.println("*** ERROR *** getValue()" +
                               " returns " + getValue +
                               " should be " + 72);
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
