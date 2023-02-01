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
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
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
     * Calculate percent error between an approximation and an actual constant
     * value. Returns the percent error.
     *
     * @param actual
     *            the actual constant value
     * @param approx
     *            the approximation value
     * @return the percent error
     */
    private static double computePercentError(double actual, double approx) {
        // Constant created to convert error to percentage form.
        final int percentageConverter = 100;

        double error = Math.abs((actual - approx) / approx)
                * percentageConverter;
        return error;
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

        // Initialize variables to store the optimal approximation values.
        double minimumPercentError = convertToPercent, optimalApproximation = 0;
        double optimalConstantA = 0, optimalConstantB = 0;
        double optimalConstantC = 0, optimalConstantD = 0;

        /*
         * Each for loop runs through every possible combination of constants to
         * find the optimal constant combination with the smallest relative
         * error.
         */
        for (int indexA = 0; indexA < numberOfConstants; indexA++) {
            for (int indexB = 0; indexB < numberOfConstants; indexB++) {
                for (int indexC = 0; indexC < numberOfConstants; indexC++) {
                    for (int indexD = 0; indexD < numberOfConstants; indexD++) {
                        // de Jager formula calculates approximation.
                        double muApproximation = Math.pow(userInputW,
                                constants[indexA])
                                * Math.pow(userInputX, constants[indexB])
                                * Math.pow(userInputY, constants[indexC])
                                * Math.pow(userInputZ, constants[indexD]);

                        /*
                         * Checks if the relative error for the current
                         * iteration is less than the lowest recorded relative
                         * error, and fills all of the optimal value storing
                         * variables with the current values of said variables
                         * if true.
                         */
                        if (computePercentError(muConstant,
                                muApproximation) < minimumPercentError) {
                            optimalApproximation = muApproximation;
                            minimumPercentError = computePercentError(
                                    muConstant, muApproximation);
                            optimalConstantA = constants[indexA];
                            optimalConstantB = constants[indexB];
                            optimalConstantC = constants[indexC];
                            optimalConstantD = constants[indexD];
                        }
                    }
                }
            }
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
