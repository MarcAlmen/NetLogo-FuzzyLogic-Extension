package sets;

import org.nlogo.api.ExtensionObject;
import org.nlogo.api.LogoList;

public abstract class FuzzySet implements ExtensionObject {
	
	//Hacer tipo enumerado
	private String description;
	private String label;
	//
	private LogoList parameters;
	private boolean continuous;
	private double[] universe;
	
	FuzzySet(String description,LogoList param,boolean continuous,String label,double[] universe){
		this.description = description;
		parameters = param;
		this.continuous = continuous;
		this.label = label;
		this.universe = universe;
	}
	
	public abstract double evaluate(double d);
	public abstract double evaluate(FuzzySet f);
	
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

	public LogoList getParameters() {
		return parameters;
	}

	public void setParameters(LogoList parameters) {
		this.parameters = parameters;
	}

	public double[] getUniverse() {
		return universe;
	}

	public void setUniverse(double[] universe) {
		this.universe = universe;
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
