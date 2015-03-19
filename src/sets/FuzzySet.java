package sets;

import java.util.List;

import org.nlogo.api.ExtensionObject;

public abstract class FuzzySet implements ExtensionObject {
	
	private String description;
	private String label;
	private boolean continuous;
	private double[] universe;
	
	FuzzySet(String description,boolean continuous,String label,double[] universe){
		this.description = description;
		this.continuous = continuous;
		this.label = label;
		this.universe = universe.clone();
	}
	
	public abstract double evaluate(double d);
	public abstract double evaluate(FuzzySet f);
	@SuppressWarnings("rawtypes")
	public abstract List getParameters();
	
	@Override
	public String dump(boolean arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtensionName() {
		return "fuzzylogic";
	}

	@Override
	public abstract String getNLTypeName();

	@Override
	public boolean recursivelyEqual(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double[] getUniverse() {
		return universe.clone();
	}

	public void setUniverse(double[] universe) {
		this.universe = universe.clone();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isContinuous() {
		return continuous;
	}

	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}
	
	

}
