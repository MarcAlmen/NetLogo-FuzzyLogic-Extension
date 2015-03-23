package sets;

import general.DegreeOfFulfillment;

import java.util.List;

public class ProbOrSet extends OperatorSet {

	public ProbOrSet(List<FuzzySet> params, boolean continuous,String label, double[] universe) {
		super("Prob-or-set", params, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		// TODO Confirmar con luis
		return 0;
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
