import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Compute an approximation of any constant with the minimum possible relative
 * error using the de Jager formula.
 *
 * @author Nicholas McCracken
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double positiveDouble = 0;

        /*
         * Loop repeatedly asks user for a number. If number given is negative,
         * the loop continues.
         */
        while (positiveDouble <= 0) {
            out.print("Please enter a positive real number: ");
            String userInput = in.nextLine();

            /*
             * Checks if the user input is an integer, which is then converted
             * to an integer if true and set to equal the loop condition
             * variable, thereby allowing the loop to check if the user input
             * integer passes or fails conditions.
             */
            if (FormatChecker.canParseDouble(userInput)) {
                positiveDouble = Double.parseDouble(userInput);
            }
        }
        return positiveDouble;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double positiveDouble = 0;

        /*
         * Loop repeatedly asks user for a number. If number given is negative
         * or equal to 1, the loop continues.
         */
        while (positiveDouble <= 0 || positiveDouble == 1.0) {
            out.print("Please enter a positive real number that is not equal"
                    + " to 1.0: ");
            String userInput = in.nextLine();

            /*
             * Checks if the user input is an integer, which is then converted
             * to an integer if true and set to equal the loop condition
             * variable, thereby allowing the loop to check if the user input
             * integer passes or fails conditions.
             */
            if (FormatChecker.canParseDouble(userInput)) {
                positiveDouble = Double.parseDouble(userInput);
            }
        }
        return positiveDouble;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        /*
         * Initialize constants, the quantity of constants within the constant
         * array, and the required integer to convert a number to percentage.
         */
        final double[] constants = { -5, -4, -3, -2, -1, -1.0 / 2, -1.0 / 3,
                -1.0 / 4, 0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1, 2, 3, 4, 5 };
        final int numberOfConstants = 17;
        final int convertToPercent = 100;

        /*
         * Instruct user what to enter then store in respective variables
         * necessary for computation of de Jager formula.
         */
        out.println("Enter a constant to be approximated.");
        double muConstant = getPositiveDouble(in, out);
        out.println("");

        out.println("Enter four numbers with personal meaning to you.");
        double userInputW = getPositiveDoubleNotOne(in, out);
        double userInputX = getPositiveDoubleNotOne(in, out);
        double userInputY = getPositiveDoubleNotOne(in, out);
        double userInputZ = getPositiveDoubleNotOne(in, out);
        out.println("");

        /*
         * Initialize index values to represent each of the four constants, and
         * variables to store the optimal approximation values.
         */
        int indexA = 0, indexB = 0, indexC = 0, indexD = 0;
        double minimumPercentError = convertToPercent, optimalApproximation = 0;
        double optimalConstantA = 0, optimalConstantB = 0;
        double optimalConstantC = 0, optimalConstantD = 0;

        /*
         * Each while loop runs through every possible combination of constants
         * to find the optimal constant combination with the smallest relative
         * error.
         */
        while (indexA < numberOfConstants) {
            while (indexB < numberOfConstants) {
                while (indexC < numberOfConstants) {
                    while (indexD < numberOfConstants) {
                        // de Jager formula calculates approximation.
                        double muApproximation = Math.pow(userInputW,
                                constants[indexA])
                                * Math.pow(userInputX, constants[indexB])
                                * Math.pow(userInputY, constants[indexC])
                                * Math.pow(userInputZ, constants[indexD]);

                        /*
                         * Percent error is calculated using approx and actual
                         * values of the user selected constant.
                         */
                        double percentError = Math
                                .abs((muConstant - muApproximation)
                                        / muApproximation)
                                * convertToPercent;

                        /*
                         * Checks if the relative error for the current
                         * iteration is less than the lowest recorded relative
                         * error, and fills all of the optimal value storing
                         * variables with the current values of said variables
                         * if true.
                         */
                        if (percentError < minimumPercentError) {
                            optimalApproximation = muApproximation;
                            minimumPercentError = percentError;
                            optimalConstantA = constants[indexA];
                            optimalConstantB = constants[indexB];
                            optimalConstantC = constants[indexC];
                            optimalConstantD = constants[indexD];
                        }
                        indexD++;
                    }
                    /*
                     * After each run of the while loop nested within, it's
                     * respective constant index is rest back to zero, and the
                     * constant index of this while loop is increased by 1,
                     * which repeats for all proceeding while loops until every
                     * possible combination is checked.
                     */
                    indexD = 0;
                    indexC++;
                }
                indexC = 0;
                indexB++;
            }
            indexB = 0;
            indexA++;
        }

        // Optimal constants, approximation, and percent error is printed.
        out.println("The best combination of constants using the de Jager"
                + " formula are a = " + optimalConstantA + ", b = "
                + optimalConstantB + ", c = " + optimalConstantC + ", d = "
                + optimalConstantD + ".");
        out.println("The best approximation is " + optimalApproximation + ".");
        out.println("The percent error is " + minimumPercentError + "%.");

        // Close input and output streams.
        in.close();
        out.close();
    }
}
