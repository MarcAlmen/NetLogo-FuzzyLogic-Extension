package sets;

import general.DegreeOfFulfillment;

import org.nlogo.api.LogoList;

public class PiecewiseLinearSet extends FuzzySet {

	public PiecewiseLinearSet(LogoList param, boolean continuous,String label, double[] universe) {
		super("PiecewiseLinear", param, continuous, label, universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double evaluate(double d) {
		double[] universe = getUniverse();
		LogoList params = getParameters();
		LogoList point;
		LogoList pointB;
		int index = 0;
		//If the number is out of the universe, return Not a Number(undefined)
		if(d < universe[0] || d > universe[1]){
			return Double.NaN;
		}
		//Seek the index of the lower point in which the number is between(Best English History)
		//i.e: [0 1] [3 0] are the parameters points, and the number to evaluate is 2. We want the index of [0 1]
		while(index < params.size()-1){
			point =(LogoList) params.get(index+1);
			//If the i+1 first element is lower than the number, keep on iterating
			if((Double) point.first()<=d){
				index++;
			}else{//if not, break;
				break;
			}
		}
		point =(LogoList) params.get(index);
		double x =(Double) point.first();
		//if the x value of the point is exactly the same as the point given, report the y value
		if(x == d){
			return (Double) point.get(1);
		}else{//If not, calculate the y value
			pointB = (LogoList) params.get(index+1);
			//Gradient of the line that join the two points
			double gradient = ((Double) pointB.get(1) - (Double) point.get(1))/((Double) pointB.first() - (Double) point.first());
			//Calculate and return the value of x in that line
			return (Double) point.get(1) + (gradient *(d - (Double) point.first()));
		}
	}

	@Override
	public double evaluate(FuzzySet f) {
		//if not continuous mixed fulfillment
		if(!f.isContinuous()){
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}else{
			//if continuous and piecewiseLinear --> piecewise Fulfillment
			if(f instanceof PiecewiseLinearSet){
				return DegreeOfFulfillment.piecewiseFulfillment(this, f);
			}else{
				return DegreeOfFulfillment.continuousFulfillment(this, f);
			}
		}
	}

	@Override
	public String getNLTypeName() {
		return "PiecewiseLinear";
	}

}
