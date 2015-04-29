package sets.derived;

import sets.general.DerivedSet;
import sets.general.FuzzySet;
import general.DegreeOfFulfillment;

/**
 * When we prod a continuous set, a new prod-set is created. This class
 * represents prod sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class ProdSet extends DerivedSet {

	/**
	 * Call the constructor of DerivedSet.
	 * 
	 * @see sets.general.DerivedSet#DerivedSet(String, FuzzySet, double,
	 *      boolean, String, double[])
	 */
	public ProdSet(FuzzySet param, double limit, boolean continuous,
			String label, double[] universe) {
		super("prod-set", param, limit, continuous, label, universe);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double evaluate(double d) {
		double eval = param.evaluate(d);
		if (eval == Double.NaN) {
			return Double.NaN;
		}
		return this.c * eval;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double evaluate(FuzzySet f) {
		if (!f.isContinuous()) {
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		} else {
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNLTypeName() {
		return "continuous-prod";
	}

}
