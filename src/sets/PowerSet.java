package sets;

import general.DegreeOfFulfillment;

public class PowerSet extends DerivatedSet{

	public PowerSet(FuzzySet param, double limit, boolean continuous, String label, double[] universe) {
		super("power-set", param, limit, continuous, label, universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double evaluate(double d) {
		double eval = param.evaluate(d);
		if(eval == Double.NaN){
			return Double.NaN;
		}
		return Math.pow(eval,c);
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
		return "continuous-power";
	}

}
