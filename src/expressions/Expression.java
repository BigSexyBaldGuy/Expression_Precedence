package expressions;

import java.util.*;
import java.text.*;

/**
 *  {@code Expression} is the class that takes tokens from the parser
 *  and creates an object that can be evaluated.
 *  @version 2022092300
 *  @author David Simmons
 */
public class    Expression
{
    private Expression  value;

    /**
     *  Constructor of an expression.
     *  @param string {@code String} containing the expression.
     *  @throws ParseException When the given parameter doesn't
     *  comply with the syntax.
     */
    public  Expression(String string)
            throws ParseException
    {
        String                  tokens[];
        Stack<String>           pendingOperations;
        Stack<Expression>       pendingOperands;

        /*
         *  Have the parser give us all the tokens.
         */
        tokens = Parse.parse(string);

        /*
         *  We need stacks to collect tokens into expressions.
         *  Expressions are in infix form as in
         *    operand operator operand.
         *  Each operand may be another expression.
         */
        pendingOperations = new Stack<>();
        pendingOperands = new Stack<>();

        /*
         *  Let's visit each token in order.
         */
        for (String nextToken : tokens) {
            if (Character.isDigit(nextToken.charAt(0)) == true) {
                /*
                 *  The parser guarantees that if the token starts
                 *  with a digit, it's all digits.  So, instantiate
                 *  an integer operand and push it on the stack to
                 *  save it until an operation tells us what to do
                 *  with it.
                 */

//                System.out.println("digit:  Pushing '" + nextToken +
//                                   "' as an operand");
                pendingOperands.push(new Integer(nextToken));
                continue;
            }

            /*
             *  We know it's not an integer.
             */

			/*
			 *  Let's see if we can construct an expression from
			 *  what we've seen so far.
			 */
			constructOperation(pendingOperations, pendingOperands);

            /*
             *  This token becomes the next operation and all the
             *  operations we just constructed (if any) are its
             *  left-hand operand.
             */
            pendingOperations.push(nextToken);
//            System.out.println("op:  Pushing '" + nextToken +
//                               "' as an operation");
        }

        /*
         *  We've run out of tokens.  Construct whatever operations
         *  are left.
         */
		constructOperation(pendingOperations, pendingOperands);

		if (pendingOperations.empty() == false) {
			/*
			 *  There's an operation left!?
			 */
			throw(new ParseException("Don't know what to do with '" +
									 pendingOperations.pop() + "'",
									 0));
		}

        if (pendingOperands.empty() == true) {
			/*
			 *  There aren't any subexpressions left!?
			 */
			throw(new ParseException("Expression evaluated to nothing",
									 0));
		}

		/*
		 *  Assume the top operand is our value.
		 */
		value = pendingOperands.pop();
		if (pendingOperands.empty() == false) {
			/*
			 *  Having operands left is wrong.
			 */
			throw(new ParseException("Operands left over" +
									 " starting with \"" +
									 pendingOperands.pop() + "\"", 0));
        }
    }

    /**
     *  Constructor which does nothing for use by our subclasses.
     */
    public Expression()
    {
    }

    /*
     *  Helper method to create an expression from an operator
     *  and its operands.  The operator must be on the top of
     *  the pendingOperations stack and its operands must be
     *  at the top of the pendingOperands stack.
     */
    private void constructOperation(Stack<String> pendingOperations,
                                    Stack<Expression> pendingOperands)
                        throws ParseException
    {
        while (pendingOperations.empty() == false) {
			char		pendingOperator;
			String		pendingOperation;
			Expression  left;
			Expression  right;

            pendingOperation = pendingOperations.pop();
            pendingOperator = pendingOperation.charAt(0);

			left = null;
			right = null;

            // Addition
			if (pendingOperator == Addition.operator ||
                pendingOperator == Subtraction.operator ||
                pendingOperator == Multiplication.operator ||
                pendingOperator == Division.operator) {
				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing right" +
											 " operand for " +
											 pendingOperation, 0));
				}
				right = pendingOperands.pop();

				if (pendingOperands.empty() == true) {
					throw(new ParseException("Missing left" +
											 " operand for " +
											 pendingOperation, 0));
				}
				left = pendingOperands.pop();

                switch (pendingOperator) {
                    case Addition.operator:
                        pendingOperands.push(new Addition(left, right));
                        break;
                    case Subtraction.operator:
                        pendingOperands.push(new Subtraction(left,
                                right));
                        break;
                    case Multiplication.operator:
                        pendingOperands.push(new Multiplication(left,
                                right));
                        break;
                    case Division.operator:
                        pendingOperands.push(new Division(left,
                                right));
                        break;
                }


			} else {
				throw(new ParseException("Don't understand" +
										 " operation '" +
										 pendingOperation + "'", 0));
			}
		}
    }

    /**
     *  Evaluate the expression and return its value.
     *  @return Integer which represents the current value of
     *  the expression.
     */
    public int getValue()
    {
        if (value != null) {
            return(value.getValue());
        }

        return(0);
    }

    /**
     *  Return a {@code String} representation of the expression.
     *  @return {@code String} which represents the expression.
     */
    public String       toString()
    {
        if (value != null) {
            return("" + value);
        }

        return(null);
    }

    /**
     *  Unit test our {@code Expression} constructor.
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
        String toTest[]    = {
                "2 + 5 - 1 * 5 / 2",
//                    "1",
//                    "1 + 2",
//                    "1 + 2 + 3",
//                    "1 2",
//                    "1 + + 2",
//                    "+",
//                    "+ 1",
//                    "1 + ",
//                    "1 + 2 +",
//                    "1 + 2 + 3 +",
                };
        /*
         *  The items in this array are the results of evaluating the
         *  corresponding expressions at the same index in the
         *  previous array.  A -99 value means we expect an exception.
         */
        int             results[]   = {
                15,
//                    1,
//                    3,
//                    6,
//                    -99,
//                    -99,
//                    -99,
//                    -99,
//                    -99,
//                    -99,
//                    -99,
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

            System.out.println("Trying to evaluate:\n" +
                               "  \"" + toTest[whichTest] +
                               "\"");

            try {
                expression = new Expression(toTest[whichTest]);
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
