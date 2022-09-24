package expressions;

import java.text.ParseException;

/**
 *  {@code Parenthesis} is the class that extends Expression to
 *
 *  @version 2022092200
 *  @author David Simmons
 */
public class Parenthesis extends Expression
{
    /**
     *  The open operator used for open {@code Parenthesis}.
     */
    public static final char openOperator = '(';

    /**
     *  The close operator used for close {@code Parenthesis}.
     */
    public static final char closeOperator = ')';

    /**
     *  The priority used for {@code Parenthesis}.
     */
    public static final int priority = '3';

    private String subexpression;
    private Expression expression;

    /**
     *  Construct an instance of {@code Parenthesis} that
     *  @param expression Instance of expression class.
     */
    public Parenthesis(Expression expression)
    {
        this.expression = expression;
    }

    /**
     *  Return the current expression.
     *  @return The current expression being evaluated.
     */
    public int  getValue()
    {
        return(expression.getValue());
    }

    /**
     *  Return our expression as a {@code String}.
     *  @return Our expression as a {@code String}.
     */
    public String toString()
    {
        return(openOperator + " " +
               this.expression.toString() + " " +
               closeOperator);
    }

    /**
     *  Unit test our {@code Parenthesis} expression.
     *  @param arg Command line arguments
     */
    public static void  main(String arg[])
    {
        int             errors;
        int             whichTest;
        /*
         *  The items in this array are expressions to evaluate.
         *  Each corresponds to the list of results in the
         *  following at the same index.
         */
        String toTest[] = {
               "1 + (2 * 3)",
               "(1 * 2) + 3",
        };
        /*
         *  The items in this array are the results of evaluating the
         *  corresponding expressions at the same index in the
         *  previous array. A -99 value means we expect an exception.
         */
        int results[] = {
            6,
            5,
        };


        if (toTest.length != results.length) {
            /*
             *  Someone changed the expressions to evaluate or
             *  results without changing the other.
             */
            System.out.println("*** ERROR *** " +
                    toTest.length +
                    " expressions to evaluate and " +
                    results.length +
                    " results");
            System.exit(1);
        }

        /*
         *  Go through each test.
         */
        errors = 0;
        for (whichTest = 0; (whichTest < toTest.length);
             ++whichTest) {
            int         getValue;
            String      toString;
            Expression  expression;
            String      parenthesisResults;

            System.out.println("Trying to evaluate:\n" +
                    "  \"" + toTest[whichTest] +
                    "\"");

            try {
                expression = new Expression(toTest[whichTest]);
                Parenthesis parenthesis = new Parenthesis(expression);
                // the expression is now stored in the instance variable
                parenthesisResults =
                        String.valueOf(parenthesis.getValue());

                System.out.println(parenthesisResults);
            } catch (Exception exception) {
                /*
                 *  Got an exception.
                 */
                if ((exception instanceof ParseException) != true) {
                    /*
                     *  It's the wrong exception.
                     */
                    System.out.println("*** ERROR ***");
                    ++errors;
                } else if (results[whichTest] != -99) {
                    /*
                     *  We weren't expecting this Parse Exception.
                     */
                    System.out.println("*** ERROR *** at position " +
                            ((ParseException)exception).
                                    getErrorOffset());
                    ++errors;
                }
                exception.printStackTrace(System.out);
                continue;
            }

            getValue = expression.getValue();
            System.out.println("Evaluates to: " + getValue);

            if (getValue != results[whichTest]) {
                System.out.println("*** ERROR *** getValue()" +
                        " returns " + getValue +
                        " should be " + results[whichTest]);
                ++errors;
            }

            toString = "" + expression;
            if (toString.equals(toTest[whichTest]) == false) {
                System.out.println("*** ERROR *** toString()" +
                        " returns " + toString +
                        " should be " + toTest[whichTest]);
                ++errors;
            }
        }

        if (errors > 0) {
            System.out.println("\nUNIT TEST FAILED with " +
                    errors + " errors!");
            System.exit(1);
        }
    }
}
