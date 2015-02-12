package sets;

import general.DegreeOfFulfillment;

import org.nlogo.api.LogoList;

public class DiscreteNumericSet extends FuzzySet {

	public DiscreteNumericSet(LogoList param, boolean continuous,
			String label, Double[] universe) {
		super("DiscreteNumeric", param, continuous, label, universe);
	}

	@Override
	public double evaluate(Double d){
		//Obtain the parameters of the FuzzySet and iterate them
		LogoList parameters= getParameters();
		Double aux;
		for(Object o : parameters){
			LogoList point = (LogoList) o;
			//If the first value(the x value in a graphic) of any parameter equals d we return the second value(The y value in a graphic)
			aux =(Double) point.get(0);
			if(aux.compareTo(d) == 0){
				return (Double) point.get(1);
			}
		}
		return Double.NaN;
	}

	@Override
	public double evaluate(FuzzySet f) {
		if(f.isContinuous()){
			//If 1 discrete and 1 continuous call mixedFulfillment
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}else{
			//If both discrete call discreteFulfillment
			return DegreeOfFulfillment.discreteFulfillment(this,f);		
		}
	}

	@Override
	public String getNLTypeName() {
		return "DiscreteNumeric";
	}

}
