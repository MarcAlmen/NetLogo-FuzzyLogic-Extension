package sets;

import general.DegreeOfFulfillment;

import java.util.List;

public class MaxOrSet extends MixedSet{

	public MaxOrSet(List<FuzzySet> params, boolean continuous, String label, double[] universe) {
		super("Max-Or-set", params, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		double max = Double.NEGATIVE_INFINITY;
		double eval = Double.NEGATIVE_INFINITY;
		for(FuzzySet f : parameters){
			eval = f.evaluate(d);
			if(eval > max){
				max = eval;
			}
		}
		return max;
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
		return "max-or-set";
	}

}
