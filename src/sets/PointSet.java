package sets;

import java.util.ArrayList;
import java.util.List;

public abstract class PointSet extends FuzzySet{
	
	List<double[]> parameters;

	PointSet(String description,List<double[]> params, boolean continuous, String label, double[] universe) {
		super(description, continuous, label, universe);
		//parameters = params;
		parameters = new ArrayList<double[]>(params);
	}
	
	public List<double[]> getParameters() {
		List<double[]> params = new ArrayList<double[]>();
		params.addAll(parameters);
		return  params;
	}

	public void setParameters(List<double[]> parameters) {
		this.parameters.clear();
		this.parameters.addAll(parameters);
	}

}
