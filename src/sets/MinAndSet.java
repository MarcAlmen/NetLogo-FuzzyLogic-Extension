package sets;

import general.DegreeOfFulfillment;
import java.util.List;

/**
 * This class represents the min(also known by and) sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class MinAndSet extends OperatorSet {

	/**
	 * Call the constructor from OperatorSet.
	 */
	public MinAndSet(List<FuzzySet> params, boolean continuous, String label,
			double[] universe) {
		super("Min-And-Set", params, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the min-and set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double min = Double.POSITIVE_INFINITY;
		double eval = Double.POSITIVE_INFINITY;
		// Iterate over the fuzzy sets
		for (FuzzySet f : parameters) {
			// evaluate all the fuzzy sets
			eval = f.evaluate(d);
			// If one of them return NaN, Min-And will return NaN too.
			if (eval == Double.NaN) {
				return Double.NaN;
			}
			// Look for the lowest evaluation.
			if (eval < min) {
				min = eval;
			}
		}
		return min;
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the Min-and set.
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
		return "min-and-set";
	}
}
