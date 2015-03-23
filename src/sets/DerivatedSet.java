package sets;

import java.util.ArrayList;
import java.util.List;

public abstract class DerivatedSet extends FuzzySet {

	FuzzySet param;
	double c;
	
	DerivatedSet(String description,FuzzySet param,double limit, boolean continuous, String label, double[] universe) {
		super(description, continuous, label, universe);
		this.param= param;
		c = limit;
	}
	
	public List<FuzzySet> getParameters(){
		List<FuzzySet> l = new ArrayList<FuzzySet>();
		l.add(param);
		return l;
	}
	
	public FuzzySet getSet(){
		return param;
	}
	
	public double getLimit(){
		return c;
	}

}
