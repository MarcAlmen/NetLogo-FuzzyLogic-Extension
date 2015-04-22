package sets;

import general.DegreeOfFulfillment;

/**
 * When a continuous fuzzy set is truncated, a new cut-set is created.
 * This class represents cut-sets.
 * @author Marcos Almendres.
 *
 */
public class CutSet extends DerivedSet {

	/**
	 * Call the Constructor from DerivatedSet.
	 * 
	 * @param param
	 *            The continuous fuzzy set.
	 * @param limit
	 *            The number to truncate the fuzzy set.
	 * @param continuous
	 *            True always.
	 * @param label
	 *            A label for the new cut set.
	 * @param universe
	 *            the universe of the cut set.
	 */
	public CutSet(FuzzySet param, double limit, boolean continuous,
			String label, double[] universe) {
		super("cut-set", param, limit, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the cut-set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double eval = param.evaluate(d);
		// If the fuzzy set is not defined in the number, return Not a Number
		if (eval == Double.NaN) {
			return Double.NaN;
		}
		// Truncate the evaluation result if greater than the parameter.
		if (eval > c) {
			return c;
		} else {
			return eval;
		}
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the cut-set.
	 * @param f The fuzzy set to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(FuzzySet f) {
		// If one set continuous and the other discrete.
		if (!f.isContinuous()) {
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		} else {// If both sets continuous
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		}
	}

	@Override
	/**
	 * String returned to netlogo.
	 */
	public String getNLTypeName() {
		return "continuous-cut";
	}
}
