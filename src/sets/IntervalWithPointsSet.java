package sets;

import java.util.List;

import general.DegreeOfFulfillment;

public class IntervalWithPointsSet extends PointSet {
	
	double defaultValue;

	public IntervalWithPointsSet(List<double[]> param,boolean continuous, String label, double[] universe,double value) {
		super("IntervalWithPoints", param, continuous, label, universe);
		defaultValue = value;
	}

	@Override
	public double evaluate(double d) {
		double[] universe = getUniverse();
		List<double[]> params = getParameters();
		double x;
		//if the number is out of the universe return not a number
		if(d < universe[0] || d > universe[1]){
			return Double.NaN;
		}
		//Iterate over the points of parameters
		for(double[] point : params){
			x = point[0];
			//If the number to evaluate is the same as the x value of the points return the y value
			if(d == x){
				return point[1];
			}
			//If the number is bigger than the x value we stop looking for more points cause they are ordered.
			if(x > d){
				break;
			}
		}
		return defaultValue;
	}

	@Override
	public double evaluate(FuzzySet f) {
		if(f.isContinuous()){
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		}else{
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}
	}

	@Override
	public String getNLTypeName() {
		return "IntervalWithPoints";
	}

}
