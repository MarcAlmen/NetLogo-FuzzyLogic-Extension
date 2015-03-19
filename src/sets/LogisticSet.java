package sets;

import java.util.List;

import general.DegreeOfFulfillment;

public class LogisticSet extends FunctionSet {

	public LogisticSet(List<Double> param, boolean continuous,String label, double[] universe) {
		super("Logistic", param, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		List<Double> params = getParameters();
		double[] univ = getUniverse();
		// If out of the universe the function return undefined(Not a Number)
		if(d < univ[0] || d > univ[1]){
			return Double.NaN;
		}

		// function =  1/1+a*e^(-b*(x-x0))
		return 1 / (1 + (params.get(1)) * Math.pow(Math.E,(-(params.get(2)) * (d-(params.get(0))))));
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
		return "Logistic";
	}

}
