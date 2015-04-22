package sets;

import java.util.List;

import general.DegreeOfFulfillment;

/**
 * This class represents gaussian sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class GaussianSet extends FunctionSet {

	/**
	 * Call the constructor of FunctionSet.
	 */
	public GaussianSet(List<Double> param, boolean continuous, String label,
			double[] universe) {
		super("Gaussian", param, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the gaussian set.
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
		double s = params.get(1);
		double m = params.get(0);
		// if standard deviations is 0 return the mean.
		if (s == 0) {
			return m;
		} else {
			// e^(-((x-m)^2)/(2*(s^2)))
			return Math.pow(Math.E, -(Math.pow(d - m, 2))
					/ (2 * Math.pow(s, 2)));
		}
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the gaussian set.
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
		// TODO Auto-generated method stub
		return "Gaussian";
	}

}
