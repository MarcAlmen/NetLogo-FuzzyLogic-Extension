package sets;

import general.DegreeOfFulfillment;
import java.util.List;

public class MinAndSet extends OperatorSet {
	


	public MinAndSet(List<FuzzySet> params, boolean continuous, String label, double[] universe) {
		super("Min-And-Set", params, continuous, label, universe);
	}

	@Override
	public double evaluate(double d) {
		double min = Double.POSITIVE_INFINITY;
		double eval = Double.POSITIVE_INFINITY;
		for(FuzzySet f : parameters){
			eval = f.evaluate(d);
			if(eval == Double.NaN){
				return Double.NaN;
			}
			if(eval < min){
				min = eval;
			}
		}
		return min;
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
		return "min-and-set";
	}

}
