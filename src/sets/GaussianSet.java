package sets;



import java.util.List;

import general.DegreeOfFulfillment;

public class GaussianSet extends FunctionSet {

	public GaussianSet(List<Double> param, boolean continuous, String label, double[] universe) {
		super("Gaussian", param, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		List<Double> params = getParameters();
		double[] univ = getUniverse();
		// If out of the universe the function return undefined(Not a Number)
		if(d < univ[0] || d > univ[1]){
			return Double.NaN;
		}
		double s = params.get(1);
		double m = params.get(0);
		if(s == 0){
			return m;
		}else{
			return Math.pow(Math.E, -(Math.pow(d-m,2))/(2*Math.pow(s, 2)));
		}
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
		// TODO Auto-generated method stub
		return "Gaussian";
	}

}
