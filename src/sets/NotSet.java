package sets;

import general.DegreeOfFulfillment;

import java.util.List;

/**
 * This class represents the not sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class NotSet extends OperatorSet {

	/**
	 * Call the constructor from OperatorSet.
	 */
	public NotSet(List<FuzzySet> params, boolean continuous, String label,
			double[] universe) {
		super("Set-Not", params, continuous, label, universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Evaluate a number in the not set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double eval = parameters.get(0).evaluate(d);
		if (eval == Double.NaN) {
			return Double.NaN;
		} else {
			// return the opposite value, fuzzy logic works between 0 and 1.
			return 1 - eval;
		}
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the not set.
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
		return "set-not";
	}
}
