package sets;

import general.DegreeOfFulfillment;

import java.util.List;

public class SumSet extends MixedSet {

	public SumSet(List<FuzzySet> params, boolean continuous, String label, double[] universe) {
		super("Sum-Set", params, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		double eval,sum = 0;
		for(FuzzySet f : parameters){
			eval = f.evaluate(d);
			sum += eval;
		}
		if(sum > 1){
			return 1;
		}else{
			return sum;
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
		return "sum-set";
	}

}
