package sets;

import java.util.List;

import general.DegreeOfFulfillment;

public class ExponentialSet extends FunctionSet {

	public ExponentialSet(List<Double> param, boolean continuous,String label, double[] universe) {
		super("Exponential", param, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		List<Double> params = getParameters();
		double[] univ = getUniverse();
		double result;
		// If out of the universe the function return undefined(Not a Number)
		if(d < univ[0] || d > univ[1]){
			return Double.NaN;
		}
		result = (params.get(0))*Math.pow(Math.E,(params.get(1)) *(d - params.get(2)));
		//if the result is higher than 1 or lower than 0, it is clipped
		if(result > 1){
			result = 1;
		}else if(result < 0){
			result = 0;
		}
		return result;
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
		return "Exponential";
	}

}
