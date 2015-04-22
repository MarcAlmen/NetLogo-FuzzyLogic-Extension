package sets;

import general.DegreeOfFulfillment;

public class ProdSet extends DerivedSet {

	public ProdSet(FuzzySet param, double limit,boolean continuous, String label, double[] universe) {
		super("prod-set", param, limit, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		double eval = param.evaluate(d);
		if(eval == Double.NaN){
			return Double.NaN;
		}
		return this.c * eval;
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
		return "continuous-prod";
	}

}
