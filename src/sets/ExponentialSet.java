package sets;

import java.util.List;

import general.DegreeOfFulfillment;

/**
 * This class represents exponential sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class ExponentialSet extends FunctionSet {

	/**
	 * Call the constructor of FunctionSet.
	 */
	public ExponentialSet(List<Double> param, boolean continuous, String label,
			double[] universe) {
		super("Exponential", param, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the exponential set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		List<Double> params = getParameters();
		double[] univ = getUniverse();
		double result;
		// If out of the universe the function return undefined(Not a Number)
		if (d < univ[0] || d > univ[1]) {
			return Double.NaN;
		}
		// a*e^(b*(x-c))
		result = (params.get(0))
				* Math.pow(Math.E, (params.get(1)) * (d - params.get(2)));
		// if the result is higher than 1 or lower than 0, it is clipped
		if (result > 1) {
			result = 1;
		} else if (result < 0) {
			result = 0;
		}
		return result;
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the exponential set.
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
		return "Exponential";
	}
}
