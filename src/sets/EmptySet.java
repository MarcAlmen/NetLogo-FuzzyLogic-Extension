package sets;

import java.util.List;

public class EmptySet extends FuzzySet {

	public EmptySet(String description, boolean continuous, String label,
			double[] universe) {
		super(description, continuous, label, universe);
		// TODO Auto-generated constructor stub
	}
	
	public EmptySet(){
		super(null,true,null,new double[]{});
	}

	@Override
	public double evaluate(double d) {
		return 0;
	}

	@Override
	public double evaluate(FuzzySet f) {
		return 0;
	}

	@Override
	public List<Object> getParameters() {
		return null;
	}

	@Override
	public String getNLTypeName() {
		// TODO Auto-generated method stub
		return "EmptySet";
	}

}
