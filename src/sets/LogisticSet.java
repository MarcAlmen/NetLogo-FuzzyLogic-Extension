package sets;

import java.util.List;

import general.DegreeOfFulfillment;

/**
 * This class represents logistic sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class LogisticSet extends FunctionSet {

	/**
	 * Call the constructor of FunctionSet.
	 */
	public LogisticSet(List<Double> param, boolean continuous, String label,
			double[] universe) {
		super("Logistic", param, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the logistic set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		List<Double> params = getParameters();
		double[] univ = getUniverse();
		// If out of the universe the function return undefined(Not a Number)
		if (d < univ[0] || d > univ[1]) {
			return Double.NaN;
		}

		// 1/(1+a*e^(-b*(x-x0)))
		return 1 / (1 + (params.get(1))
				* Math.pow(Math.E, (-(params.get(2)) * (d - (params.get(0))))));
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the logistic set.
	 * @param f The fuzzy set to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(FuzzySet f) {
		if (f.isContinuous()) {
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		} else {
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}
	}

	@Override
	/**
	 * String returned to netlogo.
	 */
	public String getNLTypeName() {
		return "Logistic";
	}

}
