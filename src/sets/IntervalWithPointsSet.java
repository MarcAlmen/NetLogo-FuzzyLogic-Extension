package sets;

import java.util.List;

import general.DegreeOfFulfillment;

/**
 * This class represents a interval with points set.
 * 
 * @author Marcos Almendres.
 *
 */
public class IntervalWithPointsSet extends PointSet {

	private double defaultValue;

	/**
	 * Call the constructor of PointSet. This set is a bit different and need to
	 * save another value.
	 */
	public IntervalWithPointsSet(List<double[]> param, boolean continuous,
			String label, double[] universe, double value) {
		super("IntervalWithPoints", param, continuous, label, universe);
		defaultValue = value;
	}

	@Override
	/**
	 * Evaluate a number in the interval with points set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double[] universe = getUniverse();
		List<double[]> params = getParameters();
		double x;
		// if the number is out of the universe return not a number
		if (d < universe[0] || d > universe[1]) {
			return Double.NaN;
		}
		// Iterate over the points of parameters
		for (double[] point : params) {
			x = point[0];
			// If the number to evaluate is the same as the x value of the point
			// return the y value
			if (d == x) {
				return point[1];
			}
			// If the number is bigger than the x value we stop looking for more
			// points cause they are sorted.
			if (x > d) {
				break;
			}
		}
		// If there wasn't a point with that x value return the default value.
		return defaultValue;
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the interval with points set.
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
		return "IntervalWithPoints";
	}

}
