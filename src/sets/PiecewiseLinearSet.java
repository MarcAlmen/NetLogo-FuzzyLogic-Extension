package sets;

import java.util.List;

import general.DegreeOfFulfillment;

/**
 * This class represents piecewise linear sets.
 * 
 * @author Marcos Almendres.
 *
 */
public class PiecewiseLinearSet extends PointSet {

	/**
	 * Call the constructor of PointSet.
	 */
	public PiecewiseLinearSet(List<double[]> param, boolean continuous,
			String label, double[] universe) {
		super("PiecewiseLinear", param, continuous, label, universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Evaluate a number in the piecewise linear set.
	 * @param d The number to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(double d) {
		double[] universe = getUniverse();
		List<double[]> params = getParameters();
		double[] point;
		double[] pointB;
		int index = 0;
		// If the number is out of the universe, return Not a Number(undefined)
		if (d < universe[0] || d > universe[1]) {
			return Double.NaN;
		}
		// Seek the index of the lower point in which the number is between(Best
		// English History)
		// i.e: [0 1] [3 0] are the parameters points, and the number to
		// evaluate is 2. We want the index of [0 1]
		while (index < params.size() - 1) {
			point = params.get(index + 1);
			// If the i+1 first element is lower than the number, keep on
			// iterating
			if (point[0] <= d) {
				index++;
			} else {// if not, break;
				break;
			}
		}
		point = params.get(index);
		double x = point[0];
		// if the x value of the point is exactly the same as the point given,
		// report the y value
		if (x == d) {
			return point[1];
		} else {// If not, calculate the y value
			pointB = params.get(index + 1);
			// Gradient of the line that join the two points
			double gradient = (pointB[1] - point[1]) / (pointB[0] - point[0]);
			// Calculate and return the value of y in that line
			return point[1] + (gradient * (d - point[0]));
		}
	}

	@Override
	/**
	 * Evaluate a fuzzy set in the piecewise linear set.
	 * @param f The fuzzy set to evaluate.
	 * @return The evaluation result.
	 */
	public double evaluate(FuzzySet f) {
		// if not continuous mixed fulfillment
		if (!f.isContinuous()) {
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		} else {
			// if continuous and piecewiseLinear --> piecewise Fulfillment
			if (f instanceof PiecewiseLinearSet) {
				return DegreeOfFulfillment.piecewiseFulfillment(this,
						(PointSet) f);
			} else {// if continuous but not piecewise --> continuous
					// fulfillment
				return DegreeOfFulfillment.continuousFulfillment(this, f);
			}
		}
	}

	@Override
	/**
	 * String returned to netlogo.
	 */
	public String getNLTypeName() {
		return "PiecewiseLinear";
	}

}
