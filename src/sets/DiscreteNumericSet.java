package sets;

import java.util.List;

import general.DegreeOfFulfillment;

public class DiscreteNumericSet extends PointSet {

	public DiscreteNumericSet(List<double[]> param, boolean continuous, String label, double[] universe) {
		super("DiscreteNumeric", param, continuous, label, universe);
	}

	@Override
	public double evaluate(double d){
		//Obtain the parameters of the FuzzySet and iterate them
		List<double[]> parameters= getParameters();
		for(double[] point : parameters){
			//If the first value(the x value in a graphic) of any parameter equals d we return the second value(The y value in a graphic)
			if(point[0] == d){
				return point[1];
			}
		}
		return Double.NaN;
	}

	@Override
	public double evaluate(FuzzySet f){
		if(f.isContinuous()){
			//If 1 discrete and 1 continuous call mixedFulfillment
			return DegreeOfFulfillment.mixedFulfillment(this, f);
		}else{
			//If both discrete call discreteFulfillment
				return DegreeOfFulfillment.discreteFulfillment(this,(PointSet) f);		
		}
	}

	@Override
	public String getNLTypeName() {
		return "DiscreteNumeric";
	}

}
