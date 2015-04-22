package sets;

import general.DegreeOfFulfillment;

/**
 * When a continuous fuzzy set is powered, a new power set is created.
 * This class represents power sets
 * 
 * @author Marcos Almendres.
 *
 */
public class PowerSet extends DerivedSet{

	/**
	 * Call the Constructor from DerivedSet.
	 * 
	 * @param param
	 *            The continuous fuzzy set.
	 * @param limit
	 *            The number to power the fuzzy set.
	 * @param continuous
	 *            True always.
	 * @param label
	 *            A label for the new power set.
	 * @param universe
	 *            the universe of the power set.
	 */
	public PowerSet(FuzzySet param, double limit, boolean continuous, String label, double[] universe) {
		super("power-set", param, limit, continuous, label, universe);
	}

	@Override
	/**
	 * Evaluate a number in the cut-set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double eval = param.evaluate(d);
		if(eval == Double.NaN){
			return Double.NaN;
		}
		return Math.pow(eval,c);
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the cut-set.
	 * @param f The fuzzy set to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(FuzzySet f) {
		if(!f.isContinuous()){
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}else{
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		}
	}

	@Override
	public String getNLTypeName() {
		return "continuous-power";
	}

}
