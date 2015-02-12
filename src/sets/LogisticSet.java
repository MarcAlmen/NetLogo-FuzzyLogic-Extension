package sets;

import general.DegreeOfFulfillment;

import org.nlogo.api.LogoList;

public class LogisticSet extends FuzzySet {

	public LogisticSet(LogoList param, boolean continuous,String label, double[] universe) {
		super("Logistic", param, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		LogoList params = getParameters();
		double[] univ = getUniverse();
		// If out of the universe the function return undefined(Not a Number)
		if(d < univ[0] || d > univ[1]){
			return Double.NaN;
		}
//		double a = (Double) params.get(1);
//		double b = (Double) params.get(2);
//		double x = (Double) params.first();
		//params = [x0 a b [Universe]]
		// function =  1/1+a*e^(-b*(x-x0))
		return 1 / (1 + ((Double) params.get(1)) * Math.pow(Math.E,(-((Double) params.get(2)) * (d-((Double) params.first())))));
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
