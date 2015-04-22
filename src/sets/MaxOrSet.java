package sets;

import general.DegreeOfFulfillment;

import java.util.List;

/**
 * This class represents the max(also known by or) sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class MaxOrSet extends OperatorSet {

	/**
	 * Call the constructor from OperatorSet.
	 */
	public MaxOrSet(List<FuzzySet> params, boolean continuous, String label,
			double[] universe) {
		super("Max-Or-set", params, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the max-or set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double max = Double.NEGATIVE_INFINITY;
		double eval = Double.NEGATIVE_INFINITY;
		// Iterate over the fuzzy sets.
		for (FuzzySet f : parameters) {
			eval = f.evaluate(d);
			// if one of them return NaN the MaxOr set too.
			if (eval == Double.NaN) {
				return Double.NaN;
			}
			// Look for the greatest of the evaluations.
			if (eval > max) {
				max = eval;
			}
		}
		return max;
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the max-or set.
	 * @param f The fuzzy set to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(FuzzySet f) {
		if (!f.isContinuous()) {
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		} else {
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		}
	}

	@Override
	/**
	 * String returned to netlogo.
	 */
	public String getNLTypeName() {
		// TODO Auto-generated method stub
		return "max-or-set";
	}

}
