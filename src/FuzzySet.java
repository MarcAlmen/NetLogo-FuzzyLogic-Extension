import java.util.List;

import org.nlogo.api.ExtensionObject;
import org.nlogo.api.LogoList;

public class FuzzySet implements ExtensionObject {
	
	//Hacer tipo enumerado
	private FuzzyType description;
	private String label;
	//
	private LogoList parameters;
	private boolean continuous;
	private Double[] universe;
	
	FuzzySet(FuzzyType descrip,LogoList param,boolean continuous,String label,Double[] universe){
		description = descrip;
		parameters = param;
		this.continuous = continuous;
		this.label = label;
		this.universe = universe;
	}
	
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
	public String getNLTypeName() {
		return "";
	}

	@Override
	public boolean recursivelyEqual(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public FuzzyType getDescription() {
		return description;
	}

	public void setDescription(FuzzyType description) {
		this.description = description;
	}

	public LogoList getParameters() {
		return parameters;
	}

	public void setParameters(LogoList parameters) {
		this.parameters = parameters;
	}

	public Double[] getUniverse() {
		return universe;
	}

	public void setUniverse(Double[] universe) {
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
