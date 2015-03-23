package sets;

import java.util.ArrayList;
import java.util.List;

public abstract class OperatorSet extends FuzzySet{
	
	List<FuzzySet> parameters;

	OperatorSet(String description,List<FuzzySet> params, boolean continuous, String label,double[] universe) {
		super(description, continuous, label, universe);
		parameters = new ArrayList<FuzzySet>();
		parameters.addAll(params);
	}

	@Override
	public List<FuzzySet> getParameters() {
		List<FuzzySet> backParams = new ArrayList<FuzzySet>();
		backParams.addAll(parameters);
		return backParams;
	}
}
