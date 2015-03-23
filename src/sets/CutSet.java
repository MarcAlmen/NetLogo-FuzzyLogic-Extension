package sets;

import general.DegreeOfFulfillment;


public class CutSet extends DerivatedSet {
	
	public CutSet(FuzzySet param,double limit, boolean continuous, String label, double[] universe) {
		super("cut-set", param,limit, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		double eval = param.evaluate(d);
		if(eval == Double.NaN){
			return Double.NaN;
		}
		if(eval > c){
			return c;
		}else{
			return eval;
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
		return "continuous-cut";
	}


}
