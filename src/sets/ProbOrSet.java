package sets;

import general.DegreeOfFulfillment;

import java.util.List;

public class ProbOrSet extends OperatorSet {

	public ProbOrSet(List<FuzzySet> params, boolean continuous,String label, double[] universe) {
		super("Prob-or-set", params, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		//double[] evaluations = new double[parameters.size()];
		double result = parameters.get(0).evaluate(d);
		double b = 0;
		for(int i = 1 ; i < parameters.size() ; i++){
			b = parameters.get(i).evaluate(d);
			result = result + (b * (1 - result));
		}
		return result;
	}

	@Override
	public double evaluate(FuzzySet f) {
		if(!f.isContinuous()){
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}else{
			return DegreeOfFulfillment.continuousFulfillment(this, f);
		}
	}

	@Override
	public String getNLTypeName() {
		// TODO Auto-generated method stub
		return "Prob-or-set";
	}

}
