package sets;

import general.DegreeOfFulfillment;

import java.util.List;

public class NotSet extends OperatorSet {

	public NotSet(List<FuzzySet> params, boolean continuous, String label, double[] universe) {
		super("Set-Not", params, continuous, label, universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double evaluate(double d) {
		double eval = parameters.get(0).evaluate(d);
		if(eval == Double.NaN){
			return Double.NaN;
		}else{
			return 1-eval;	
		}
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
		return "set-not";
	}

}
