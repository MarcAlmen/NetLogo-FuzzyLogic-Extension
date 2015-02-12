package sets;

import general.DegreeOfFulfillment;

import org.nlogo.api.LogoList;

public class IntervalWithPointsSet extends FuzzySet {

	public IntervalWithPointsSet(LogoList param,boolean continuous, String label, double[] universe) {
		super("IntervalWithPoints", param, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		double[] universe = getUniverse();
		LogoList params = getParameters();
		LogoList point;
		double x;
		//if the number is out of the universe return not a number
		if(d < universe[0] || d > universe[1]){
			return Double.NaN;
		}
		//Iterate over the points of parameters
		for(Object o: params){
			point = (LogoList) o;
			x = (Double) point.first();
			//If the number to evaluate is the same as the x value of the points return the y value
			if(d == x){
				return (Double) point.get(1);
			}
			//If the number is bigger than the x value we stop looking for more points cause they are ordered.
			if(x > d){
				break;
			}
		}
		return universe[2];
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
