package sets;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionSet extends FuzzySet {
	
	List<Double> parameters;

	FunctionSet(String description,List<Double> params, boolean continuous, String label,double[] universe) {
		super(description, continuous, label, universe);
		parameters = new ArrayList<Double>();
		parameters.addAll(params);
	}

	public List<Double> getParameters() {
		List<Double> params = new ArrayList<Double>();
		params.addAll(parameters);
		return parameters;
	}

	public void setParameters(List<Double> parameters) {
		this.parameters.clear();
		this.parameters.addAll(parameters);
	}

}
